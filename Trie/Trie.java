package Trie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Trie<T> implements TrieInterface {

	TrieNode<T> root;
	public Trie() {
		root = null;
	}
	@Override
    public void printTrie(TrieNode trieNode) {
		TrieNode curr = trieNode;
		for(int i=0;i<128;i++) {
			if(curr.arr[i]!=null) {
				if(curr.arr[i].getValue()!=null) {
					System.out.println(curr.arr[i].getValue().toString());
				}
				printTrie(curr.arr[i]);
			}
		}
    }

    @Override
    public boolean delete(String word) {
    	if(root==null) {
//    		System.out.println("ERROR DELETING");
    		return false;
    	}
    	TrieNode<T> curr = root;
    	int y = word.length();
    	for(int i=0;i<y;i++) {
    		char c = word.charAt(i);
    		int k = c;
    		if(curr.arr[k]!=null) {
    			if(i==y-1) {
    				if(curr.arr[k].getValue()!=null) {
//    					curr.arr[k] = null;
//    					curr = curr.arr[k];
    					for(int p=i;p>=0;p--) {
    						c = word.charAt(p);
    			    		k = c;
    			    		if(curr==null) {
    			    			curr = curr.paren;
    			    		}
    			    		else {
    			    			curr.arr[k] = null;
    			    			if(curr.getValue()!=null) {
    			    				if(p==i) {
    			    					curr.setVal(null);
    			    				}
    			    				else {
//    			    					System.out.println("jdbnijdb");
//    			    					curr.arr[k] = null;
//    			    					System.out.println("DELETED");
        			    				return true;
    			    				}
        			    		}
//        						curr.arr[k] = null;
        						for(int j=0;j<128;j++) {
        							if(curr.arr[j]!=null&&p!=i) {
//        								System.out.println("kjbnbbj");
//        								System.out.println("DELETED");
        								return true;
        							}
        						}
//        						curr.arr[k] = null;
        						TrieNode<T> nod = curr.paren;
        						curr = nod;
        						if(p!=i) {
        							curr.arr[k] = null;
//        							System.out.println(k);
        						}
//        						curr.arr[k] = null;
    			    		}
    					}
//    					System.out.println("DELETED");
    					return true;
    				}
    				else {
//    					System.out.println("ERROR DELETING");
    					return false;
    				}
    			}
    		}
    		else {
//    			System.out.println("ERROR DELETING");
    			return false;
    		}
    		curr = curr.arr[k];
    	}
//    	System.out.println("ERROR DELETING");
        return false;
    }

    @Override
    public TrieNode search(String word) {
    	if(root==null) {
    		//System.out.println("32");
    		return null;
    	}
    	else {
    		int y = word.length();
    		TrieNode<T> curr = root;
    		for(int i=0;i<y;i++) {
    			char c = word.charAt(i);
    			int k = c;
    			if(curr.arr[k]!=null) {
    				if(i==y-1) {
	    				if(curr.arr[k].getValue()!=null) {
	    					return curr.arr[k];
	    				}
	    				else {
	    					//System.out.println("32");
	    					return null;
	    				}
    				}
    			}
    			else {
    				//System.out.println("32");
    				return null;
    			}
    			curr = curr.arr[k];
    		}
    	}
        return null;
    }

    @Override
    public TrieNode startsWith(String prefix) {
    	if(root==null) {
    		System.out.println("1");
    		return null;
    	}
    	else {
    		int y = prefix.length();
    		TrieNode<T> curr = root;
    		for(int i=0;i<y;i++) {
    			char c = prefix.charAt(i);
    			int k = c;
    			if(curr.arr[k]!=null) {
    				if(i==y-1) {
    					return curr.arr[k];
//    					curr = curr.arr[k];
//    					for(int j=0;j<128;j++) {
//    						if(curr.arr[j]!=null) {
//    							return curr.arr[j];
//    						}
//    					}
//    					return null;
//	    				if(curr.arr[k].getValue()!=null) {
//	    					return curr.arr[k];
//	    				}
//	    				else {
//	    					return null;
//	    				}
    				}
    			}
    			else {
    				return null;
    			}
    			curr = curr.arr[k];
    		}
    	}
        return null;
    }

    @Override
    public boolean insert(String word, Object value) {
    	int y = word.length();
    	//System.out.println("21");
    	if(root==null) {
    		//System.out.println("22");
    		root = new TrieNode();
    	}
    	TrieNode<T> curr = root;
    	for(int i=0;i<y;i++) {
			char c = word.charAt(i);
			int k = c;
			if(curr.arr[k]==null) {
				curr.arr[k] = new TrieNode();
				curr.arr[k].ch = c;
				if(i==y-1) {
					curr.arr[k].setVal((T) value);		//casting;
					return true;
				}
			}
			else if(i==(y-1)) {
				if(curr.arr[k].getValue()==null) {
					curr.arr[k].setVal((T) value);     //casting;
					return true;
				}
				else {
					return false;
				}
			}
			TrieNode<T> pare = curr;
			curr = curr.arr[k];
			curr.paren = pare;
		}
        return false;
    }

    @Override
    public void printLevel(int level) {
//    	TrieNode<T> curr = root;
//    	Queue<TrieNode> q = new LinkedList<TrieNode>();
    	TrieNode<T> curr = root;
    	Queue<TrieNode<T>> q = new LinkedList<TrieNode<T>>();
    	Queue<TrieNode<T>> qu = new LinkedList<TrieNode<T>>();
    	int leve = 1;
//    	System.out.print("Level " + level + ": ");
//    	char c = curr.ch;
//		System.out.print(c + ",");
    	int[] arr = new int[128];
    	for(int i=32;i<127;i++) {
    		arr[i] = 0;
    	}
    	while(true) {
    		
    		for(int i=0;i<128;i++) {
    			if(curr.arr[i]!=null) {
//    				System.out.print("jnrej   ");
    				qu.add(curr.arr[i]);
    			}
    		}
    		if(q.isEmpty()) {
    			if(leve==(level+1)) {
//    				lis.sort(null);
    				int hell = 0;
    				System.out.print("Level " + level + ": ");
    				for(int po=33;po<127;po++) {
    					int y = arr[po];
    					char cg = (char) po;
    					for(int kl=0;kl<y;kl++) {
    						if(hell==1) {
    							System.out.print(",");
    						}
    						System.out.print(cg);
//    						if(kl!=y-1) {
//    							System.out.println(",");
//    						}
    						hell=1;
    					}
    					arr[po] = 0;
    				}
    				System.out.println();
    				return;
    			}
    			for(int po=32;po<127;po++) {
    				arr[po] = 0;
    			}
//    			System.out.println("jjehrjbv       " + qu.size());
    			while(!qu.isEmpty()) {
    				q.add(qu.remove());
    			}
//    			qu.clear();
//    			level++;
//    			System.out.print("Level " + level + ": ");
    			leve++;
//    			System.out.print("jjrng");
	    		if(q.isEmpty()) {
	    			System.out.println();
	    			return;
	    		}
    		}
    		curr = q.remove();
    		int c = curr.ch;
    		arr[c]++;
//    		System.out.print(c + ",");
    	}
    }

    @Override
    public void print() {
    	System.out.println("-------------");
    	System.out.println("Printing Trie");
    	TrieNode<T> curr = root;
    	Queue<TrieNode<T>> q = new LinkedList<TrieNode<T>>();
    	Queue<TrieNode<T>> qu = new LinkedList<TrieNode<T>>();
    	int level = 1;
//    	System.out.print("Level " + level + ": ");
//    	char c = curr.ch;
//		System.out.print(c + ",");
    	int[] arr = new int[128];
    	for(int i=32;i<127;i++) {
    		arr[i] = 0;
    	}
    	while(true) {
    		
    		for(int i=0;i<128;i++) {
    			if(curr.arr[i]!=null) {
//    				System.out.print("jnrej   ");
    				qu.add(curr.arr[i]);
    			}
    		}
    		if(q.isEmpty()) {
    			if(level!=1) {
//    				lis.sort(null);
    				int hell = 0;
    				for(int po=33;po<127;po++) {
    					int y = arr[po];
    					char cg = (char) po;
    					for(int kl=0;kl<y;kl++) {
    						if(hell==1) {
    							System.out.print(",");
    						}
    						System.out.print(cg);
    						hell = 1;
//    						System.out.print(",");
    					}
    					arr[po] = 0;
    				}
    				System.out.println();
    			}
//    			System.out.println("jjehrjbv       " + qu.size());
    			while(!qu.isEmpty()) {
    				q.add(qu.remove());
    			}
//    			qu.clear();
//    			level++;
    			System.out.print("Level " + level + ": ");
    			level++;
//    			System.out.print("jjrng");
	    		if(q.isEmpty()) {
	    			System.out.println();
	    			System.out.println("-------------");
	    			return;
	    		}
    		}
    		curr = q.remove();
    		int c = curr.ch;
    		arr[c]++;
//    		System.out.print(c + ",");
    	}
    }
}