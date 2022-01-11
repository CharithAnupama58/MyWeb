package business;


import business.custom.impl.CourseBOImpl;
import business.custom.impl.RegisterDetailsBOImpl;
import business.custom.impl.StudentBOImpl;
import dao.custom.impl.StudentDAOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    //factory method
    public SuperBO getBO(BOFactory.BOTypes types) {
        switch (types) {
            case COURSE:
                return new CourseBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            case REGISTERDETAILS:
                return new RegisterDetailsBOImpl();
            default:
                return null;
        }
    }

    public enum BOTypes {
        COURSE, STUDENT, REGISTERDETAILS
    }
}
