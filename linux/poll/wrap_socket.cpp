#include"wrap_socket.h"
#include<iostream>
#include<errno.h>
#include<unistd.h>
#include<arpa/inet.h>
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
ssize_t my_read(int fd,char * ptr);
ssize_t Readline(int fd,void * vptr,size_t maxlen);
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
