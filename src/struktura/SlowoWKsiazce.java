package struktura;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.TABLE;


@Entity
@Table(name = "slwowks")
public class SlowoWKsiazce {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = TABLE)
	private int id;
	@JoinColumn(name = "KID_ID", referencedColumnName = "ID")
	private Ksiaska kid;
	@JoinColumn(name = "SLOWO_ID", referencedColumnName = "ID")
	private Slowo slowo;
	@Column(name = "ILERAZYWKSIAZCE")
	private int ileRazyWKsiazce;
	public int getId() {
		return id;
	}

	public SlowoWKsiazce() {
	}
	
	public SlowoWKsiazce(Ksiaska kid, Slowo slowo) {
		super();
		this.kid = kid;
		this.slowo = slowo;
		this.ileRazyWKsiazce = 1;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Ksiaska getKid() {
		return kid;
	}
	public void setKid(Ksiaska kid) {
		this.kid = kid;
	}
	public Slowo getSlowo() {
		return slowo;
	}
	public void setSlowo(Slowo slowo) {
		this.slowo = slowo;
	}
	public int getIleRazyWKsiazce() {
		return ileRazyWKsiazce;
	}
	public void setIleRazyWKsiazce(int ileRazyWKsiazce) {
		this.ileRazyWKsiazce = ileRazyWKsiazce;
	}
	@Override
	public String toString() {
		return "SlowoWKsiazce [id=" + id + ", ileRazyWKsiazce="
				+ ileRazyWKsiazce + ", kid=" + kid + ", slowo=" + slowo + "]";
	}

	public boolean notExists() {
		return this.slowo == null;
	}
}
