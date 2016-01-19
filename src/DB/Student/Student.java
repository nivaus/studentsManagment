package DB.Student;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Student {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String studentClass;
    private Double grade;

    public Student(String id, String firstName, String lastName, String studentClass, Double grade) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentClass = studentClass;
        this.grade = grade;
    }

    public Student() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }
}
