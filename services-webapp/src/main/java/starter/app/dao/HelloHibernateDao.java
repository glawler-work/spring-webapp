package starter.app.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import model.Hello;

@Repository
public class HelloHibernateDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public String getHello() {
        Object o = sessionFactory.getCurrentSession().get(Hello.class, 1);
        if(o instanceof Hello) {
            return ((Hello)o).getValue();
        }
        throw new RuntimeException();
    }
}
