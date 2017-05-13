package struktura;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.TABLE;

@NamedQuery(name="ZnaneSlowa.AllZnaneSlowa", query="select m from ZnaneSlowa m")
@Entity
@Table(name = "znaneslowa")
public class ZnaneSlowa implements I_Slowo {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = TABLE)
	private int id;
	@JoinColumn(name = "ZNANESLOWO_ID", referencedColumnName = "ID")
	private Slowo znaneSlowo;

	public Slowo getZnaneSlowo() {
		return znaneSlowo;
	}

	public void setZnaneSlowo(Slowo znaneSlowo) {
		this.znaneSlowo = znaneSlowo;
	}
	
	
	public Slowo getSlowoAllg() {
		return getZnaneSlowo();
	}

	public void setSlowoAllg(Slowo s) {
		setZnaneSlowo(s);
	}

}
