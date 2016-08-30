package fer.blog;

import java.util.*;

import org.hibernate.Query;

public class UserDAO extends GenericDAO<User, Long>{
	List<User> users = new ArrayList<User>();

	public void updateUser(User user){
		
	}

	public User findByEmail(String userEmail){
		User user = null;
		String sql = "select t from User t where t.userEmail = :email";
		Query query = HibernateSessionManager.getSession().createQuery(sql).setParameter("email", userEmail);
		user = findOne(query);
		return user;
	}
	public List<Entry> getUserEntries(User user){
		user = findByID(User.class, user.getUserId());
		List<Entry> entries = new ArrayList<Entry>(user.getEntries());
		return entries;
	}

}
