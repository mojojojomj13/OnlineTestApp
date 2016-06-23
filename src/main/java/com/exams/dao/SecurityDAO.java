/**
 *
 */
package com.exams.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.exams.exceptions.DatabaseException;
import com.exams.vo.Role;
import com.exams.vo.User;

/**
 * @author Prithvish Mukherjee
 */
@Repository
public class SecurityDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public User getUser(final String userName, final String password) throws DatabaseException {
		try {
			return jdbcTemplate.query(
					" SELECT * FROM USERS U LEFT OUTER JOIN USERS_DETAILS UD ON U.USERNAME = UD.USERNAME "
							+ " WHERE LOWER(U.USERNAME) = LOWER(?) AND U.PASSWORD = ? ",
					new Object[] { userName, password }, (ResultSetExtractor<User>) rs -> {
						if (!rs.next()) {
							return null;
						}
						User user = new User();
						user.setUserName(rs.getString("USERNAME"));
						user.setPassword(rs.getString("PASSWORD"));
						user.setEnabled(rs.getBoolean("ENABLED"));
						user.setFirstName(rs.getString("FIRSTNAME"));
						user.setMiddleName(rs.getString("MIDDLENAME"));
						user.setLastName(rs.getString("LASTNAME"));
						return user;
					});
		} catch (DataAccessException e) {
			throw new DatabaseException("Some error while retrieving User (Security) from DB  :: " + e, e);
		}
	}

	public List<Role> getRoles(final String userName) {
		try {
			return jdbcTemplate.query("SELECT * FROM USER_ROLES WHERE LOWER(USERNAME) = LOWER(?) ",
					new Object[] { userName }, (RowMapper<Role>) (rs, rowNum) -> {
						Role role = new Role();
						role.setName(rs.getString("ROLE"));
						return role;
					});
		} catch (DataAccessException exception) {
			exception.printStackTrace();
		}
		return null;
	}
}
