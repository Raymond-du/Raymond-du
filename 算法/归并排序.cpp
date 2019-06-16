#include<iostream>
using namespace std;

//将两个有序的序列合并成一个有序的序列
//array是一个带归并的数组,l是归并起点的位置 r是右边起点的位置, r_end是右边结束的位置  返 回一个归并排序的数组
void merge(int *array,int l,int r,int r_end){
        //方便后面的while使用
        int l_end=r-1;
        //待排序数组的长度
        int len=r_end-l+1;
        //先new一块合适大的空间
        int* result=new int[len];
        int temp=0;
        while(l<=l_end&&r<=r_end){
                if(array[l]<array[r]){
                        result[temp++]=array[l++];
                }else{
                        result[temp++]=array[r++];
                }
        }
        while(l<=l_end){
                result[temp++]=array[l++];
        }
        while(r<=r_end){
                result[temp++]=array[r++];
        }
        for(int i=0;i<len;i++){
                array[r_end--]=result[--temp];
        }
        delete[] result;
}

void m_sort(int *array,int l,int r_end){
        int center=(l+r_end)/2;
        if(l<r_end){
                m_sort(array,l,center);
                m_sort(array,center+1,r_end);
                merge(array,l,center+1,r_end);
        }
}

int main(){
        int a[]={5, 3, 2, 6, 3, 5, 8,4,0,1};
        m_sort(a,0,8);
        for(int i=0;i<9;i++){
                cout<<a[i]<<endl;
        }
        return 0;
}
