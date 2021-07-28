package RedBlack;


public class RBTree<T extends Comparable, E> implements RBTreeInterface<T, E>  {

	RedBlackNode<T, E> root = null;
	String Black = "Black";
	String Red = "Red";
    @Override
    public void insert(T key, E value) {
    	//RedBlackNode<T, E> nod = new RedBlackNode<T, E>(key, value);
//    	print(root,1,"");
//    	System.out.println();
//    	print(root,2,"");
//    	System.out.println();
//    	print(root,3,"");
//    	System.out.println();
//    	print(root,4,"");
//    	System.out.println();
//    	print(root,5,"");
//    	System.out.println();
    	if(root==null) {
    		root = new RedBlackNode<T, E>(key, value);
    		root.color = Black;
    		//root.left.parent = root;
    		//root.right.parent = root;
    		return;
    	}
    	else {
    		RedBlackNode<T, E> curr = root;
    		RedBlackNode<T, E> uncl = null;
    		RedBlackNode<T, E> paren = null;
    		String currdire = "";
    		while(true) {
    			if(curr==null) {
    				String parendire;
    				curr = new RedBlackNode<T, E>(key, value);
					curr.parent = paren;
					if(currdire=="Left") {
						paren.left = curr;
					}
					else {
						paren.right = curr;
					}
//					System.out.println(curr.parent.key);
					//curr.color = Red;
					while(true) {
						root.color = Black;
	    				if(curr.parent.color==Black) {
	    					//System.out.println("jirjf");
	    					return;
	    				}
	    				//System.out.println(curr.key + paren.color);
	    				if(curr.parent.parent==null) {
//    						System.out.println("jibib");
	    					//uncl = null;
    					}
	    				if(curr.parent.parent.right!=null&&curr.parent.parent.right.key.toString().compareTo(curr.parent.key.toString())==0) {
	    					uncl = curr.parent.parent.left;
	    					parendire = "Right";
	    				}
	    				else {
	    					if(curr.parent.parent==null) {
	    						uncl = null;
	    					}
	    					else {
	    						uncl = curr.parent.parent.right;
	    					}
	    					parendire = "Left";
	    				}
	    				if(uncl==null||uncl.color==Black) {
//	    					System.out.println("qwetyruruoiyeuwuowuouuoeuo");
	    					if(parendire=="Left") {
	    						if(currdire=="Left") {
	    							RedBlackNode<T, E> nod = curr.parent.parent;
	    							if(curr.parent.parent.key.toString().compareTo(root.key.toString())==0){
										root = curr.parent;
										nod.left = root.right;
										root.color = Black;
										nod.color = Red;
										root.right = nod;
										if(nod.left!=null) {
	    									nod.left.parent = nod;
	    								}
										nod.parent = root;
										return;
									}
	    							if(nod.parent.right.key.toString().compareTo(nod.key.toString())==0) {
	    								curr.parent.parent.parent.right = curr.parent;
	    								curr.parent.parent = nod.parent;
	    							}
	    							else {
	    								curr.parent.parent.parent.left = curr.parent;
	    								curr.parent.parent = nod.parent;
	    							}
	    							nod.left = curr.parent.right;
									nod.color = Red;
									if(nod.left!=null) {
										nod.left.parent = nod;
									}
									curr.parent.parent.color = Black;
									curr.parent.right = nod;
									nod.parent = curr.parent;
									return;
	    						}
	    						else {
	    							RedBlackNode<T, E> nod = curr.parent.parent;
	    							RedBlackNode<T, E> node = curr.parent;
	    							node.right = curr.left;
	    							curr.left = node;
	    							node.parent = curr;
	    							nod.left = curr;
	    							RedBlackNode<T, E> mag = curr;
	    							curr = node;
	    							curr.parent = mag;
	    							curr.parent.parent = nod;
	    							node = curr.parent;
	    							nod = curr.parent.parent;
//	    							System.out.println(curr.key + "hioleekpjbninknn");
	    							if(curr.parent.parent.key.toString().compareTo(root.key.toString())==0){
										root = curr.parent;
										nod.left = root.right;
										root.color = Black;
										nod.color = Red;
										root.right = nod;
										nod.parent = root;
										if(nod.left!=null) {
	    									nod.left.parent = nod;
	    								}
//										System.out.println(root.right.key);
//										System.out.println("helloweofjn");
										return;
									}
	    							if(nod.parent.right.key.toString().compareTo(nod.key.toString())==0) {
	    								curr.parent.parent.parent.right = curr.parent;
	    								curr.parent.parent = nod.parent;
	    							}
	    							else {
	    								curr.parent.parent.parent.left = curr.parent;
	    								curr.parent.parent = nod.parent;
	    							}
	    							nod.left = curr.parent.right;
									nod.color = Red;
									if(nod.left!=null) {
										nod.left.parent = nod;
									}
									curr.parent.color = Black;
									curr.parent.right = nod;
									nod.parent = curr.parent;
									return;
	    						}
	    					}
	    					else {
	    						if(currdire=="Right") {
	    							RedBlackNode<T, E> gran = curr.parent.parent;
	    							if(gran.key.toString().compareTo(root.key.toString())==0) {
	    								root = curr.parent;
	    								gran.right = root.left;
	    								if(gran.right!=null) {
	    									gran.right.parent = gran;
	    								}
	    								root.color = Black;
	    								gran.color = Red;
	    								root.left = gran;
	    								gran.parent = root;
	    								return;
	    							}
	    							if(gran.parent.right.key.toString().compareTo(gran.key.toString())==0) {
	    								curr.parent.parent.parent.right = curr.parent;
	    								curr.parent.parent = gran.parent;
	    							}
	    							else {
	    								curr.parent.parent.parent.left = curr.parent;
	    								curr.parent.parent = gran.parent;
	    							}
	    							gran.right = curr.parent.left;
	    							gran.color = Red;
	    							if(gran.right!=null) {
	    								gran.right.parent = gran;
	    							}
	    							curr.parent.left = gran;
	    							curr.parent.color = Black;
	    							gran.parent = curr.parent;
//	    							System.out.println(gran.parent.key + "hikeijeleekpjbninknn");
	    							return;
	    						}
	    						else {
	    							RedBlackNode<T, E> gran = curr.parent.parent;
	    							RedBlackNode<T, E> node = curr.parent;
	    							node.left = curr.right;
	    							curr.right = node;
	    							node.parent = curr;
	    							gran.right = curr;
	    							if(node.left!=null) {
	    								node.left.parent = node;
	    							}
	    							RedBlackNode<T, E> mag = curr;
	    							curr = node;
	    							curr.parent = mag;
	    							curr.parent.parent = gran;
	    							node = curr.parent;
	    							gran = curr.parent.parent;
	    							
	    							if(gran.key.toString().compareTo(root.key.toString())==0) {
	    								root = curr.parent;
	    								gran.right = root.left;
	    								root.color = Black;
	    								gran.color = Red;
	    								root.left = gran;
	    								if(gran.right!=null) {
	    									gran.right.parent = gran;
	    								}
	    								gran.parent = root;
	    								return;
	    							}
	    							if(gran.parent.right.key.toString().compareTo(gran.key.toString())==0) {
	    								curr.parent.parent.parent.right = curr.parent;
	    								curr.parent.parent = gran.parent;
	    							}
	    							else {
	    								curr.parent.parent.parent.left = curr.parent;
	    								curr.parent.parent = gran.parent;
	    							}
	    							gran.right = curr.parent.left;
	    							gran.color = Red;
	    							if(gran.right!=null) {
	    								gran.right.parent = gran;
	    							}
	    							curr.parent.left = gran;
	    							curr.parent.color = Black;
	    							gran = curr.parent;
	    							return;
	    						}
	    					}
	    				}
	    				if(uncl.color==Red) {
//	    					System.out.println("coming soon");
	    					curr.color = Red;
	    					curr.parent.color = Black;
	    					curr.parent.parent.color = Red;
	    					uncl.color = Black;
	    					if(curr.parent.parent.right!=null&&curr.parent.parent.right.key.toString().compareTo(curr.parent.key.toString())==0) {
	    						curr.parent.parent.left.color = Black;
	    					}
	    					else {
	    						if(curr.parent.parent!=null) {
	    							return;
	    						}
	    						curr.parent.parent.right.color = Black;
	    					}
	    					curr = curr.parent.parent;
	    					if(curr.key.toString().compareTo(root.key.toString())==0) {
//	    						root = curr;
	    						curr.color = Black;
	    						return;
	    					}
//	    					System.out.println(curr.parent.key.toString() + "heloojhrhvhvbejvb");
	    					if(curr.parent.left!=null&&curr.parent.left.key.compareTo(curr.key)==0) {
	    						currdire = "Left";
	    					}
	    					else {
	    						currdire = "Right";
	    					}
	    					root.color = Black;
	    					//return;
	    				}
					}
    			}
    			int cu = key.toString().compareTo(curr.key.toString());
    			if(cu<0) {
    				paren = curr;
    				curr = curr.left;
//    				System.out.println("hello");
    				currdire = "Left";
    			}
    			else {
    				if(cu>0) {
    					paren = curr;
    					curr = curr.right;
//    					System.out.println("hello");
    					currdire = "Right";
    				}
    				else {
    					curr.addval(value);
    					return;
    				}
    			}
    		}
    	}
    }
//    public void print(RedBlackNode<T, E> root,int level,String string) {
//    	if(root==null) {
//    		return;
//    	}
//    	else {
//    		if(level<=0) {
//    			return;
//    		}
//    		if(level==1) {
//    			System.out.print(string+root.key.toString() + root.color);
//    			return;
//    		}
//    		if(root.left!=null) {
//    			print(root.left,level-1,string+"L");
//    		}
//    		System.out.print("           ");
//    		if(root.right!=null) {
//    			print(root.right,level-1,string+"R");
//    		}
////    		System.out.println("");
//    	}
//    }
    @Override
    public RedBlackNode<T, E> search(T key) {
    	RedBlackNode<T, E> curr = root;
//    	print(curr,1,"");
//    	System.out.println();
//    	print(curr,2,"");
//    	System.out.println();
//    	print(curr,3,"");
//    	System.out.println();
//    	print(curr,4,"");
//    	System.out.println();
//    	print(curr,5,"");
//    	System.out.println();
//    	print(curr,6,"");
//    	System.out.println();
//    	print(curr,7,"");
//    	System.out.println();
//    	print(curr,8,"");
//    	System.out.println();
//    	print(curr,9,"");
//    	System.out.println();
//    	print(curr,10,"");
//    	System.out.println();
    	while(true) {
    		if(curr==null) {
//    			RedBlackNode<T, E> ti;
    			return new RedBlackNode<T, E>(null, null);
    		}
    		int cu = key.toString().compareTo(curr.key.toString());
    		if(cu<0) {
    			curr = curr.left;
    		}
    		else {
    			if(cu>0) {
    				curr = curr.right;
    			}
    			else {
    				return curr;
    			}
    		}
    	}
    }
}