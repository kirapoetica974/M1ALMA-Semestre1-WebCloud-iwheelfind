package apiRest;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Annotation {
    @PrimaryKey
    @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
    Long id;

    @Persistent
    String url;
    @Persistent
    Boolean ascenceur;
    @Persistent
    Boolean wc;
    @Persistent
    Boolean rampe;
    @Persistent
    Boolean parking;
    
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getAscenceur() {
		return ascenceur;
	}
	public void setAscenceur(Boolean ascenceur) {
		this.ascenceur = ascenceur;
	}
	public Boolean getWc() {
		return wc;
	}
	public void setWc(Boolean wc) {
		this.wc = wc;
	}
	public Boolean getRampe() {
		return rampe;
	}
	public void setRampe(Boolean rampe) {
		this.rampe = rampe;
	}
	public Boolean getParking() {
		return parking;
	}
	public void setParking(Boolean parking) {
		this.parking = parking;
	}  
    
}