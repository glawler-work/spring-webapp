package starter.app.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import starter.app.dao.HelloHibernateDao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HelloServiceTests {

    private static final String HELLO = "hello";
    private HelloService helloService;
    @Mock private HelloHibernateDao helloHibernateDao;

    @Before
    public void onSetUp() {
        helloService = new HelloService();
        helloService.helloHibernateDao = helloHibernateDao;
    }

    @Test
    public void happyPath() throws Exception {
        when(helloHibernateDao.getHello()).thenReturn(HELLO);

        assertThat(helloService.getFromDatabase(), is(HELLO));
    }
}
