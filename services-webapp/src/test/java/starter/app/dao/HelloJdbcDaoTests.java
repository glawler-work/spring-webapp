package starter.app.dao;

import model.UserCredential;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.ResultSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HelloJdbcDaoTests {

    private static final String USERNAME = "username";
    private UserJdbcDao userJdbcDao;
    private @Mock JdbcTemplate jdbcTemplate;
    private @Mock ResultSet resultSet;
    private UserCredential user;

    @Before
    public void onSetUp() {
        userJdbcDao = new UserJdbcDao();
        ReflectionTestUtils.setField(userJdbcDao, "jdbcTemplate", jdbcTemplate);
        user = new UserCredential(USERNAME, "password");
        when(jdbcTemplate.queryForObject(eq("select * from user where username = 'username'"), Matchers.<RowMapper<UserCredential>>any())).thenReturn(user);
    }

    @Test
    public void happyPath() throws Exception {
        assertThat(userJdbcDao.getUserCredential(USERNAME), is(user));
    }
}
