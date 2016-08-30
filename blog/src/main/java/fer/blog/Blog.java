package fer.blog;

import java.util.Date;
import java.util.logging.Level;
import java.util.*;

public class Blog 
{
    public static void main( String[] args )
    {
    	java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		EntryManager entryManager = new EntryManager();
		UserManager userManager = new UserManager();
		User user = userManager.createUser("Fernando Pessina", "fer.pessina1@gmail.com");
		System.out.println(user.getUserId()+" "+user.getUserName());
		entryManager.createEntry("My first post", "Some random text for my first post", new Date(), user);
		List<Entry> userEntries = userManager.getUserEntries(user);
		userEntries.forEach(e -> System.out.println(e.getTitle()));
    }
}
