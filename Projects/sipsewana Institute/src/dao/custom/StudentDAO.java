package dao.custom;

import dao.SuperDAO;
import entity.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO extends SuperDAO<Student,String> {
    Student ifCustomerExist(String id) throws SQLException, ClassNotFoundException;
    List<String> getStudentIds() throws SQLException, ClassNotFoundException;

}
