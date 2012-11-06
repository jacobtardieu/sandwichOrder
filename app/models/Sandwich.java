package models;


import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Sandwich extends Model {
	
	@Required
	public String name;
	
	@Required
	public String description;

	@Required
	public float prix;
	
	public Sandwich(String name, String description, float prix) {
		this.name = name;
		this.description = description;
		this.prix = prix;
	}
	
	public Sandwich() {}

	public String toString() {
		return name;	
	}
	
	
	
	
}
