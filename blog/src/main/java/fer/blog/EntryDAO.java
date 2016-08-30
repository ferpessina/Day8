package fer.blog;

import java.util.*;

import org.hibernate.Query;

public class EntryDAO extends GenericDAO<Entry, Long>{
	
	public List<Entry> getLast(int n){
		String sql = "from Entry order by entryId DESC LIMIT :number";
		Query query = HibernateSessionManager.getSession().createQuery(sql).setParameter("number", n);
		return findMany(query);
	}
	
	public List<Entry> findByTitle(String title){
		List<Entry>  entries = null;
		String sql = "select e from Entry e where e.title = :title";
		Query query = HibernateSessionManager.getSession().createQuery(sql).setParameter("title", title);
		entries = findMany(query);
		return entries;
	}
	
	public List<Entry> findByText(String text){
		List<Entry>  entries = null;
		String sql = "select e from Entry e where e.text = :text";
		Query query = HibernateSessionManager.getSession().createQuery(sql).setParameter("text", text);
		entries = findMany(query);
		return entries;
	}
}
