package dao.custom.impl;

import dao.custom.CourseDAO;
import entity.Course;
import entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl  implements CourseDAO {
    @Override
    public boolean add(Course t) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(t);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Course t) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Course search(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Course course = session.get(Course.class, id);


        transaction.commit();
        session.close();
        return course;
    }

    @Override
    public ArrayList<Course> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getCourseIds() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<String> ids = new ArrayList<>();

        Query students = session.createQuery("SELECT cID FROM Course");
        ids = students.list();

        transaction.commit();

        session.close();
        return ids;
    }
}
