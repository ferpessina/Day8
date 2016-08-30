package fer.blog;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
 

public abstract class GenericDAO<T, ID extends Serializable>{
 
    protected Session getSession() {
        return HibernateSessionManager.getSessionFactory().getCurrentSession();
    }
 
    public void save(T entity) {
        Session hibernateSession = this.getSession();
        hibernateSession.saveOrUpdate(entity);
    }
 
    public void merge(T entity) {
        Session hibernateSession = this.getSession();
        hibernateSession.merge(entity);
    }
 
    public void delete(T entity) {
        Session hibernateSession = this.getSession();
        hibernateSession.delete(entity);
    }
 
    @SuppressWarnings("unchecked")
	public List<T> findMany(Query query) {
        List<T> t;
        t = (List<T>) query.list();
        return t;
    }
 
    @SuppressWarnings("unchecked")
	public T findOne(Query query) {
        T t;
        t = (T) query.uniqueResult();
        return t;
    }
 
    @SuppressWarnings("unchecked")
	public T findByID(Class<?> clazz, Long id) {
        Session hibernateSession = this.getSession();
        T t = null;
        t = (T) hibernateSession.get(clazz, id);
        return t;
    }
 
    @SuppressWarnings("unchecked")
	public List<T> findAll(Class<?> clazz) {
        Session hibernateSession = this.getSession();
        List<T> T = null;
        Query query = hibernateSession.createQuery("from " + clazz.getName());
        T = query.list();
        return T;
    }
}