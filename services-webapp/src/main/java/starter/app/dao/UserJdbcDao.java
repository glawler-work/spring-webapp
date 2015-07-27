package starter.app.dao;

import model.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

@Repository
public class UserJdbcDao {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    protected static final String queryPattern = "select * from user where username = ''{0}''";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public UserCredential getUserCredential(String username) {

        return jdbcTemplate.queryForObject(MessageFormat.format(queryPattern, username), new RowMapper<UserCredential>() {
            @Override
            public UserCredential mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new UserCredential(rs.getString(USERNAME), rs.getString(PASSWORD));
            }
        });
    }
}
