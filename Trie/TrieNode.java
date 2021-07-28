package Trie;


import Util.NodeInterface;


public class TrieNode<T> implements NodeInterface<T> {
	T val;
	TrieNode<T>[] arr;
	TrieNode<T> paren;
	char ch;
	public TrieNode() {
		arr = new TrieNode[128];
		for(int j=0;j<128;j++) {
			arr[j] = null;
		}
		val = null;
	}
    @Override
    public T getValue() {
        return val;
    }
    
    public void setVal(T val) {
    	this.val = val;
    }

}