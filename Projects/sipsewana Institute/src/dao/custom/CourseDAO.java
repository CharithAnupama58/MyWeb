package dao.custom;

import dao.SuperDAO;
import entity.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseDAO extends SuperDAO<Course,String> {
    List<String> getCourseIds() throws SQLException, ClassNotFoundException;
}

