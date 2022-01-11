package business.custom.impl;

import business.custom.RegisterDetailsBO;
import dao.DAOFactory;
import dao.custom.CourseDAO;
import dao.custom.RegisterDetailsDAO;
import dao.custom.impl.CourseDAOImpl;
import dao.custom.impl.RegisterDetailsDAOImpl;
import dto.CourseDTO;
import dto.RegisterDetailsDTO;
import dto.StudentDTO;
import entity.Course;
import entity.RegisterDetails;
import entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FactoryConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterDetailsBOImpl implements RegisterDetailsBO {
    private final RegisterDetailsDAO registerDetailsDAO = (RegisterDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.REGISTERDETAILS);
    @Override
    public boolean saveRegistrationDetails(RegisterDetailsDTO r) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Course course = session.get(Course.class, r.getcID());
        Student student = session.get(Student.class, r.getsID());

        transaction.commit();
        session.close();

        return registerDetailsDAO.add(new RegisterDetails(r.getrId(),course,student,r.getDate()));
    }

    @Override
    public List<CourseDTO> getCourseIds(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Student student = session.get(Student.class, id);

        transaction.commit();
        session.close();
        List<CourseDTO> registerDetailsDTOS=new ArrayList<>();
        List<Course> registerDetails=registerDetailsDAO.getCourseIds(student);
        for (Course course:registerDetails
             ) {
            registerDetailsDTOS.add(new CourseDTO(course.getcID(),course.getName(),course.getDuration(),course.getFee()));
        }
        return registerDetailsDTOS;
    }

    @Override
    public String getRegID() throws SQLException, ClassNotFoundException {
        return registerDetailsDAO.getRegID();
    }
}
