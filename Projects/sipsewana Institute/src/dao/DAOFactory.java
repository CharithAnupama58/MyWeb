package dao;

import dao.custom.impl.CourseDAOImpl;
import dao.custom.impl.RegisterDetailsDAOImpl;
import dao.custom.impl.StudentDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDAOFactory() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    //factory method
    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case COURSE:
                return new CourseDAOImpl();
            case STUDENT:
                return new StudentDAOImpl();
            case REGISTERDETAILS:
                return new RegisterDetailsDAOImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        COURSE, STUDENT, REGISTERDETAILS
    }
}
