package struktura;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.TABLE;

@NamedQuery(name="MaloZnaneSlowa.AllMaloZnane", query="select m from MaloZnaneSlowa m")
@Entity
@Table(name = "maloznane")
public class MaloZnaneSlowa implements I_Slowo {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = TABLE)
	private int id;
	@JoinColumn(name = "MALOZNANESLOWO_ID", referencedColumnName = "ID")
	private Slowo maloZnaneSlowo;

	public MaloZnaneSlowa() {
		super();
	}

	public MaloZnaneSlowa(Slowo maloZnaneSlowo) {
		super();
		this.maloZnaneSlowo = maloZnaneSlowo;
	}

	public Slowo getMaloZnaneSlowo() {
		return maloZnaneSlowo;
	}

	public void setMaloZnaneSlowo(Slowo maloZnaneSlowo) {
		this.maloZnaneSlowo = maloZnaneSlowo;
	}

	public boolean exists() {
		return this.maloZnaneSlowo != null;
	}

	public Slowo getSlowoAllg() {
		return getMaloZnaneSlowo();
	}

	public void setSlowoAllg(Slowo s) {
		setMaloZnaneSlowo(s);
	}

}
