package fer.blog;

import java.util.*;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.*;


@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "USER_ID"),@UniqueConstraint(columnNames = "USER_EMAIL")})
public class User {
	Long userId;
	String userName;
	String userEmail;
	Set<Group> groups = new HashSet<Group>(0);
	Set<Entry> entries = new HashSet<Entry>(0);
	Set<Entry> liked = new HashSet<Entry>(0);

	public User(){}
	public User(String userName, String userEmail){
		this.userName = userName;
		this.userEmail = userEmail;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_ID", unique = true, nullable = false)
	public Long getUserId() {
		return userId;
	}
	@SuppressWarnings("unused")
	private void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", nullable = false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "USER_EMAIL", unique = true, nullable = false)
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "GROUP_MEMBERS", joinColumns = {
		@JoinColumn(name = "USER_ID", nullable = false, updatable = false) }, inverseJoinColumns = { 
		@JoinColumn(name = "GROUP_ID", nullable = false, updatable = false) })
	public Set<Group> getGroups() {
		return groups;
	}
	public void setGroups( Set<Group> groups ) {
		this.groups = groups;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
	public Set<Entry> getEntries() {
		return entries;
	}
	public void setEntries( Set<Entry> entries ) {
		this.entries = entries;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "likes")
	public Set<Entry> getLiked() {
		return liked;
	}
	public void setLiked(Set<Entry> liked) {
		this.liked = liked;
	}
	
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!this.getClass().equals(obj.getClass())) return false;

		User obj2 = (User)obj;
		if((this.userId == obj2.getUserId()) && (this.userName.equals(obj2.getUserName())) && (this.userEmail.equals(obj2.getUserEmail())))
		{
			return true;
		}
		return false;
	}
	public int hashCode() {
		int tmp = 0;
		tmp = ( userId + userName + userEmail).hashCode();
		return tmp;
	}
}
