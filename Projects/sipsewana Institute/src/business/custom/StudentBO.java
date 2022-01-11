package business.custom;

import business.SuperBO;
import dto.StudentDTO;
import entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface StudentBO extends SuperBO {
    ArrayList<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException;

    boolean addStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException;

    boolean updateStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException;

    StudentDTO ifStudentExist(String id) throws SQLException, ClassNotFoundException;

    boolean deleteStudent(String id) throws SQLException, ClassNotFoundException;

    StudentDTO searchStudent(String id) throws SQLException, ClassNotFoundException;

    List<String> getStudentIds() throws SQLException, ClassNotFoundException;
}
