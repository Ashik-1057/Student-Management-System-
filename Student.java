abstract class Person {
    abstract void displayInfo();
}

public class Student extends Person {
    private int id;
    private String name, dept;
    private double cgpa;
    
    public Student(int id, String name, String dept, double cgpa) {
        this.id = id; this.name = name; this.dept = dept; this.cgpa = cgpa;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDept() { return dept; }
    public double getCgpa() { return cgpa; }
    
    @Override
    void displayInfo() {
        System.out.println("Student: " + name);
    }
}