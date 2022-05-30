package spring.batch.jobprocessor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.ldap.core.LdapAttributes;

import spring.batch.vo.UserVO;

public class UsersExtractJobProcessor implements ItemProcessor<LdapAttributes, UserVO>
{
	@Override
	public UserVO process(LdapAttributes items) throws Exception
	{
		UserVO userVO = new UserVO();
		
		userVO.setUserName(items.get("uid").toString().split(":")[1].trim());

		if (items.get("sn") != null)
		{
			userVO.setLastName(items.get("sn").toString().split(":")[1]);
		}

		if (items.get("givenName") != null)
		{
			userVO.setFirstName(items.get("givenName").toString().split(":")[1]);
		}

		if (items.get("mail") != null)
		{
			userVO.setEmail(items.get("mail").toString().split(":")[1]);
		}
		
		return userVO;
	}
}