package com.studentbase.app;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;

import com.studentbase.app.entity.User;

public class UserSecureContext implements SecurityContext{
	
	// Logger
	final static Logger LOG = Logger.getLogger(AuthentificationFilter.class);

	private User user;
	private Principal principal;
	
    public UserSecureContext(final User user) {
        this.user = user;
        this.principal = new Principal() {

            public String getName() {
            	LOG.info("UserSecureContext: ");
                return user.getLogin();
            }            
            
        };		
    }

	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.BASIC_AUTH;
	}

	@Override
	public Principal getUserPrincipal() {
		return this.principal;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserInRole(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
