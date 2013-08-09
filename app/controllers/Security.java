package controllers;

import javax.inject.Inject;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import play.Logger;
import play.cache.Cache;

import models.User;
import javax.naming.directory.Attribute;

public class Security extends Secure.Security {

	private static class AttributeMapper implements AttributesMapper
	{
		@Override
		public Object mapFromAttributes(Attributes attrs) throws NamingException {
						
			String id = attrs.get("uid").get().toString();
			String firstName = "";
			
			String[] names = attrs.get("cn").get().toString().split(" ");
			for(int i=0; i<names.length-1; i++) {
				firstName += names[i];
			}
			
			String lastName = attrs.get("sn").get().toString();
			String email = attrs.get("mail").get().toString();
			String status = "personnel";
			
			String mailDomain = email.split("@")[1];
			if(mailDomain.split(".mines-nantes")[0].equals("etudiant")) {
				status = "etudiant";
			}
			
			System.out.println("id:"+id);
			System.out.println("firstName:"+firstName);
			System.out.println("lastName:"+lastName);
			System.out.println("email:"+email);
			System.out.println("status:"+status);
			
	        Cache.set(session.getId()+"USERID",		id);
	        Cache.set(session.getId()+"FIRSTNAME",  firstName);
	        Cache.set(session.getId()+"LASTNAME",   lastName);
	        Cache.set(session.getId()+"EMAIL",		email);
	        Cache.set(session.getId()+"STATUS",     status);
			
			return null;
		}
	}

	@Inject 
	private static LdapTemplate ldap;
	
	static boolean authenticate(String login, String password) {

		// if the injection didn't worked (eclipse)
		if(ldap==null) {
			ApplicationContext context = new FileSystemXmlApplicationContext("conf/application-context.xml");
			ldap = new LdapTemplate();
			ldap = context.getBean("ldapTemplate", LdapTemplate.class);
		}
		
		if(login.equals("admin") && password.equals("admin")) {
			
			Cache.set(session.getId()+"USERID",		"ADMIN-SODEXO");
	        Cache.set(session.getId()+"FIRSTNAME",  "ADMIN-SODEXO");
	        Cache.set(session.getId()+"LASTNAME",   "ADMIN-SODEXO");
	        Cache.set(session.getId()+"EMAIL",		"ADMIN-SODEXO");
	        Cache.set(session.getId()+"STATUS",     "ADMIN-SODEXO");
			
			return true;
		}
		
		//Rajout d'un utilisateur test
		if (login.equals("test") && password.equals("test")) {
			
			Cache.set(session.getId()+"USERID",		"test");
	        Cache.set(session.getId()+"FIRSTNAME",  "test");
	        Cache.set(session.getId()+"LASTNAME",   "test");
	        Cache.set(session.getId()+"EMAIL",		"test");
	        Cache.set(session.getId()+"STATUS",     "etudiant");
			
			return true;
		}
		
		Boolean reponse = false;

		EqualsFilter filter = new EqualsFilter("uid", login);

		try {
			reponse = ldap.authenticate(DistinguishedName.EMPTY_PATH, filter.encode(), password);
		}
		catch( AuthenticationException e) {
			Logger.info("Mauvais (LOGIN,MDP) pour "+login);
		}
		
		// if the user is correctly logged-in, we explore the properties contained in the LDAP object
		if(reponse) {
			ldap.search(DistinguishedName.EMPTY_PATH, filter.encode(), new AttributeMapper());
		}
		
		
		
		return reponse;
	}
	
	public static boolean check(String profile) {
        String status = Cache.get(session.getId()+"STATUS", String.class);
        if ("admin".equals(profile)) {
            return (status=="ADMIN-SODEXO");
        }
        else {
            return false;
        }
    } 
}
