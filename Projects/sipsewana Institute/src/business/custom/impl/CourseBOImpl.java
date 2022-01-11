package business.custom.impl;

import business.custom.CourseBO;
import dao.DAOFactory;
import dao.custom.CourseDAO;
import dao.custom.RegisterDetailsDAO;
import dao.custom.StudentDAO;
import dao.custom.impl.CourseDAOImpl;
import dao.custom.impl.StudentDAOImpl;
import dto.CourseDTO;
import dto.StudentDTO;
import entity.Course;
import entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseBOImpl implements CourseBO {
    private final CourseDAO courseDAO = (CourseDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.COURSE);
    @Override
    public ArrayList<CourseDTO> getAllCourses() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean addCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException {
        return courseDAO.add(new Course(
                courseDTO.getcID(),
                courseDTO.getName(),
                courseDTO.getDuration(),
                courseDTO.getFee()
        ));
    }

    @Override
    public boolean updateCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public StudentDTO ifCourseExist(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean deleteCourse(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<String> getCourseIds() throws SQLException, ClassNotFoundException {
        return courseDAO.getCourseIds();
    }

    @Override
    public CourseDTO searchCourse(String id) throws SQLException, ClassNotFoundException {
        Course c=courseDAO.search(id);
        return new CourseDTO(c.getcID(),c.getName(),c.getDuration(),c.getFee());
    }
}
