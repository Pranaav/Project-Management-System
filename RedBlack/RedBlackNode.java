package RedBlack;

import Util.RBNodeInterface;

import java.util.LinkedList;
import java.util.List;

public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {

	LinkedList<E> obj;
	RedBlackNode<T, E> left, right, parent;
	T key;
	String color;
	public RedBlackNode(T key, E val) {
		obj = new LinkedList<E>();
		if(val==null) {
			obj = null;
		}
		this.key = key;
		if(obj!=null) {
			this.addval(val);
		}
		left = null;
		right = null;
		parent = null;
		color = "Red";
	}
    @Override
    public E getValue() {
        return null;
    }
    public void addval(E val) {
    	obj.addLast(val);
    }
    @Override
    public List<E> getValues() {
        return obj;
    }
}
