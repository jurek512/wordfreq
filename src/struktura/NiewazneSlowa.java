package struktura;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.TABLE;

@NamedQuery(name="NiewazneSlowa.AllNiewazneSlowa", query="select m from NiewazneSlowa m")
@Entity
@Table(name = "niewaznesl")
public class NiewazneSlowa implements I_Slowo {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = TABLE)
	private int id;
	@JoinColumn(name = "NIEWAZNESLOWO_ID", referencedColumnName = "ID")
	private Slowo niewazneSlowo;

	public Slowo getNiewazneSlowo() {
		return niewazneSlowo;
	}

	public void setNiewazneSlowo(Slowo niewazneSlowo) {
		this.niewazneSlowo = niewazneSlowo;
	}

	public Slowo getSlowoAllg() {
		return getNiewazneSlowo();
	}

	public void setSlowoAllg(Slowo s) {
		setNiewazneSlowo(s);
	}

}
