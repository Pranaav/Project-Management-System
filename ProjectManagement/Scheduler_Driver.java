package ProjectManagement;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import PriorityQueue.MaxHeap;
import PriorityQueue.Pair;
import PriorityQueue.PriorityQueueDriverCode;
import RedBlack.RBTree;
import RedBlack.RedBlackNode;
import Trie.Trie;
import Trie.TrieNode;

//import java.io.*;
//import java.net.URL;
//import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
public class Scheduler_Driver extends Thread implements SchedulerInterface {
	static ArrayList<Project> listproj = new ArrayList<Project>(); 
	static Trie<Project> protri = new Trie<Project>();
    static MaxHeap<Job> joheap = new MaxHeap<Job>();
    static ArrayList<User> listuser = new ArrayList<User>();
    //static HashMap<String,User> map;
    static RBTree<String, Job> rbtr = new RBTree<String, Job>();
    static Trie<Job> jobtri = new Trie<Job>();
    static int jobcount = 0;
    static int jolop = 0;
    static int globtime = 0;
    //static RBTree<String, Job> priorjob;
    static Trie<User> usertri = new Trie<User>();
    //static RBTree<String, Job> rbglot;
    static LinkedList<Job> joli = new LinkedList<Job>();
    static MaxHeap<Project> prohep = new MaxHeap<Project>();
//    joheap = new MaxHeap<Job>();
	//map = new HashMap<String, User>();
//	rbtr = new RBTree<String, Job>();
//	protri = new Trie<Project>();
//	jobtri = new Trie<Job>();
//	jobcount = 0;
//	globtime = 0;
//	jolop =  0;
//	joqueh = new MaxHeap<Job>();
//	joli = new LinkedList<Job>();
//	usertri = new Trie<User>();
//	listproj = new ArrayList<Project>();
//	listuser = new ArrayList<User>();
    public static void main(String[] args) throws IOException {
//
    	//priorjob = new RBTree<String, Job>();
        Scheduler_Driver scheduler_driver = new Scheduler_Driver();
        File file;
        if (args.length == 0) {
            URL url = Scheduler_Driver.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File commandFile) throws IOException {


        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(commandFile));

            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
                if (cmd.length == 0) {
                    System.err.println("Error parsing: " + st);
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;

                switch (cmd[0]) {
                    case "PROJECT":
                        handle_project(cmd);
                        break;
                    case "JOB":
                        handle_job(cmd);
                        break;
                    case "USER":
                        handle_user(cmd[1]);
                        break;
                    case "QUERY":
                        handle_query(cmd[1]);
                        break;
                    case "": // HANDLE EMPTY LINE
                        handle_empty_line();
                        break;
                    case "ADD":
                        handle_add(cmd);
                        break;
                    //--------- New Queries
                    case "NEW_PROJECT":
                    case "NEW_USER":
                    case "NEW_PROJECTUSER":
                    case "NEW_PRIORITY":
                        timed_report(cmd);
                        break;
                    case "NEW_TOP":
                        qstart_time = System.nanoTime();
                        timed_top_consumer(Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    case "NEW_FLUSH":
                        qstart_time = System.nanoTime();
                        timed_flush( Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    default:
                        System.err.println("Unknown command: " + cmd[0]);
                }

            }


            run_to_completion();
            print_stats();

        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + commandFile.getAbsolutePath());
        } catch (NullPointerException ne) {
            ne.printStackTrace();

        }
    }

    @Override
    public ArrayList<JobReport_> timed_report(String[] cmd) {
        long qstart_time, qend_time;
        ArrayList<JobReport_> res = null;
        switch (cmd[0]) {
            case "NEW_PROJECT":
                qstart_time = System.nanoTime();
                res = handle_new_project(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_USER":
                qstart_time = System.nanoTime();
                res = handle_new_user(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                break;
            case "NEW_PROJECTUSER":
                qstart_time = System.nanoTime();
                res = handle_new_projectuser(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_PRIORITY":
                qstart_time = System.nanoTime();
                res = handle_new_priority(cmd[1]);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
        }

        return res;
    }

    @Override
    public ArrayList<UserReport_> timed_top_consumer(int top) {
    	ArrayList<UserReport_> arr = new ArrayList<UserReport_>();
    	MaxHeap<User> maxuser = new MaxHeap<User>();
    	for(int i=0;i<listuser.size();i++) {
    		maxuser.insert(listuser.get(i));
    	}
    	for(int i=0;i<top;i++) {
    		User use = maxuser.extractMax();
    		if(use==null) {
    			break;
    		}
    		else {
    			arr.add(use);
    		}
    	}
//    	for (int i = 0; i < arr.size(); i++) {
//			System.out.println(arr.get(i).user());
//		}
        return arr;
    }



    @Override
    public void timed_flush(int waittime) {
//    	long qendt;
//    	long qstart;
    	ArrayList<Job> loji = new ArrayList<Job>();
    	int tottim = 0;
    	MaxHeap<Job> hear = new MaxHeap<Job>();
    	MaxHeap<Job> hepa = new MaxHeap<Job>();
//    	ArrayList<Job> executablejob = new ArrayList<Job>();
    	if(joheap!=null&&joheap.arr!=null) {
    		for(int i=0;i<joheap.arr.size();i++) {
        		Job joflus = joheap.arr.get(i).first;
        		int waitti = globtime - joflus.cometime;
        		if(waitti>=waittime) {
        			if(joflus.runt<=joflus.pro.budg) {
//        				executablejob.add(joflus);
        				hear.insert(joflus);
        			}
        			else {
        				hepa.insert(joflus);
        			}
        		}
        		else {
        			hepa.insert(joflus);
        		}
        	}
    		
        	while(true) {
        		Job jog = hear.extractMax();
        		if(jog==null) {
        			break;
        		}
        		if(jog.runt<=jog.pro.budg) {
        			joli.add(jog);
        			tottim += jog.runt;
    				jog.completime = globtime + tottim;
    				jog.us.consume += jog.runt;
    				jog.us.latesttime = jog.completime;
    				jog.status = "COMPLETED";
    				jog.pro.jotr.insert(jog.user, jog);
    				jog.pro.chanbudg(jog.runt);
    				jobcount--;
        		}
        		else {
        			hepa.insert(jog);
        		}
        	}
        	
//        	qstart = System.nanoTime();
        	/*while(true) {
        		Job joflus = joheap.extractMax();
        		if(joflus==null) {
        			break;
        		}
        		else {
        			int waitti = globtime - joflus.cometime;
        			if(waitti>=waittime) {
        				if(joflus.runt<=joflus.pro.budg) {
        					joli.add(joflus);
            				joflus.completime = globtime + tottim;
            				tottim += joflus.runt;
            				joflus.status = "COMPLETED";
            				joflus.pro.chanbudg(joflus.runt);
            				jobcount--;
        				}
        				else {
        					loji.add(joflus);
        				}
        			}
        			else {
        				loji.add(joflus);
        			}
        		}
        	}*/
//        	qendt = System.nanoTime();
//        	System.out.println(qendt - qstart);
//        	qstart = System.nanoTime();
        	joheap = hepa;
        	
//        	for(int i=0;i<loji.size();i++) {
//        		joheap.insert(loji.get(i));
//        	}
        	globtime += tottim;
    	}
    	return;
    }
    

    private ArrayList<JobReport_> handle_new_priority(String s) {
    	//LinkedList<Job> priojo = (LinkedList<Job>) priorjob.search(s).getValues();
//    	for (Iterator iterator = priojo.iterator(); iterator.hasNext();) {
//			Job job = (Job) iterator.next();
//			
//		}
    	int prio = Integer.parseInt(s);
    	ArrayList<JobReport_> jor = new ArrayList<JobReport_>();
    	//ArrayList<Pair<Job,Integer>> arr = joheap.arr;
//    	for(int i=0;i<arr.size();i++) {
//    		if(arr.get(i).first.prior>=prio) {
//    			jor.add(arr.get(i).first);
//    		}
//    	}
    	for(int i=0;i<listproj.size();i++) {
    		if(listproj.get(i).prior>=prio) {
    			for(int j=0;j<listproj.get(i).alljob.size();j++) {
    				Job joriop = listproj.get(i).alljob.get(j);
    				if(joriop.status.compareTo("COMPLETED")!=0) {
    					jor.add(joriop);
    				}
    			}
    		}
    	}
//    	for (int i = 0; i < jor.size(); i++) {
//			System.out.println(jor.get(i));
//		}
        return jor;
    }

//    ArrayList<Long> times = new ArrayList<Long>();
    
    private ArrayList<JobReport_> handle_new_projectuser(String[] cmd) {
//    	times = new ArrayList<Long>();
//    	long qstarttime;
//    	long qendtime;
//    	qend_time = System.nanoTime();
//    	times.add(qend_time - qstart_time);
//    	qstart_time = System.nanoTime();
    	
    	ArrayList<JobReport_> arr = new ArrayList<JobReport_>();
    	Project nod = (Project) protri.search(cmd[1]).getValue();
    	List<Job> jopr = nod.jotr.search(cmd[2]).getValues();
    	//System.out.println(jopr);
    	List<Job> jotripi = nod.jobrb.search(cmd[2]).getValues();
    	//System.out.println(jotripi);
//    	ArrayList<Job> jopr = new ArrayList<Job>(nod.getValue().jobrb.search(cmd[2]).getValues());
    	int inti = Integer.parseInt(cmd[3]);
    	int outti = Integer.parseInt(cmd[4]);
    	//Trie<Job> joie = new Trie<Job>();
//    	qstarttime = System.nanoTime();
    	if(jopr!=null) {
    		Iterator<Job> it;
        	it = jopr.iterator();
//        	qendtime = System.nanoTime();
//        	System.out.println(qendtime - qstarttime);
        	while (it.hasNext()) {
//    			Job = (Job) it.next();
    			Job job = it.next();
    			if(job.cometime>=inti) {
        			if(job.cometime<=outti) {
        				arr.add(job);
        			}
        		}
    		}
    	}
//    	for (Iterator<Job> iterator = jopr.iterator(); iterator.hasNext();) {
//			Job job = iterator.next();
//			if(job.cometime>=inti) {
//    			if(job.completime<=outti) {
//    				arr.add(job);
//    			}
//    		}
//			//joie.insert(job.name, job);
//		}
    	if(jotripi!=null) {
    		for (Iterator<Job> itera = jotripi.iterator(); itera.hasNext();) {
    			Job job = itera.next();
    			if(job.status.compareTo("COMPLETED")!=0) {
    				if(job.cometime>=inti) {
    	    			if(job.cometime<=outti) {
    	    				arr.add(job);
    	    			}
    	    			else {
    	    				break;
    	    			}
    	    		}
    			}
    		}
    	}
//    	qend_time = System.nanoTime();
//    	times.add(qend_time - qstart_time);
//    	qstart_time = System.nanoTime();
//    	for(int i=0;i<jopr.size();i++) {
//    		Job jo = jopr.get(i);
//    		if(jo.cometime>=inti) {
//    			if(jo.completime<=outti) {
//    				arr.add(jo);
//    			}
//    			else {
//    				break;
//    			}
//    		}
//    	}
//    	System.out.println(jopr.size());
//    	for (int i = 0; i < arr.size(); i++) {
//			System.out.println(arr.get(i));
//		}
        return arr;
    }

    private ArrayList<JobReport_> handle_new_user(String[] cmd) {
    	ArrayList<JobReport_> arr = new ArrayList<JobReport_>();
    	TrieNode<User> nod = usertri.search(cmd[1]);
    	User use = nod.getValue();
    	int inti = Integer.parseInt(cmd[2]);
    	int outti = Integer.parseInt(cmd[3]);
    	ArrayList<Job> jopr = use.jobli;
    	for(int i=0;i<jopr.size();i++) {
    		Job jo = jopr.get(i);
    		if(jo.cometime>=inti) {
    			if(jo.cometime<=outti) {
    				arr.add(jo);
    			}
    			else {
    				break;
    			}
    		}
    	}
//    	for (int i = 0; i < arr.size(); i++) {
//			System.out.println(arr.get(i));
//		}
        return arr;
    }

    private ArrayList<JobReport_> handle_new_project(String[] cmd) {
    	ArrayList<JobReport_> arr = new ArrayList<JobReport_>();
    	TrieNode<Project> nod = protri.search(cmd[1]);
    	Project pro = nod.getValue();
    	int inti = Integer.parseInt(cmd[2]);
    	int outti = Integer.parseInt(cmd[3]);
    	ArrayList<Job> jopr = pro.alljob;
    	for(int i=0;i<jopr.size();i++) {
    		Job jo = jopr.get(i);
    		if(jo.cometime>=inti) {
    			if(jo.cometime<=outti) {
    				arr.add(jo);
    			}
    			else {
    				break;
    			}
    		}
    	}
//    	for (int i = 0; i < arr.size(); i++) {
//			System.out.println(arr.get(i));
//		}
        return arr;
    }




    public void schedule() {
            execute_a_job();
    }

    public void run_to_completion() {
    	System.out.println("Running code");
		System.out.println("Remaining jobs: " + jobcount);
		int u = 1;
		while(true) {
    		Job job = joheap.extractMax();
//    		System.out.println(job);
    		if(job==null) {
    			break;
    		}
    		if(u==1) {
    			System.out.println("Executing: " + job.name + " from: " + job.pronam);
    		}
    		if(job.runt<=job.pro.budg) {
    			if(u==0) {
    				System.out.println("Running code");
            		System.out.println("Remaining jobs: " + jobcount);
            		System.out.println("Executing: " + job.name + " from: " + job.pronam);
    			}
    			job.pro.chanbudg(job.runt);
//        		System.out.println("Executing: " + job.name + " from: " + job.pronam);
    			System.out.println("Project: " + job.pronam + " budget remaining: " + job.pro.budg);
//    			System.out.println("Execution cycle completed");
    			jobcount--;
    			job.status = "COMPLETED";
    			System.out.println("System execution completed");
    			u = 0;
    			joli.add(job);
    			globtime += job.runt;
    			job.completime = globtime;
    			User us = (User) usertri.search(job.user).getValue();
    			us.consume += job.runt;
    			us.latesttime = job.completime;
    			job.pro.jotr.insert(job.user, job);
    		}
    		else {
    			if(u==0) {
    				System.out.println("Running code");
            		System.out.println("Remaining jobs: " + jobcount);
            		System.out.println("Executing: " + job.name + " from: " + job.pronam);
    			}
    			rbtr.insert(job.pronam, job);
    			System.out.println("Un-sufficient budget.");
    			jobcount--;
    			u = 1;
    		}
    	}
    }

    public void print_stats() {
    	System.out.println("--------------STATS---------------");
    	System.out.println("Total jobs done: " + joli.size());
    	int time = 0;
    	while(!joli.isEmpty()) {
    		Job jo = joli.remove();
    		time += jo.runt;
    		System.out.println("Job{user='" + jo.user + "', project='" + jo.pronam + "', jobstatus=" + jo.status + ", execution_time=" + jo.runt + ", end_time=" + jo.completime + ", name='" + jo.name + "'}");
    	}
    	System.out.println("------------------------");
    	System.out.println("Unfinished jobs: ");
    	int x =0;
    	while(true) {
//    		Job jo = joqueh.extractMax();
    		Project po = prohep.extractMax();
    		if(po==null) {
    			break;
    		}
    		for(int i=0;i<po.alljob.size();i++) {
    			Job jo = po.alljob.get(i);
    			if(jo.status!="COMPLETED") {
        			x++;
        			System.out.println("Job{user='" + jo.user + "', project='" + jo.pronam + "', jobstatus=" + jo.status + ", execution_time=" + jo.runt + ", end_time=" + "null" + ", name='" + jo.name + "'}");
        		}
    		}
    	}
    	System.out.println("Total unfinished jobs: " + x);
    	System.out.println("--------------STATS DONE---------------");
    }

    public void handle_add(String[] cmd) {
    	System.out.println("ADDING Budget");
    	Project pro = (Project) protri.search(cmd[1]).getValue();
    	pro.budg = pro.budg + Integer.parseInt(cmd[2]);
    	LinkedList<Job> lis = (LinkedList<Job>) rbtr.search(pro.nam).getValues();
    	if(lis!=null) {
    		while(!lis.isEmpty()) {
        		Job jok = lis.remove();
        		joheap.insert(jok);
        		jobcount++;
        	}
    	}
    }

    public void handle_empty_line() {
       schedule();
    }


    public void handle_query(String key) {
    	TrieNode<Job> jo = jobtri.search(key);
    	System.out.println("Querying");
    	if(jo==null) {
    		System.out.println(key + ": NO SUCH JOB");
    	}
    	else {
    		if(jo.getValue().status=="COMPLETED") {
    			System.out.println(key + ": COMPLETED");
    		}
    		else {
    			System.out.println(key + ": NOT FINISHED");
    		}
    	}
    }

    public void handle_user(String name) {
    	System.out.println("Creating user");
    	User user = new User(name);
    	//map.put(name, user);
    	usertri.insert(name, user);
    	listuser.add(user);
    }

    public void handle_job(String[] cmd) {
    	System.out.println("Creating job");
    	TrieNode<User> noddy = usertri.search(cmd[3]);
    	if(noddy==null) {
    		System.out.println("No such user exists: " + cmd[3]);
    		return;
    	}
    	TrieNode<Project> nod = protri.search(cmd[2]);
    	if(nod==null) {
    		System.out.println("No such project exists. " + cmd[2]);
    		return;
    	}
    	Project pro = nod.getValue();
    	Job jo = new Job(cmd, pro, jolop);
    	jo.cometime = globtime;
    	jolop++;
    	joheap.insert(jo);
    	jobtri.insert(jo.name, jo);
//    	joqueh.insert(jo);
    	noddy.getValue().addjo(jo);
    	pro.jobrb.insert(noddy.getValue().name, jo);
    	//pro.assignuser(noddy.getValue());
    	//String h = jo.cometime + "";
    	pro.addjob(jo);
    	jo.us = noddy.getValue();
    	//String h = pro.budg + "";
    	//priorjob.insert(h, jo);
    	//rbglot.insert(h, jo);
    	jobcount++;
    }

    public void handle_project(String[] cmd) {
    	System.out.println("Creating project");
    	Project pro = new Project(cmd);
    	protri.insert(cmd[1], pro);
    	prohep.insert(pro);
    	listproj.add(pro);
    }

    public void execute_a_job() {
    	System.out.println("Running code");
    	System.out.println("Remaining jobs: " + jobcount);
    	while(true) {
    		Job job = joheap.extractMax();
//    		System.out.println(job);
    		if(job==null) {
    			break;
    		}
    		System.out.println("Executing: " + job.name + " from: " + job.pronam);
    		if(job.runt<=job.pro.budg) {
//    			System.out.println(job.pro.budg);
    			job.pro.budg = job.pro.budg - job.runt;
    			System.out.println("Project: " + job.pronam + " budget remaining: " + job.pro.budg);
    			System.out.println("Execution cycle completed");
    			jobcount--;
    			job.status = "COMPLETED";
    			joli.add(job);
    			globtime += job.runt;
    			job.completime = globtime;
    			User us = (User) usertri.search(job.user).getValue();
    			us.consume += job.runt;
    			us.latesttime = globtime;
    			job.pro.jotr.insert(job.user, job);
    			break;
    		}
    		else {
    			rbtr.insert(job.pronam, job);
    			System.out.println("Un-sufficient budget.");
    			jobcount--;
    		}
    	}
    }

	@Override
	public void timed_handle_user(String name) {
		User user = new User(name);
    	//map.put(name, user);
    	usertri.insert(name, user);
    	listuser.add(user);
	}

	@Override
	public void timed_handle_job(String[] cmd) {
		TrieNode<User> noddy = usertri.search(cmd[3]);
    	if(noddy==null) {
//    		System.out.println("No such user exists: " + cmd[3]);
    		return;
    	}
    	TrieNode<Project> nod = protri.search(cmd[2]);
    	if(nod==null) {
//    		System.out.println("No such project exists. " + cmd[2]);
    		return;
    	}
    	Project pro = nod.getValue();
    	Job jo = new Job(cmd, pro, jolop);
    	jo.cometime = globtime;
    	jolop++;
    	joheap.insert(jo);
    	jobtri.insert(jo.name, jo);
//    	joqueh.insert(jo);
    	noddy.getValue().addjo(jo);
    	pro.jobrb.insert(noddy.getValue().name, jo);
    	//pro.assignuser(noddy.getValue());
    	//String h = jo.cometime + "";
    	pro.addjob(jo);
    	jo.us = noddy.getValue();
    	//String h = pro.budg + "";
    	//priorjob.insert(h, jo);
    	//rbglot.insert(h, jo);
    	jobcount++;
	}

	@Override
	public void timed_handle_project(String[] cmd) {
		Project pro = new Project(cmd);
    	protri.insert(cmd[1], pro);
    	prohep.insert(pro);
    	listproj.add(pro);
	}

	@Override
	public void timed_run_to_completion() {
		int u = 1;
		while(true) {
    		Job job = joheap.extractMax();
//    		System.out.println(job);
    		if(job==null) {
    			break;
    		}
//    		if(u==1) {
//    			System.out.println("Executing: " + job.name + " from: " + job.pronam);
//    		}
    		if(job.runt<=job.pro.budg) {
//    			if(u==0) {
//    				System.out.println("Running code");
//            		System.out.println("Remaining jobs: " + jobcount);
//            		System.out.println("Executing: " + job.name + " from: " + job.pronam);
//    			}
    			job.pro.chanbudg(job.runt);
//        		System.out.println("Executing: " + job.name + " from: " + job.pronam);
//    			System.out.println("Project: " + job.pronam + " budget remaining: " + job.pro.budg);
//    			System.out.println("Execution cycle completed");
    			jobcount--;
    			job.status = "COMPLETED";
//    			System.out.println("System execution completed");
    			u = 0;
    			joli.add(job);
    			globtime += job.runt;
    			job.completime = globtime;
    			User us = (User) usertri.search(job.user).getValue();
    			us.consume += job.runt;
    			us.latesttime = globtime;
    			job.pro.jotr.insert(job.user, job);
    		}
    		else {
//    			if(u==0) {
//    				System.out.println("Running code");
//            		System.out.println("Remaining jobs: " + jobcount);
//            		System.out.println("Executing: " + job.name + " from: " + job.pronam);
//    			}
    			rbtr.insert(job.pronam, job);
//    			System.out.println("Un-sufficient budget.");
    			jobcount--;
    			u = 1;
    		}
    	}
	}
    
}
