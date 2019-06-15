#include<iostream>
#include<vector>
#include<string>
using namespace std;

//进行堆排序 获取根节点的最大值 再和最后一位的节点互换拿出 最大值

void swap(int& n1,int& n2){
	if(n1!=n2){
		n1=n1^n2;
		n2=n1^n2;
		n1=n1^n2;
	}
}

//c测试堆排序
//使用递归的操作
//array使int的数组,size使数组的大小,n使当前的位置
void heapify(int* array,int size,int n){
	//递归的出口
	if(n>=size){
		return ;
	}
	//把n看成一个父节点
	int c1=n*2+1;//左孩子的位置
	int c2=n*2+2;//右孩子的位置
	//三个数的最大叔的索引
	int max=n;

	if(c1<size&&array[c1]>array[max]){
		max=c1;
	}
	if(c2<size&&array[c2]>array[max]){
		max=c2;
	}
	if(max!=n){
		swap(array[n],array[max]);
		heapify(array,size,max);
	}
}

//利用这个函数可以在根结点处获得最大值
//建立堆 把无序的对zh变成有序的堆
void build_heap(int *array,int size){
	//获取z最后的一颗子树
	int parent=size-1-1/2;
	for(int i=parent;i>=0;i--){
		heapify(array,size,i);
	}
}

//现在将最大值与最后的节点交换
void heap_sort(int *array,int size){
	build_heap(array,size);
	for(int i=size-1;i>=0;i--){
		swap(array[0],array[i]);
		//第一个和最后一个交换
		heapify(array,i,0);
	}
}

int main(){
	int array[]={2,5,3,1,10,4};
	heap_sort(array,6);
	for(int i=0;i<6;i++){
		cout<<array[i]<<endl;
	}
}

