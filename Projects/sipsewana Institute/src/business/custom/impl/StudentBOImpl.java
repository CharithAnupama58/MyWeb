package business.custom.impl;

import business.custom.StudentBO;
import dao.DAOFactory;
import dao.custom.CourseDAO;
import dao.custom.StudentDAO;
import dao.custom.impl.StudentDAOImpl;
import dto.StudentDTO;
import entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    @Override
    public ArrayList<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean addStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        return studentDAO.add(new Student(
                studentDTO.getStId(),
                studentDTO.getStName(),
                studentDTO.getStAddress(),
                studentDTO.getStNic(),
                studentDTO.getStTel()
        ));
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public StudentDTO ifStudentExist(String id) throws SQLException, ClassNotFoundException {
        Student s=studentDAO.ifCustomerExist(id);
        return new StudentDTO(s.getStId(),s.getStName(),s.getStAddress(),s.getStNic(),s.getStTel());
    }

    @Override
    public boolean deleteStudent(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public StudentDTO searchStudent(String id) throws SQLException, ClassNotFoundException {
       Student s=studentDAO.search(id);
       return new StudentDTO(s.getStId(),s.getStName(),s.getStAddress(),s.getStNic(),s.getStTel());

    }

    @Override
    public List<String> getStudentIds() throws SQLException, ClassNotFoundException {
        return studentDAO.getStudentIds();
    }
}
