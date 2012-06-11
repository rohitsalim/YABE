package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import play.db.jpa.Model;

@Entity
public class Post extends Model{
	
	public String title;
	public Date postedAt;
	
	@Lob @Type(type = "org.hibernate.type.TextType")
	public String content;
	
	@OneToMany(mappedBy="post", cascade=CascadeType.ALL)
	
	public List<Comment> comments;
	
	//Many posts to one User
	@ManyToOne
	public User author;
	
	public Post (User author, String title, String content){
		comments = new ArrayList<Comment>();
		this.author = author;
		this.title = title;
		this.content = content;
		this.postedAt = new Date();
	}
	
	public Post addComment(String author, String content){
		Comment commentForPost = new Comment(author, content, this);
		commentForPost.save();
		this.comments.add(commentForPost);
		this.save();
		return this;
	}
	
	public Post previous(){
		return Post.find("postedAt < ? order by postedAt desc", postedAt).first();
	}
	
	public Post next(){
		return Post.find("postedAt > ? order by postedAt asc", postedAt).first();
	}
	
	
}
