package starter.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import starter.app.dao.HelloHibernateDao;

@Service
public class HelloService {

    @Autowired
    protected HelloHibernateDao helloHibernateDao;

    public String getFromDatabase(String method) {
        return helloHibernateDao.getHelloViewLocation();
    }
}
