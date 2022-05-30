package spring.batch.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DBOperations
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private String tableName;

	public JdbcTemplate getJdbcTemplate()
	{
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public boolean truncate()
	{
		String tableName = this.tableName;

		String truncateTableQuery = "DELETE FROM " + tableName;

		try
		{
			this.jdbcTemplate.execute(truncateTableQuery);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}
}