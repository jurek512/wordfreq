package struktura;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.TABLE;

@NamedQuery(name="Slowo.SlowoByName", query="SELECT m FROM Slowo m WHERE m.slowo LIKE :pslowo")
@Entity
@Table(name = "slowo")
public class Slowo {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = TABLE)
	private int id;
	@Column(name = "SLOWO")
	private String slowo;
	public int getId() {
		return id;
	}
	
	public Slowo() {
	}
	
	public Slowo(String slowo) {
		super();
		this.slowo = slowo;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getSlowo() {
		return slowo;
	}
	public void setSlowo(String slowo) {
		this.slowo = slowo;
	}
	@Override
	public String toString() {
		return "Slowo [id=" + id + ", slowo=" + slowo + "]";
	}
	
	public boolean isEmpty() {
		return this.slowo == null || this.slowo.length() == 0;
	}
}
