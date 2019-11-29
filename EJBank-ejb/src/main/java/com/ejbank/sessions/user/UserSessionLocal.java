package com.ejbank.sessions.user;

import javax.ejb.Local;
import com.ejbank.payloads.user.UserPayload;

@Local
public interface UserSessionLocal {
	public UserPayload get(int id) ;
	
	public String getUser(int id) ;

	UserPayload getByLastName(String lastname);
}
