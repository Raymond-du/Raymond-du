#include<iostream>
using namespace std;
typedef char TElemType;//二叉树的存储结构

typedef struct BiTree {
	TElemType data;//数据
	struct BiTree* lchild, *rchild;//左右孩子指针
};
//插入一个元素到二叉树中
void insertBiTree(BiTree** tree, TElemType* elem) {
	if (*tree == NULL) {
		*tree = new BiTree();
		(*tree)->data = *elem;
		return;
	}
	if (*elem <= (*tree)->data) {
		insertBiTree(&(*tree)->lchild, elem);
	} else {
		insertBiTree(&(*tree)->rchild, elem);
	}
}

//把TElemType的数组装换成二叉树
BiTree* createBiTree(TElemType* elems,int len) {
	BiTree* biTree =NULL;
	for (int i = 0; i < len; i++) {
		insertBiTree(&biTree, &elems[i]);
	}
	return biTree;
}
//前序遍历
void preOrderTraversal(BiTree* t) {
	if (NULL!=t) {
		cout << t->data;
		preOrderTraversal(t->lchild);
		preOrderTraversal(t->rchild);
	}
}
//后序遍历
void postOrderTraversal(BiTree* t) {
	if (t != NULL) {
		postOrderTraversal(t->lchild);
		cout << t->data;
		postOrderTraversal(t->rchild);
	}
}
//中序遍历
void inOrderTraversal(BiTree * t) {
	if (NULL != t) {
		inOrderTraversal(t->lchild);
		inOrderTraversal(t->rchild);
		cout << t->data;
	}
}
//获得二叉树的深度
int getDepth(BiTree* biTree) {
	if (biTree == NULL) {
		return 0;
	}
	int l = getDepth(biTree->lchild) + 1;
	int r = getDepth(biTree->rchild) + 1;
	return l > r ? l : r;
}
//统计二叉树的节点个数
int getNodeCount(BiTree* biTree) {
	if (biTree == NULL) {
		return 0;
	}
	int l = getNodeCount(biTree->lchild);
	int r = getNodeCount(biTree->rchild);
	return l + r + 1;
}
//统计二叉树的叶节点个数
int getLeafNodeCount(BiTree* biTree) {
	if (biTree == NULL) {
		return 0;
	}
	if (biTree->lchild == NULL&&biTree->rchild == NULL) {
		return 1;
	}
	int l = getLeafNodeCount(biTree->lchild);
	int r = getLeafNodeCount(biTree->rchild);
	return l + r;
}

void main() {
	TElemType elems[] = "hfsdopoqsfmk";
	BiTree* tree =createBiTree(elems, sizeof(elems)-1);
	inOrderTraversal(tree);
	cout << endl;
	postOrderTraversal(tree);
	cout << endl;
	preOrderTraversal(tree);
	int depth = getDepth(tree);
	int nodeCount = getNodeCount(tree);
	int leafNodeCount = getLeafNodeCount(tree);
}
