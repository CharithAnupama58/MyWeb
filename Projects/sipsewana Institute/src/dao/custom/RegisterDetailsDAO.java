package dao.custom;

import dao.SuperDAO;
import entity.Course;
import entity.RegisterDetails;
import entity.Student;

import java.sql.SQLException;
import java.util.List;

public interface RegisterDetailsDAO extends SuperDAO<RegisterDetails,String> {
    List<Course> getCourseIds(Student student) throws SQLException, ClassNotFoundException;
    String getRegID() throws SQLException, ClassNotFoundException;
}
