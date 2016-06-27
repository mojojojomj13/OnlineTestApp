package com.exams.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.exams.domain.Question;
import com.exams.exceptions.DatabaseException;

@Repository(value = "quesDao")
public class QuestionsDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionsDao.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public void addQuestions(Question question) throws DatabaseException {
		try {
			StringBuffer sqlBuffer = new StringBuffer(" INSERT INTO QUESTIONS "
					+ " (QUES_TEXT, QUES_OP1, QUES_OP2, QUES_OP3, QUES_OP4,  QUES_MRKS, CORRECT_OPS)  VALUES ");
			StringBuffer ops = new StringBuffer("'{");
			for (String op : question.getCorrectOps()) {
				ops.append("\"" + op + "\",");
			}
			ops.deleteCharAt(ops.lastIndexOf(","));
			ops.append("}'");
			sqlBuffer.append(" ('" + question.getQuesText() + "', '" + question.getOpA() + "',  '" + question.getOpB()
					+ "' ,  '" + question.getOpC() + "',  '" + question.getOpD() + "', " + question.getMarks() + ", "
					+ ops.toString() + " ) ,");
			sqlBuffer.deleteCharAt(sqlBuffer.lastIndexOf(","));
			System.out.println(sqlBuffer.toString());
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement pst = conn.prepareStatement(sqlBuffer.toString(), Statement.RETURN_GENERATED_KEYS);
			int i = pst.executeUpdate();
			LOGGER.info("update done :: " + i);
			if (i == 1) {
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					LOGGER.info("ID  :: " + rs.getLong(1));
					jdbcTemplate.update(" UPDATE QUESTIONS SET QUES_IMG  =  ? WHERE QUESTION_ID =  ? ",
							new Object[] { question.getQuesImg(), rs.getLong(1) });
				}
			}
		} catch (SQLException | DataAccessException e) {
			throw new DatabaseException(e.toString(), e);
		}
	}

	public List<Question> getAllQuestions() throws DatabaseException {
		try {
			return jdbcTemplate.query(" SELECT * FROM QUESTIONS ", (ResultSetExtractor<List<Question>>) rs -> {
				List<Question> quesList = new ArrayList<Question>();
				while (rs.next()) {
					Question q = new Question();
					q.setQuesText(rs.getString("QUES_TEXT"));
					q.setOpA(rs.getString("QUES_OP1"));
					q.setOpB(rs.getString("QUES_OP2"));
					q.setOpC(rs.getString("QUES_OP3"));
					q.setOpD(rs.getString("QUES_OP4"));
					q.setMarks(rs.getString("QUES_MRKS"));
					Array arr = rs.getArray("CORRECT_OPS");
					ResultSet rset = arr.getResultSet();
					List<String> strList = new ArrayList<String>();
					while (rset.next()) {
						strList.add(rset.getString(1));
					}
					q.setCorrectOps(strList);
					q.setQuesImg((byte[]) rs.getObject("QUES_IMG"));
					if (!quesList.contains(q))
						quesList.add(q);
				}
				return quesList;
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.toString(), e);
		}
	}
}
