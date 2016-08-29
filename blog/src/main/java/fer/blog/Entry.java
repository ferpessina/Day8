package fer.blog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Table(name = "entries", uniqueConstraints = {@UniqueConstraint(columnNames = "ENTRY_ID")})
public class Entry{
	Long entryId;
	String title;
	String text;
	Date entryDate;
	User creator;
	Set<User> likes = new HashSet<User>(0);
	
	public Entry(){}
	
	public Entry(String title, String text,Date entryDate, User creator){
		this.title = title;
		this.text = text;
		this.entryDate = entryDate;
		this.creator = creator;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ENTRY_ID", unique = true, nullable = false)
	public Long getEntryId() {
		return entryId;
	}
	@SuppressWarnings("unused")
	private void setEntryId(Long entryId) {
		this.entryId = entryId;
	}
	
	@Column(name = "TITLE", nullable = false)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "TEXT", nullable = false)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Column(name = "ENTRY_DATE", nullable = false)
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	@Transactional
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATOR", nullable = false)
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "ENTRY_LIKES", joinColumns = {
		@JoinColumn(name = "ENTRY_ID", nullable = false, updatable = false) }, inverseJoinColumns = { 
		@JoinColumn(name = "USER_ID", nullable = false, updatable = false) })
	public Set<User> getLikes() {
		return likes;
	}
	public void setLikes(Set<User> likes) {
		this.likes = likes;
	}
	
	public void print(){
		System.out.println();
		System.out.println(title);
		System.out.println(text);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		System.out.println("Created by \""+creator.getUserName()+"\" on: "+dateFormat.format(entryDate));
//		System.out.println("Tags: "+tags);
	}

}
