package PriorityQueue;

public class Student implements Comparable<Student> {
    private String name;
    private Integer marks;

    public Student(String trim, int parseInt) {
    	name = trim;
    	marks = parseInt;
    }


    @Override
    public int compareTo(Student student) {
        return (marks - student.marks);
    }
    
    @Override
	public String toString() {
		// TODO Auto-generated method stub
    	String s = "Student{name='" + name + "', marks=" + marks + "}";
		return s;
	}


	public String getName() {
        return name;
    }
}
