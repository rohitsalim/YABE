import org.junit.*;

import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	@Before
	public void setup(){
		Fixtures.deleteDatabase();
	}
	
    @Test
    public void createAndRetrieveUser(){
    	User A = new User("bob@gmail.com", "bob", "xx");
    	A.save();
    	User bob = (User) User.find("byEmail", "bob@gmail.com").fetch(1).get(0);
    	assertNotNull(bob);
    	assertEquals("bob", bob.fullname);
    }
    
    @Test
    public void tryAndConnectAsUser(){
    	User A = new User ("bob@gmail.com", "bob", "secret");
    	A.save();
    	assertNotNull(User.connect("bob@gmail.com", "secret"));
    	assertNull(User.connect("bob@gmail.com", "badpassword"));
    	assertNull(User.connect("tom@gmail.com", "secret"));
    }


    @Test
    public void createPost(){
    	User bob = new User("bob@gmail.com", "bob", "secret");
    	bob.save();
    	
    	Post bobPost = new Post(bob,"My first post", "Hello World");
    	bobPost.save();
    	
    	//To test that the post was created
    	assertEquals(1, Post.count());
    	
    	List<Post> bobPosts = Post.find("byAuthor", bob).fetch();
    	assertEquals(1, bobPosts.size());
    	assertNotNull(bobPosts.get(0));
    	assertEquals(bob, bobPosts.get(0).author);
    	assertEquals("My first post", bobPosts.get(0).title);
    	assertEquals("Hello World", bobPosts.get(0).content);
    	assertNotNull(bobPosts.get(0).date);
    }
    
    @Test
    public void createAndAddComments(){
    	User bob = new User("bob@gmail.com", "bob", "password");
    	bob.save();
    	Post bobPost = new Post(bob, "My first post", "hello world");
    	bobPost.save();
    	bobPost.addComment("Jeff", "Nice post");
    	bobPost.addComment("Tom", "I knew that !");
    	//Now to run the tests
    	
    	//To count things
    	assertEquals(1, User.count());
    	assertEquals(1, Post.count());
    	assertEquals(2, Comment.count());
    	
    	//To retrieve bob's post
    	bobPost = Post.find("byAuthor", bob).first();
    	assertNotNull(bobPost);
    	
    	//To retrieve the comments
    	assertEquals(2, bobPost.comments.size());
    	assertEquals("Jeff", bobPost.comments.get(0).user);
    	
    	//Deleting the post
    	bobPost.delete();
    	
    	//To check if the comments have been deleted
    	assertEquals(1, User.count());
    	assertEquals(0, Post.count());
    	assertEquals(0, Comment.count());
    	
    	
    	
    	
    }
}
