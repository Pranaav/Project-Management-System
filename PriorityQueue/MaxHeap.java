package PriorityQueue;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Iterator;

public class MaxHeap<T extends Comparable<T>> implements PriorityQueueInterface<T> {


	public ArrayList<Pair<T,Integer>> arr = new ArrayList<Pair<T,Integer>>();
	int size = 1;
	int nxtind =0;
	int find = 0;
	@Override
	public void insert(T element) {
		arr.add(new Pair<T, Integer>(element, size));
		find = arr.size() - 1;
		while(true) {
			if(arr.get(find).compareTo(arr.get((find-1)/2))>0) {
				Pair<T, Integer> ele = arr.get(find);
				arr.set(find, arr.get((find-1)/2));
				arr.set((find-1)/2, ele);
				find = (find-1)/2;
			}
			else {
				break;
			}
		}
		size++;
		return;
	}

	@Override
	public T extractMax() {
		find = arr.size() - 1;
		if(find<0) {
			return null;
		}
		Pair<T, Integer> ele = arr.get(0);
		arr.set(0, arr.get(find));
		arr.set(find , ele);
		Pair<T, Integer> ans = arr.remove(find);
		find = 0;
		while(true) { 
			int er = (2*find) + 1;
			int or = (2*find) + 2;
			if(arr.size()<=er||arr.size()<or) {
				break;
			}
			if(arr.size()==or) {
				if(arr.get(find).compareTo(arr.get(er))<0) {
					ele = arr.get(find);
					arr.set(find, arr.get((2*find) +1));
					arr.set((2*find) + 1, ele);
				}
				break;
			}
			int fir = arr.get(er).compareTo(arr.get(find));
			int sec = arr.get(or).compareTo(arr.get(find));
			if(fir>0) {
				if(arr.get(er).compareTo(arr.get(or))>0) {
					ele = arr.get(find);
					arr.set(find, arr.get((2*find) +1));
					arr.set((2*find) + 1, ele);
					find = (2*find) + 1;
				}
				else {
					ele = arr.get(find);
					arr.set(find, arr.get((2*find) + 2));
					arr.set((2*find) + 2, ele);
					find = (2*find) + 2;
				}
			}
			else {
				if(sec<=0) {
					break;
				}
				else {
					ele = arr.get(find);
					arr.set(find, arr.get((2*find) + 2));
					arr.set((2*find) + 2, ele);
					find = (2*find) + 2;
				}
			}
		}
		return ans.first;
	}
		//    void print() {
		//    	for(int i=0;i<arr.size();i++) {
		//    		System.out.println(arr.get(i)+" "+i);
		//    	}
		//    	System.out.println();
		//    }

}