package entity;

import javax.persistence.*;

@Entity
public class RegisterDetails implements SuperEntity {
    @Id
    private String rID;
    @ManyToOne
    @JoinColumn(name = "courseID",referencedColumnName = "cID")
    private Course courseID;
    @ManyToOne
    @JoinColumn(name = "studentID",referencedColumnName = "ID")
    private Student studentID;
    private String date;



    @Override
    public String toString() {
        return "RegisterDetails{" +
                "rID='" + rID + '\'' +
                ", courseID=" + courseID +
                ", studentID=" + studentID +
                ", date='" + date + '\'' +
                '}';
    }

    public RegisterDetails() {
    }

    public RegisterDetails(String rID, Course courseID, Student studentID, String date) {
        this.setrID(rID);
        this.setCourseID(courseID);
        this.setStudentID(studentID);
        this.setDate(date);
    }

    public String getrID() {
        return rID;
    }

    public void setrID(String rID) {
        this.rID = rID;
    }

    public Course getCourseID() {
        return courseID;
    }

    public void setCourseID(Course courseID) {
        this.courseID = courseID;
    }

    public Student getStudentID() {
        return studentID;
    }

    public void setStudentID(Student studentID) {
        this.studentID = studentID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
