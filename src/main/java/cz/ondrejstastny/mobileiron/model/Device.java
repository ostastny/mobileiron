package cz.ondrejstastny.mobileiron.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Device", catalog = "mobileiron", uniqueConstraints = {
		@UniqueConstraint(columnNames = "PhoneNumber")})
public class Device {
	private Integer id;
	private String phoneNumber;
	private String operatingSystem;
	private Set<Application> apps = new HashSet<Application>(0);
	private User user;
	
	
	public Device() {
		
	}
	
	public Device(Integer id, String phoneNumber, String operatingSystem, User user, Set<Application> apps) {
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.operatingSystem = operatingSystem;
		this.apps = apps;
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DeviceId", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PhoneNumber", unique = true, nullable = false, length = 32)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	@Column(name = "OS", unique = false, nullable = false, length = 16)
	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "DevicesToApps", catalog = "mobileiron", joinColumns = { 
			@JoinColumn(name = "DeviceId", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "ApplicationId", 
					nullable = false, updatable = false) })
	public Set<Application> getApps() {
		return apps;
	}

	public void setApps(Set<Application> apps) {
		this.apps = apps;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
        if (!(object instanceof Device)) {
            return false;
        }
        Device other = (Device) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.ondrejstastny.mobileiron.model.Device[id=" + id + "]";
    }
}
