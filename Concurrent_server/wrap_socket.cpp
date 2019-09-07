#include"wrap_socket.h"
#include<iostream>
#include<errno.h>
#include<unistd.h>
#include<sys/stat.h>
#include<sys/types.h>
#include<arpa/inet.h>
#include<fcntl.h>
void perr_exit(const char *s){
	perror(s);
	exit(-1);
}

int Accept(int fd,struct sockaddr *sa,socklen_t *salenptr){
	int n;
	while((n=accept(fd,sa,salenptr))<0){
		if((errno==ECONNABORTED)||(errno==EINTR)){
			continue;
		}else{
			perr_exit("accept error");
		}
	}
	return n;
}
int Bind(int fd,const struct sockaddr *sa,socklen_t salen){
	int n;
	if((n=bind(fd,sa,salen))<0){
		perr_exit("bind error");
	}
	return n;
}
int Connect(int fd,const struct sockaddr *sa,socklen_t salen){
	int n;
	if((n=connect(fd,sa,salen))<0){
		perr_exit("connect error");
	}
	return n;
}
int Listen(int fd,int backlog){
	int n;
	if((n=listen(fd,backlog))<0){
		perr_exit("listen error");
	}
	return n;
}
int Socket(int family,int type,int protocol){
	int n;
	if((n=socket(family,type,protocol))<0){
		perr_exit("socket error");
	}
	return n;
}
ssize_t Read(int fd,void *ptr,size_t nbytes){
	ssize_t n;
	while((n=read(fd,ptr,nbytes))==-1){
		if(errno==EINTR){
			continue;
		}else{
			return -1;
		}
	}
	return n;
}
ssize_t Write(int fd,const void *ptr,size_t nbytes){
	ssize_t n;
	while((n=write(fd,ptr,nbytes))==-1){
		if(errno==EINTR){
			continue;
		}else{
			return -1;
		}
	}
	return n;
}
int Close(int fd){
	int n;
	if((n=close(fd))){
		perr_exit("close error");
	}
	return n;
}
//应该读取的字节数
ssize_t Readn(int fd,void *vptr,size_t n){
	size_t nleft; //剩余未读取的字节数
	ssize_t nread; //实际读到的字节数
	char *ptr;

	ptr=(char *)vptr;
	nleft=n;  //未读取字节数
	
	while(nleft>0){
		if((nread=read(fd,ptr,nleft))<0){
			if(errno==EINTR){
				nread=0;
			}else{
				return -1;
			}
		}else if(nread==0){
			break;
		}
		nleft-=nread;
		ptr+=nread;
	}
	return n-nleft;
}
ssize_t Writen(int fd,const void * vptr,size_t n){
	size_t nleft;
	ssize_t nwriten;
	const char *ptr;

	ptr=(char*)vptr;
	nleft=n;

	while(nleft>0){
		if((nwriten=write(fd,ptr,nleft))){
			if(nwriten<0&&errno==EINTR){
				nwriten=0;
			}else{
				return -1;
			}
		}
		nleft-=nwriten;
		ptr+=nwriten;
	}
	return n;
}
ssize_t my_read(int fd,char * ptr){
	static int read_cnt;
	static char *read_ptr;
	static char read_buf[100];

	while(read_cnt<=0){
		if((read_cnt=read(fd,read_buf,sizeof(read_buf)))){
			if(errno==EINTR)
				continue;
			return -1;
		}else if(read_cnt==0)
			return -1;
		read_ptr=read_buf;
	}
	read_cnt--;
	*ptr=*read_ptr++;
	return 1;
}
ssize_t Readline(int fd,void * vptr,size_t maxlen){
	ssize_t n,rc;
	char c,*ptr;
	ptr=(char *)vptr;
	
	for(n=1;n<(ssize_t)maxlen;n++){
		if((rc=my_read(fd,&c))==1){
			*ptr++=c;
			if(c=='\n')
				break;
		}else if(rc==0){
			*ptr=0;
			return n-1;
		}else{
			return -1;
		}
	}
	*ptr=0;
	return n;
}
int Inet_pton(int af,const char *src,void *dst){
	int n;
	if((n=inet_pton(af,src,dst))<=0){
		if(n==0){
			perr_exit("Not in persentation format");
		}else{
			perr_exit("inet_pton error");
		}
	}
	return n;
}
//判断文件是否是目录文件
int isdir(const char *pathname){
	struct stat fileStat;
	if(stat(pathname,&fileStat)==0&&fileStat.st_mode&S_IFDIR){
		return 0;
	}
	return -1;
}
int Mkdir(const char *pathname, mode_t mode){
	int n;
	if((n=mkdir(pathname,mode))==-1){
		if(errno==EEXIST&&0==isdir(pathname)){
			return 0;
		}else
			perr_exit("mkdir error");
	}
	return n;
}
//open函数进行包装
int Open(const char *pathname,int flags,mode_t mode){
	int n;
	if((n=open(pathname,flags,mode))==-1){
		perr_exit("open error");
	}
	return n;
}
