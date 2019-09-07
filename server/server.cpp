#include"wrap_socket.h"
#include<iostream>
#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
//这个头文件包含了sockaddr
#include<netinet/in.h>
#include<arpa/inet.h>
#include<string.h>
#define PORT 1214
using namespace std;

int main(){
	sockaddr_in6 sa_server;
	sockaddr_in6 sa_client;
	socklen_t salen=sizeof(sockaddr);
	memset(&sa_client,'\0',sizeof(sa_client));

	int fd_server=Socket(AF_INET6,SOCK_STREAM,0);
	sa_server.sin6_family=AF_INET6;
	sa_server.sin6_addr=in6addr_any;
	sa_server.sin6_port=htons(PORT);
	Bind(fd_server,(sockaddr*)&sa_server,sizeof(sockaddr_in6));
	Listen(fd_server,5);
	int fd_client=Accept(fd_server,(sockaddr *)&sa_client,&salen);
	
	char buf[1024];
	ssize_t r_len;
	while((r_len=Read(fd_client,buf,1024))>0){
		buf[r_len]=0;
		cout<<"len="<<r_len<<"\t"<<&buf[0]<<endl;
	}

	close(fd_client);
	Close(fd_server);
	return 0;
}
