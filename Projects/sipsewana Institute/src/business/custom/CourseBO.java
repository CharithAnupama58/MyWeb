package business.custom;

import business.SuperBO;
import dto.CourseDTO;
import dto.StudentDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CourseBO extends SuperBO {
    ArrayList<CourseDTO> getAllCourses() throws SQLException, ClassNotFoundException;

    boolean addCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException;

    boolean updateCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException;

    StudentDTO ifCourseExist(String id) throws SQLException, ClassNotFoundException;

    boolean deleteCourse(String id) throws SQLException, ClassNotFoundException;

    List<String> getCourseIds() throws SQLException, ClassNotFoundException;

    CourseDTO searchCourse(String id) throws SQLException, ClassNotFoundException;
}
