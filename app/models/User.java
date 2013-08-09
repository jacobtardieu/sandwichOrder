package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class User extends Model {

	@Required
	public String userid;
	
	@Required
	public String firstname;
	
	@Required
	public String lastname;

	@Required
	public String email;
	
	@Required
	public String status;

	public User(String userid, String firstname, String lastname, String email, String status) {
		this.userid = userid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.status = status;
	}

	
}
