package cz.ondrejstastny.mobileiron.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "User", catalog = "mobileiron", uniqueConstraints = {
		@UniqueConstraint(columnNames = "Name"),
		@UniqueConstraint(columnNames = "Email") })
public class User {
	private Integer id;
	private String name;
	private String email;
	private Set<Device> devices = new HashSet<Device>(0);
	
	
	public User() {
		
	}
	
	public User(Integer id, String name, String email, Set<Device> devices) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.devices = devices;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserId", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(name = "Name", unique = true, nullable = false, length = 128)
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Column(name = "Email", unique = true, nullable = false, length = 128)
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "User")
	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}
	
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: this method won't work when id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.ondrejstastny.mobileiron.model.User[id=" + id + "]";
    }

}
