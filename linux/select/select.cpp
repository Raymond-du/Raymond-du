#include<iostream>
#include<sys/types.h>
#include<sys/socket.h>
#include"wrap_socket.h"
#include<unistd.h>
#include<string.h>
#include<arpa/inet.h>
#include<ctype.h>
#define SRV_PORT 1214

using namespace std;
//我们使用绑定ipv6的地址

int main(){
	//自定义一个client数组 大小是1024个  方便查找 和一个监听的端口
	int listenfd,clientfd[FD_SETSIZE];
	//maxfd select描述的最大描述符 +1，clientfd的最大的下标，减少重复次数
	int maxfd,maxi=-1,nready;
	//读取的缓冲区，和地址
	char buf[BUFSIZ],ip_str[INET_ADDRSTRLEN];
	
	sockaddr_in6 sa_client,sa_server;
	socklen_t sa_len;
	//read_set获取到读信号的集合，all_set等待信号的集合
	fd_set read_set,all_set;

	listenfd=Socket(AF_INET6,SOCK_STREAM,0);
	//初始化sa_server
	memset(&sa_server,'\0',sizeof(sockaddr_in6));
	sa_server.sin6_family=AF_INET6;
	sa_server.sin6_port=htons(SRV_PORT);
	sa_server.sin6_addr=in6addr_any;
	//绑定sockaddr
	Bind(listenfd,(sockaddr*)&sa_server,sizeof(sockaddr_in6));
	//监听端口
	Listen(listenfd,5);
	//select的第一参数的值 描述符最大值+1
	maxfd=listenfd;
	//初始化clientfd数组
	for(int i=0;i<FD_SETSIZE;i++){
		clientfd[i]=-1;
	}
	//初始化all_set并添加listenfd
	FD_ZERO(&all_set);
	FD_SET(listenfd,&all_set);
	//循环获取读取的信号
	while(1){
		read_set=all_set;
		//等待集合中套接子的信号 nready获得几个信号
		if((nready=select(maxfd+1,&read_set,NULL,NULL,NULL))<0){
			perr_exit("select error");
		}
		//判断是否是listenfd的信号
		if(FD_ISSET(listenfd,&read_set)){
			sa_len=sizeof(sockaddr_in6);
			memset(&sa_client,'\0',sizeof(sa_client));
			int tempfd=Accept(listenfd,(sockaddr*)&sa_client,&sa_len);
			inet_ntop(AF_INET6,&sa_client.sin6_addr,ip_str,INET_ADDRSTRLEN);
			cout<<"connect-> ip: "<<ip_str<<"port: "<<htons(sa_client.sin6_port)<<endl;
			//将连接到的sock添加到clientfd的数组中
			int i=0;
			for(;i<FD_SETSIZE;i++){
				if(clientfd[i]<0){
					clientfd[i]=tempfd;
					break;
				}
			}
			//判断clientfd数组是否越界
			if(i>FD_SETSIZE){
				perr_exit("too many client");
			}
			//添加tempfd到集合中
			FD_SET(tempfd,&all_set);
			//重新定向clientfd下标的最大值
			if(maxi<i){
				maxi=i;
			}		
			//重新获取文件描述符的最大值
			if(tempfd>maxfd){
				maxfd=tempfd;
			}

			if(--nready==0)
				continue;
		}
		for(int i=0;i<=maxi;i++){
			//跨过不符合要求的
			if(clientfd[i]<0){
				continue;
			}
			if(FD_ISSET(clientfd[i],&read_set)){
				int n;
				//客户端关闭连接,关闭监听客户的信号
				if((n=Read(clientfd[i],buf,BUFSIZ))==0){
					Close(clientfd[i]);
					FD_CLR(clientfd[i],&all_set);
					clientfd[i]=-1;
				}
				buf[n]=0;
				cout<<"read :"<<buf<<endl;
				if(--nready==0)
					break;
			}
		}
	}
	Close(listenfd);
	return 0;
}
