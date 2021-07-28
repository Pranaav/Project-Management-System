package PriorityQueue;

public class Pair<T extends Comparable<T>, E extends Comparable<E>> implements Comparable<Pair<T, E>> {
	public T first;
	E second;
	public Pair(T element, E ele) {
		first = element;
		second = ele;
	}
	@Override
	public int compareTo(Pair<T, E> o) {
		int temp = first.compareTo(o.first);
		if(temp==0) {
			temp = o.second.compareTo(second);
		}
		return temp;
	}

	
}
