int* getNext(string& s) {
	int *next = new int[s.length()];
	next[0] = 0;

	int i = 1, j = 0;
	while (i < s.length()) {
		if (s[i] == s[j]) {
			next[i++] = (++j);
		} else if (0 == j&&s[i] != s[j]) {
			next[i++] = j;
		} else {
			j = next[j - 1];
		}
	}
	return next;
}

int KMP(string s, string p) {
	int* next = getNext(s);
	int j = 0;
	for (int i = 0; i < p.length(); i++) {
		if (p[i] == s[j]) {
			j++;
		} else {
			if (j == 0) {
				continue;
			}
			i--;
			j =next[j - 1];
		}
		if (s.length() == j) {
			delete next;
			return i-s.length()+1;//返回首先查到的s的索引
		}
	}
	delete next;
	return -1;
}
