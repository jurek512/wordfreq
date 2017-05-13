package struktura;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;


@Entity
@Table(name = "ksiaska")
public class Ksiaska {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	@Column(name = "AUTOR")
	private String autor;
	@Column(name = "TITEL")
	private String titel;
	public int getId() {
		return id;
	}

	public Ksiaska() {
		super();
	}

	public Ksiaska(String autor, String titel) {
		super();
		this.autor = autor;
		this.titel = titel;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	@Override
	public String toString() {
		return "Ksiaska [autor=" + autor + ", id=" + id + ", titel=" + titel
				+ "]";
	}

	public boolean exists() {
		return this.id != 0;
	}
	
}
