#include<iostream>
#include<stdlib.h>
#include<string.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include<poll.h>
#include<errno.h>
#include<ctype.h>
#include"wrap_socket.h"

#define MAXLINE 80
#define SERV_PORT 1214
#define OPEN_MAX 1024 //设置一个最大的连接数
using namespace std;

int main(){
	//poll的实现方式同select
	int i,j,maxi,listenfd,connfd,sockfd;
	int nready;
	ssize_t n;

	char buf[MAXLINE],str[INET_ADDRSTRLEN];
	socklen_t clilen;
	pollfd client[OPEN_MAX];
	sockaddr_in cliaddr,servaddr;

	listenfd=Socket(AF_INET,SOCK_STREAM,0);

	int opt=1;
	//设置了端口复用
	setsockopt(listenfd,SOL_SOCKET,SO_REUSEADDR,&opt,sizeof(opt));
	
	bzero(&servaddr,sizeof(servaddr));
	servaddr.sin_family=AF_INET;
	servaddr.sin_port=htons(SERV_PORT);
	servaddr.sin_addr.s_addr=INADDR_ANY;

	Bind(listenfd,(sockaddr*)&servaddr,sizeof(servaddr));
	Listen(listenfd,20);

	client[0].fd=listenfd;
	client[0].events=POLLIN;
	
	for(i=1;i<OPEN_MAX;i++)
		client[i].fd=-1;
	maxi=0;
	
	while(1){
		nready=poll(client,maxi+1,-1);

		if(client[0].revents & POLLIN){
			clilen=sizeof(cliaddr);
			connfd=Accept(listenfd,(sockaddr*)&cliaddr,&clilen);
			inet_ntop(AF_INET,&cliaddr.sin_addr,str,sizeof(str));
			cout<<"recived ip: "<<str<<" port :"<<htons(cliaddr.sin_port)<<endl;

			for(i=1;i<OPEN_MAX;i++)
				if(client[i].fd<0){
					client[i].fd=connfd;
					break;
				}

			if(i==OPEN_MAX)
				perr_exit("too many clients");

			client[i].events=POLLIN;
			if(i>maxi)
				maxi=i;
			if(--nready<=0)
				continue;
		}

		for(i=1;i<=maxi;i++){
			if((sockfd=client[i].fd)<0)
				continue;

			if(client[i].revents & POLLIN){
				if((n=Read(sockfd,buf,MAXLINE))<0){
					if(errno==ECONNRESET){
						cout<<"client"<<i<<" aborted connection "<<endl;
						Close(sockfd);
						client[i].fd=-1;
					}else{
						perr_exit("read error");
					}
				}else if(n==0){
					cout<<"client close connection"<<endl;
					Close(sockfd);
					client[i].fd=-1;
				}else{
					cout<<buf<<endl;
					for(j=0;j<n;j++)
						buf[j]=toupper(buf[j]);
					Writen(sockfd,buf,n);
				}
				if(--nready<=0)
					break;
			}
		}
	}
	return 0;
}
