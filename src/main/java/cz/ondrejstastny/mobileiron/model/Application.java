package cz.ondrejstastny.mobileiron.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Application", catalog = "mobileiron", uniqueConstraints = {
		@UniqueConstraint(columnNames = "Name")})
public class Application {
	private Integer id;
	private String name;
	private String description;
	private Set<Device> devices = new HashSet<Device>(0);
	
	public Application() {
		
	}
	
	public Application(Integer id, String name, String description, Set<Device> devices) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.devices = devices;
	}

	@Column(name = "Description", unique = false, nullable = true, length = 512)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "Name", unique = true, nullable = false, length = 64)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ApplicationId", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "Applications")
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
        if (!(object instanceof Application)) {
            return false;
        }
        Application other = (Application) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.ondrejstastny.mobileiron.model.Application[id=" + id + "]";
    }
	
}
