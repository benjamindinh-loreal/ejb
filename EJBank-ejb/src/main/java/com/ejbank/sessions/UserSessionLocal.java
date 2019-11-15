package com.ejbank.sessions;

import javax.ejb.Local;

import com.ejbank.payloads.UserPayload;

@Local
public interface UserSessionLocal {
	public UserPayload get(int id) ;
}
