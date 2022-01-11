package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Attendance;
import model.DaySalary;
import model.Employee;
import model.Salary;
import view.tm.AttendanceTM;
import view.tm.EmployeeTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
    public boolean saveEmployee(Employee e1) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Employee VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, e1.getEmpID());
        stm.setObject(2, e1.getName());
        stm.setObject(3, e1.getAddress());
        stm.setObject(4, e1.getContact());
        stm.setObject(5, e1.getNic());
        return stm.executeUpdate() > 0;
    }

    public Employee getEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Employee WHERE EmpID=?");
        stm.setObject(1, employeeId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );

        } else {
            return null;
        }
    }

    public boolean updateEmployee(Employee e1) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Employee SET EmpName=?, address=?, contact=?, nic=? WHERE EmpID=?");
        stm.setObject(1, e1.getName());
        stm.setObject(2, e1.getAddress());
        stm.setObject(3, e1.getContact());
        stm.setObject(4, e1.getNic());
        stm.setObject(5, e1.getEmpID());
        return stm.executeUpdate() > 0;
    }

    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Employee WHERE EmpID='" + id + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ObservableList<EmployeeTM> getAllEmployees() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Employee");
        ResultSet rst = stm.executeQuery();
        ObservableList<EmployeeTM> employees = FXCollections.observableArrayList();
        while (rst.next()) {
            employees.add(new EmployeeTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));


        }
        return employees;
    }

    public List<String> getEmployeeIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Employee").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public boolean saveAttendance(Attendance at1) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO EmployeeAttendance VALUES(?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, at1.getEmpID());
        stm.setObject(2, at1.getName());
        stm.setObject(3, at1.getDate());
        return stm.executeUpdate() > 0;
    }

    public Attendance getAttendance(String employeeId,String date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM EmployeeAttendance WHERE EmpID=? AND ADate=?");
        stm.setObject(1, employeeId);
        stm.setObject(2, date);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Attendance(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );

        } else {
            return null;
        }
    }

    public boolean saveEmployeeSalary(String employeeId, double salary) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO EmpDaySalary VALUES(?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, employeeId);
        stm.setObject(2, salary);

        return stm.executeUpdate() > 0;
    }

    public DaySalary getSalary(String employeeId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM empdaysalary WHERE EmpID=?");
        stm.setObject(1, employeeId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new DaySalary(
                    rst.getString(1),
                    rst.getDouble(2)

            );

        } else {
            return null;
        }
    }

    public boolean updateSalary(DaySalary d1) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE empdaysalary SET salary=? WHERE EmpID=?");
        stm.setObject(1, d1.getSalary());
        stm.setObject(2, d1.getEmpId());
        return stm.executeUpdate() > 0;
    }

    public ObservableList<AttendanceTM> getAttendanceData(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM employeeattendance WHERE EmpID=?");
        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        ObservableList<AttendanceTM> employeesAttendance = FXCollections.observableArrayList();
        while (rst.next()) {
            employeesAttendance.add(new AttendanceTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
        }
        return employeesAttendance;
    }

    public int getAttendanceCount(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM employeeattendance WHERE EmpID=?");
        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return rst.getInt(1);
        }else{
            return 0;
        }
    }

    public String getSalaryID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT salaryId FROM Salary ORDER BY salaryId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "S-00" + tempId;
            } else if (tempId < 99) {
                return "S-0" + tempId;
            } else {
                return "S-" + tempId;
            }

        } else {
            return "S-001";
        }
    }

    public Salary getMonth(String m,String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Salary WHERE workerID=? AND `month`=?");
        stm.setObject(1, id);
        stm.setObject(2, m);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Salary(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6)
            );

        } else {
            return null;
        }
    }


    public boolean saveEmployeePayment(Salary s1) throws SQLException, ClassNotFoundException {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.
                    prepareStatement("INSERT INTO salary VALUES(?,?,?,?,?,?)");


            stm.setObject(1, s1.getSalaryId());
            stm.setObject(2, s1.getEmpId());
            stm.setObject(3, s1.getMonth());
            stm.setObject(4, s1.getPriceForADay());
            stm.setObject(5, s1.getWorkingDayCount());
            stm.setObject(6, s1.getSalary());

            if (stm.executeUpdate() > 0) {
                if (removeAttendanceDetails(s1.getEmpId(), s1.getMonth())) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                    return false;
                }
            } else {
                con.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {

                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;

    }

    private boolean removeAttendanceDetails(String empId, String month) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM EmployeeAttendance WHERE EmpID='" + empId + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> getSalaryIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM salary").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public Salary getTotalSalary(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM salary WHERE salaryID=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Salary(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6)
            );

        } else {
            return null;
        }
    }

    public boolean updateTotalSalary(Salary s1) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE salary SET priceForADay=?,salary=? WHERE salaryID=?");
        stm.setObject(1, s1.getPriceForADay());
        stm.setObject(2, s1.getSalary());
        stm.setObject(3, s1.getSalaryId());
        return stm.executeUpdate() > 0;
    }
}


 
