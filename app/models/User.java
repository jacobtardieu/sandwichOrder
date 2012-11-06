package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class User extends Model {

	@Required
	public int id_user;
	
	@Required
	public String name;
	
	@Required
	public String prenom;
	
	@Required
	public String email;
	
	@Required
	public boolean sodexo;
	
}