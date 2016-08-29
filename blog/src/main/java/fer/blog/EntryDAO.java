package fer.blog;

import java.util.Date;
import java.util.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EntryDAO{
	public boolean create(String title, String text,Date entryDate, User creator){
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		session.beginTransaction();
		
		Entry entry = new Entry(title, text, entryDate, creator);
		creator.getEntries().add(entry);
		
		session.update(creator);
		session.save(entry);
		
		session.getTransaction().commit();
		return true;
	}
	
	public void delete(Long entryId){
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();	
			Entry entry = (Entry) session.get(Entry.class, entryId);
			session.delete(entry);
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
	
	public boolean update(Entry entry){
		return true;
	}
	
	public Entry get(Long entryId){
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		session.beginTransaction();	
		Entry entry = (Entry) session.get(Entry.class, entryId);
		session.getTransaction().commit();
		session.close();
		return entry;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Entry> getAll(){
		List<Entry> entries = null;

		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			entries = session.createQuery("FROM entries").list(); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}

		return entries;
	}
}
