package fer.blog;

import java.util.*;

import org.hibernate.HibernateException;

public class UserManager {
	private UserDAO userDAO = new UserDAO();
	private EntryDAO entryDAO = new EntryDAO();

	public User createUser(String name, String email){
		try{
			HibernateSessionManager.beginTransaction();
			User user = new User();
			user = userDAO.findByEmail(email);
			if(user == null){
				System.out.println("Creating new user");
				user = new User(name, email);
				userDAO.save(user);
			}
			HibernateSessionManager.commitTransaction();
			return user;
		}catch(HibernateException ex){
			System.out.println("Error in create User");
			return null;
		}
	}
	
	public User findByUserEmail(String email){
		User user = null;
		try{
			HibernateSessionManager.beginTransaction();
			user = userDAO.findByEmail(email);
			HibernateSessionManager.commitTransaction();
		}catch(HibernateException ex){
			System.out.println("Error in findByUserEmail");
			System.out.println(ex);
		}
		return user;
	}
	
	public List<User> findAllUsers(){
		List<User> allUsers = new ArrayList<User>();
		try{
			HibernateSessionManager.beginTransaction();
			allUsers = userDAO.findAll(User.class);
			HibernateSessionManager.commitTransaction();
		}catch(HibernateException ex){
			System.out.println("Error in findAllUsers");
		}
		return allUsers;
	}
	
    public void deleteUser(User user) {
        try {
        	HibernateSessionManager.beginTransaction();
        	user = userDAO.findByID(User.class, user.getUserId());
        	Set<Entry> userEntries = user.getEntries();
        	userEntries.forEach(e -> entryDAO.delete(e));
            userDAO.delete(user);
            HibernateSessionManager.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Error deleting user");
            HibernateSessionManager.rollbackTransaction();
        }
    }
	
    public User findUserById(Long id) {
        User user = null;
        try {
        	HibernateSessionManager.beginTransaction();
            user = (User) userDAO.findByID(User.class, id);
            HibernateSessionManager.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Error finding user by ID");
        }
        return user;
    }
    
    public List<Entry> getUserEntries(User user){
    	List<Entry> entries = null;
    	try {
        	HibernateSessionManager.beginTransaction();
            user = (User) userDAO.findByID(User.class, user.getUserId());
            entries = new ArrayList<Entry>(user.getEntries());
            HibernateSessionManager.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Error getting user entries");
        }
    	return entries;
    }
    
	
}


/*	TODO:
	getUserGroups()
	getUserLikes()
*/