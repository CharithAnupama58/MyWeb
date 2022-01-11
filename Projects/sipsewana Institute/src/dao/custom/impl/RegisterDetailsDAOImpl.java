package dao.custom.impl;

import dao.custom.RegisterDetailsDAO;
import entity.Course;
import entity.RegisterDetails;
import entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterDetailsDAOImpl implements RegisterDetailsDAO {

    @Override
    public boolean add(RegisterDetails t) throws SQLException, ClassNotFoundException {
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
    public boolean update(RegisterDetails t) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public RegisterDetails search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<RegisterDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Course> getCourseIds(Student student) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "SELECT courseID FROM RegisterDetails WHERE studentID=:i_d";
        Query query = session.createQuery(hql);
        query.setParameter("i_d",student);
        List<Course> result = query.list();
        transaction.commit();
        session.close();
        return result;
    }

    @Override
    public String getRegID() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "SELECT rID FROM RegisterDetails ORDER BY rID DESC";
        Query query = session.createQuery(hql);
        List<String> result = query.list();
        transaction.commit();
        session.close();
        for (String id:result
             ) {
            if (id!=null){
                int newRegId=Integer.parseInt(id.replace("R","")) + 1;
                return String.format("R%03d",newRegId);
            }

        }
        return "R001";

    }
}
