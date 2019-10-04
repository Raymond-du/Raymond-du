#include<iostream>
#include<vector>
using namespace std;
//获取n以前的质数的数组
vector<int>* prime_array(int n) {	
	vector<int>* vec_arr=new vector<int>();
	if (n < 1) {
		return vec_arr;
	}
	if (n > 2)
		vec_arr->push_back(2);
	for (int i = 3; i <= n; i+=2) {
		int index = 1;
		for (index; index < vec_arr->size(); index++) {
			if (i % vec_arr->at(index) == 0) {
				break;
			}
		}
		if (index == vec_arr->size()) {
			vec_arr->push_back(i);
		}
	}
	return vec_arr;
}
//清空vector的数组 及回收vec
void clean_vector(vector<int>* vec) {
	vec->swap(vector<int>());
	delete vec;
}
//获取n以前的质数之和
int prime_sum(int n) {
	int sum = 0;
	vector<int>* vec = prime_array(n);
	for (int i = 0; i < vec->size(); i++) {
		sum += vec->at(i);
	}
	return sum;
}

int main() {
	int sum = prime_sum(10000);
	cout << sum << endl;
	return 0;
}
