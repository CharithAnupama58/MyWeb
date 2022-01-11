package business.custom;

import business.SuperBO;
import dto.CourseDTO;
import dto.RegisterDetailsDTO;
import dto.StudentDTO;
import entity.Course;
import entity.Student;

import java.sql.SQLException;
import java.util.List;

public interface RegisterDetailsBO extends SuperBO {

    boolean saveRegistrationDetails(RegisterDetailsDTO registerDetailsDTO) throws SQLException, ClassNotFoundException;

    List<CourseDTO> getCourseIds(String id) throws SQLException, ClassNotFoundException;

    String getRegID() throws SQLException, ClassNotFoundException;
}
