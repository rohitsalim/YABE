package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class User extends Model{
	
	public String email;
	public String fullname;
	public String password;
	public boolean isAdmin;

	public User (String email, String fullname, String password){
		this.email = email;
		this.fullname = fullname;
		this.password = password;
	}
	
	public static User connect(String email, String password){
		return User.find("byEmailAndPassword", email, password).first();
	}
	
}
