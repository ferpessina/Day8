package fer.blog;

import java.util.Date;
import java.util.logging.Level;

public class Blog 
{
    public static void main( String[] args )
    {
    	java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		EntryDAO entryManager = new EntryDAO();
		UserDAO userManager = new UserDAO();
		User user = userManager.create("Fernando Pessina", "fer.pessina1@gmail.com");
		user = userManager.get("fer.pessina1@gmail.com");
		System.out.println(user.getUserName());
		
		entryManager.create("My first post", "Some random text for my first post", new Date(), user);
//		System.out.println(entry.getTitle());
    }
}
