package models;


import javax.persistence.Entity;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Sandwich extends Model {
	
	@Required
	public String name;
	
	@Required
	@MaxSize(10000)
	public String description;

	@Required
	public float prix;
	
	public boolean disponibilite;
	
	public Sandwich(String name, String description, float prix) {
		this.name = name;
		this.description = description;
		this.prix = prix;
		this.disponibilite=false;
	}
	
	public Sandwich() {}
	

	public String toString() {
		return name;	
	}
	
	public String toJSON(){
		return "{\"name\":\""+name+"\",\"description\":\""+description+"\",\"prix\":"+prix+",\"id\":"+this.getId()+"}";
	}
	
	
}
