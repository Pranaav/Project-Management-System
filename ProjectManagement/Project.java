package ProjectManagement;

import java.util.ArrayList;
import java.util.LinkedList;

import RedBlack.RBTree;

public class Project implements Comparable<Project>{
	String nam;
	int prior;
	int budg;
	ArrayList<Job> alljob;
	RBTree<String, Job> jobrb;
	RBTree<String, Job> jotr;
	//User user;
	Project(String[] cmd){
		nam = cmd[1];
		prior = Integer.parseInt(cmd[2]);
		budg = Integer.parseInt(cmd[3]);
		alljob = new ArrayList<Job>();
		jobrb = new RBTree<String, Job>();
		jotr = new RBTree<String, Job>();
	}
	public void addjob(Job jo) {
		alljob.add(jo);
	}
//	public void assignuser(User user) {
//		this.user = user;
//	}
	public String name() {
		return nam;
	}
	public int priority() {
		return prior;
	}
	public int budget() {
		return budg;
	}
	public void chanbudg(int t) {
		budg = budg - t;
		return;
	}
	@Override
	public int compareTo(Project o) {
		return (prior - o.prior);
	}
	
}
