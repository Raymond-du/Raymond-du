#include<iostream>
#include<string.h>
#include<vector>
using namespace std;

#define M 3 //资源块的个数
#define N 4 //任务的数量
int resource[M]; //每个资源块的大小
int available[M]; //每个资源块的可获得大小
int claim[N][M];  //n个任务 对每个资源块的需求
int alloc[N][M];  //n个任务 对每个资源块以获得大小
int need[N][M];  //n个任务 对每个资源块还需要的大小

int main(){
	cout<<"请输入每个任务对每个资源块的需求"<<endl;
	for(int i=0;i<N;i++){
		for(int j=0;j<M;j++){
			cin>>claim[i][j];
		}
	}
	cout<<"请输入每个任务对已获得每个资源块的大小"<<endl;
	for(int i=0;i<N;i++){
		for(int j=0;j<M;j++){
			cin>>alloc[i][j];
		}
	}
	cout<<"每个任务对每个资源块还需的大小"<<endl;
	for(int i=0;i<N;i++){
		for(int j=0;j<M;j++){
			need[i][j]=claim[i][j]-alloc[i][j];
			cout<<need[i][j]<<"\t";
		}
		cout<<endl;
	}
	cout<<"请输入每个资源块的大小"<<endl;
	for(int i=0;i<M;i++){
		cin>>resource[i];
	}
	int alloc_sum[M];//每个资源已经获取的大小
	bzero(alloc_sum,4*M);
	for(int i=0;i<M;i++){
		for(int j=0;j<N;j++){
			alloc_sum[i]+=alloc[j][i];
		}
	}
	cout<<"每个资源块的可获得大小"<<endl;
	for(int i=0;i<M;i++){
		available[i]=resource[i]-alloc_sum[i];
		if(available[i]<0){
			cout<<"任务资源分配不正确，资源总值比已分配的资源小"<<endl;
			return 0;
		}
		cout<<available[i]<<"\t";
	}
	cout<<endl;
	vector<int> ans;
	int is_ok=0;
	int vis[N];
	bzero(vis,4*N);
	while(1){
		if(ans.size()==N){
			is_ok=1;
			break;
		}

		int index;
		int flag;
		for(int i=0;i<N;i++){
			int judge=1;
			if(!vis[i]){
				for(int j=0;j<M;j++){
					if(available[j]<need[i][j]){
						judge=0;
						break;
					}
				}
			}else{
				continue;
			}
			if(judge){
				index=i;
				flag=1;
				break;
			}
		}
		if(flag){
			vis[index]=1;
			ans.push_back(index);
			for(int k=0;k<M;k++){
				available[k]+=alloc[index][k];
			}
		}else{
			cout<<"死锁状态，无法分配"<<endl;
			return 0;
		}

	}
	if(is_ok){
		cout<<"安全序列是："<<endl;
		for(unsigned int i=0;i<ans.size();i++){
			cout<<ans[i]+1<<"\t";
		}
		cout<<endl;
	}
	return 0;
}
