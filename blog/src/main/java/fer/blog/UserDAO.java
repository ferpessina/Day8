package fer.blog;

import java.util.*;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class UserDAO {
	List<User> users = new ArrayList<User>();
	
	public User create(String name, String email){
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx = null;
		User user = null;
		try{
			tx = session.beginTransaction();
//			user = (User) session.createCriteria(User.class).add(Restrictions.eq("userEmail", email)).uniqueResult();
			
			Query query = session.createQuery("select 1 from User t where t.userEmail = :email");
			query.setParameter("email", email);
			user = (User) query.uniqueResult();
			if(user == null){
				user = new User(name, email);
				session.save(user);
			}else{
				return user;
			}
			tx.commit();
		}catch(Exception ex){
			if(tx != null){
				tx.rollback();
			}
			System.out.println(ex);
		}finally{
			session.close();
		}
		return user;
	}

	public void delete(Long userId){
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();	
			User user = (User) session.get(User.class, userId);
			session.delete(user);
			tx.commit();
		}catch(Exception ex){
			if(tx != null){
				tx.rollback();
			}
			System.out.println(ex);
		}finally{
			session.close();
		}
	}

	public void update(User user){

	}

	public User get(Long userId){
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		session.beginTransaction();	
		User user = (User) session.get(User.class, userId);
		session.getTransaction().commit();
		session.close();
		return user;
	}

	public User get(String userEmail){
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		session.beginTransaction();	
		User user = (User) session.createCriteria(User.class).add(Restrictions.eq("userEmail", userEmail)).uniqueResult();;
		session.getTransaction().commit();
		session.close();
		return user;
	}


	@SuppressWarnings("unchecked")
	public List<User> getAll(){
		List<User> users = null;

		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			users = session.createQuery("FROM User").list(); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}

		return users;
	}
}
