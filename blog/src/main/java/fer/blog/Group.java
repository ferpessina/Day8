package fer.blog;

import java.util.*;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.*;

@Entity
@Table(name = "GROUPS", uniqueConstraints = {@UniqueConstraint(columnNames = "GROUP_ID")})
public class Group {
	Long groupId;
	String groupName;
	Set<User> members = new HashSet<User>(0);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "GROUP_ID", unique = true, nullable = false)
	public Long getGroupId() {
		return groupId;
	}
	@SuppressWarnings("unused")
	private void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	@Column(name = "GROUP_NAME", nullable = false)
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
	public Set<User> getMembers() {
		return members;
	}
	public void setMembers(Set<User> members) {
		this.members = members;
	}
	
}
