package ProjectManagement;

import java.util.ArrayList;
import java.util.LinkedList;

public class User implements Comparable<User>, UserReport_ {

	String name;
	ArrayList<Job> jobli;
	int consume;
	int latesttime;
	public User(String name) {
		this.name = name;
		jobli = new ArrayList<Job>();
		consume = 0;
		latesttime = 0;
	}
	public void addjo(Job jo) {
		jobli.add(jo);
	}
    @Override
    public int compareTo(User user) {
        int lk = consume - user.consume;
        if(lk==0) {
        	lk = latesttime - user.latesttime;
        }
        return lk;
    }
    @Override
	public String user() {
		return name;
	}
	@Override
	public int consumed() {
		return consume;
	}
}
