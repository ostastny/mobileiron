package cz.ondrejstastny.mobileiron.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "device", uniqueConstraints = {
		@UniqueConstraint(columnNames = "phoneNumber")})
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
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "phoneNumber", unique = true, nullable = false, length = 32)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	@Column(name = "os", unique = false, nullable = false, length = 16)
	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "application_device", joinColumns = { 
			@JoinColumn(name = "devices_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "application_id", 
					nullable = false, updatable = false) })
	public Set<Application> getApps() {
		return apps;
	}

	public void setApps(Set<Application> apps) {
		this.apps = apps;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
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
