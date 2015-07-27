package starter.app.dao;


import model.Hello;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HelloHibernateDaoTests {

    private static final String VALUE = "value";
    private HelloHibernateDao helloHibernateDao;
    private @Mock SessionFactory sessionFactory;
    private @Mock Session session;
    private Hello hello;

    @Before
    public void onSetUp() {
        helloHibernateDao = new HelloHibernateDao();
        hello = new Hello();
        hello.setValue(VALUE);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        ReflectionTestUtils.setField(helloHibernateDao, "sessionFactory", sessionFactory);
    }

    @Test
    public void happyPath() throws Exception {
        when(session.get(Hello.class, 1)).thenReturn(hello);
        assertThat(helloHibernateDao.getHello(), is(hello.getValue()));
    }

    @Test(expected = RuntimeException.class)
    public void exception() throws Exception {
        when(session.get(Hello.class, 1)).thenReturn("A String");
        helloHibernateDao.getHello();
    }
}
