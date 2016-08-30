package fer.blog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;

public class EntryManager {
	private UserDAO userDAO = new UserDAO();
	private EntryDAO entryDAO = new EntryDAO();

	public Entry createEntry(String title, String text,Date entryDate, User creator){
		Entry entry = new Entry(title, text, entryDate, creator);
		try{
			HibernateSessionManager.beginTransaction();
			creator = userDAO.findByID(User.class, creator.getUserId());
			entryDAO.save(entry);
			creator.getEntries().add(entry);
			userDAO.updateUser(creator);
			HibernateSessionManager.commitTransaction();
		}catch(HibernateException ex){
			System.out.println("Error in createEntry");
		}
		return entry;
	}

	public void deleteEntry(Entry entry){
		try{
			HibernateSessionManager.beginTransaction();
			User creator = entry.getCreator();
			creator = userDAO.findByID(User.class, creator.getUserId());
			creator.getEntries().remove(entry);
			entryDAO.delete(entry);
			userDAO.updateUser(creator);
			HibernateSessionManager.commitTransaction();
		}catch(HibernateException ex){
			System.out.println("Error in deleteEntry");
		}
	}

	public Entry getEntry(Long entryId){
		Entry entry = null;
		try{
			HibernateSessionManager.beginTransaction();
			entry = entryDAO.findByID(Entry.class, entryId);
			HibernateSessionManager.commitTransaction();
		}catch(HibernateException ex){
			System.out.println("Error in getEntry");
		}
		return entry;
	}

	public List<Entry> getAllEntries(){
		List<Entry> entries = null;
		try{
			HibernateSessionManager.beginTransaction();
			entries = entryDAO.findAll(Entry.class);
			HibernateSessionManager.commitTransaction();
		}catch(HibernateException ex){
			System.out.println("Error in getAllEntries");
		}
		return entries;
	}

	public List<Entry> getLastEntries(int n){
		List<Entry> entries = new ArrayList<>();
		try{
			HibernateSessionManager.beginTransaction();
			entries = entryDAO.getLast(n);
			HibernateSessionManager.commitTransaction();
		}catch(HibernateException ex){
			System.out.println("Error in getLastEntries");
		}
		return entries;
	}

	public List<Entry> getEntriesByText(String text){
		List<Entry> ret = new ArrayList<>();
		try{
			HibernateSessionManager.beginTransaction();
			ret = entryDAO.findByText(text);
			HibernateSessionManager.commitTransaction();
		}catch(HibernateException ex){
			System.out.println("Error in getEntriesByText");
		}
		return ret;
	}
	
	public List<Entry> getEntriesByTitle(String title){
		List<Entry> ret = new ArrayList<Entry>();
		try{
			HibernateSessionManager.beginTransaction();
			ret = entryDAO.findByText(title);
			HibernateSessionManager.commitTransaction();
		}catch(HibernateException ex){
			System.out.println("Error in getEntriesByTitle");
		}
		return ret;
	}

//	public List<Entry> getEntriesByDate(Date from, Date to){
//		List<Entry> ret = new ArrayList<>();
//
//
//		return ret;
//	}

	public List<Entry> getEntriesByUsers(Set<User> users){
		List<Entry> ret = new ArrayList<>();
		users.forEach(u -> ret.addAll(userDAO.getUserEntries(u)));
		return ret;	
	}
}

// TODO get entries by date