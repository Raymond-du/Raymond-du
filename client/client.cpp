#include"wrap_socket.h"
#include<iostream>
#include<sys/types.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include<string.h>
#define PORT 1214
#define DESTIP6 "0:0:0:0:0:0:0:1"
using namespace std;

int main(){
	sockaddr_in6 sa_server;
	int fd_client=Socket(AF_INET6,SOCK_STREAM,0);
	sa_server.sin6_port=htons(PORT);
	sa_server.sin6_family=AF_INET6;
	Inet_pton(AF_INET6,DESTIP6,sa_server.sin6_addr.s6_addr);
	Connect(fd_client,(sockaddr*)&sa_server,sizeof(sa_server));
	
	char buf[1024];
	while(1){
		cin>>buf;
		if(strcmp(buf,"quit")==0){
			break;
		}
		Writen(fd_client,buf,strlen(buf));
	}
	Close(fd_client);
	return 0;
}
