package spring.batch.extractor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import spring.batch.vo.UserVO;

public class UserFieldsExtractor implements ItemPreparedStatementSetter<UserVO>
{
	@Override
	public void setValues(UserVO userVO, PreparedStatement stmt) throws SQLException
	{
		stmt.setInt(1, 0);
		stmt.setString(2, userVO.getUserName());
		stmt.setString(3, userVO.getFirstName());
		stmt.setString(4, userVO.getLastName());
		stmt.setString(5, userVO.getEmail());
		stmt.setTimestamp(6, new Timestamp(new Date().getTime()));
	}
}