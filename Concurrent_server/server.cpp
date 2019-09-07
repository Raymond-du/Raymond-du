#include<iostream>
#include<string.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
#include<netinet/in.h>
#include"wrap_socket.h"
#include<sys/stat.h>
#include<fcntl.h>
#include<sys/wait.h>
#include<time.h>
#include<arpa/inet.h>
#include<signal.h>
#define SERVER_PORT 1214
#define LOGDIR "./log"
#define LOGPATH "./log/server.log"

using namespace std;
//获取当前的日期及时间
char *getcurdate(){
	static char curdate[20];
	time_t rawtime;
	tm *ltime;
	time(&rawtime);
	ltime=localtime(&rawtime);
	strftime(curdate,20,"%Y-%m-%d %H:%M:%S",ltime);
	return curdate;
}

void chld_sig(int signu){
	while(wait(NULL)!=0){

	}
}

int main(){
	int fd_server,fd_client;
	sockaddr_in6 sa_server;
	sockaddr_in6 sa_client;
	socklen_t sa_len;
	char buf[1024];
	char ip[64];
	
	signal(SIGCHLD,chld_sig);

	Mkdir(LOGDIR,0755);
	int fd_log=Open(LOGPATH,O_RDWR|O_CREAT|O_APPEND,0644);
	if(dup2(fd_log,STDOUT_FILENO)==-1){
		perr_exit("dup2 file error");
	}
	
	cout<<getcurdate()<<": run server"<<endl;
	fd_server=Socket(AF_INET6,SOCK_STREAM,0);
    sa_server.sin6_family=AF_INET6;
    sa_server.sin6_addr=in6addr_any;
    sa_server.sin6_port=htons(SERVER_PORT);
    Bind(fd_server,(sockaddr*)&sa_server,sizeof(sockaddr_in6));	
	Listen(fd_server,5);

	sa_len=sizeof(sockaddr_in6);
	while(1){
		memset(&sa_client,'\0',sizeof(sa_client));
		fd_client=Accept(fd_server,(sockaddr*)&sa_client,&sa_len);
		inet_ntop(AF_INET6,&sa_client.sin6_addr,ip,64);
		cout<<getcurdate()<<": connect-> ip"<<ip<<" port: "<<htons(sa_client.sin6_port)<<endl;
		int n=fork();
		if(n<0){
			perr_exit("fork error");
		}
		if(n==0){
			//创建了新的进程把不用的文件描述符关闭
			Close(fd_server);
			break;
		}
	}
	//这里执行新的fork语句
	while(1){
		bzero(buf,1024);
		if(0==Read(fd_client,buf,1024)){
			cout<<getcurdate()<<": broke-> "<<ip<<endl;
			Close(fd_client);
			exit(0);
		}
		cout<<getcurdate()<<": recv->"<<ip<<": "<<buf<<endl;
	}
	Close(fd_server);
	return 0;
}

