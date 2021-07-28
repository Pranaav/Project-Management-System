package ProjectManagement;

public class Job implements Comparable<Job>, JobReport_ {
	String name;
	String pronam;
	String user;
	int runt;
	String status;
	Project pro;
	int prior;
	User us;
	int cometime;
	int index;
	int completime;
	public Job(String[] cmd, Project pro, int index) {
		name = cmd[1];
		pronam = cmd[2];
		user = cmd[3];
		runt = Integer.parseInt(cmd[4]);
		this.pro = pro;
		status = "REQUESTED";
		prior = pro.prior;
		this.index = index;
		cometime =0;
	}
	public Project projec() {
		return pro;
	}
	
    @Override
	public String toString() {
		String s = name;
		return s;
	}
	@Override
    public int compareTo(Job job) {
    	int gx = (pro.priority() - job.projec().priority());
    	if(gx==0) {
    		gx = job.index - this.index;
    	}
        return gx;
    }
	@Override
	public String user() {
		return user;
	}
	@Override
	public String project_name() {
		return pronam;
	}
	@Override
	public int budget() {
		return pro.budg;
	}
	@Override
	public int arrival_time() {
		return cometime;
	}
	@Override
	public int completion_time() {
		return completime;
	}
}