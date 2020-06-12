package persistencia;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "opinion")
public class Opinion implements Serializable {

	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int codigoOpinion;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE) // Si borra la opinion, no se borra la pizza
	@JoinColumn(name = "codigoPizza")
	@Valid
	private Pizza pizza;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "nick")
	@Valid
	private Usuario usuario;

	@Column
	@NotBlank
	@Size(max = 100) // No ponemos el min porque está validado con NotBlank
	private String textoOpinion;

	@Column
	@NotNull
//	@Size(min = 1, max = 2) // Para que valore del 1 al 10 (2 cifras)
	// MIRAR ANOTACION @MAX/MIN PARA INTEGER, NO SIZE
	private int valoracion;

	@Column
	@NotNull
	private LocalDateTime fecha;

	// Constructor
	public Opinion() {

	}

	public Opinion(Pizza pizza, Usuario usuario, String textoOpinion, int valoracion) {
		super();
		this.pizza = pizza;
		this.usuario = usuario;
		this.textoOpinion = textoOpinion;
		this.valoracion = valoracion;
		this.fecha = LocalDateTime.now();
	}

	// get/set/equals/toString

	public int getCodigoOpinion() {
		return codigoOpinion;
	}

	public Pizza getPizza() {
		return pizza;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public String getTextoOpinion() {
		return textoOpinion;
	}

	public void setTextoOpinion(String textoOpinion) {
		this.textoOpinion = textoOpinion;
	}

	public int getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setCodigoOpinion(int codigoOpinion) {
		this.codigoOpinion = codigoOpinion;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	// ¿Es necesario si actualizamos la opinion?
	public void setFecha(LocalDateTime fecha) {
		// Volveria a hacer el now
		this.fecha = fecha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigoOpinion;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Opinion other = (Opinion) obj;
		if (codigoOpinion != other.codigoOpinion)
			return false;
		return true;
	}

	@Override
//	public String toString() {
//		return "Opinion [codigoOpinion=" + codigoOpinion + ", pizza=" + pizza + ", usuario=" + usuario
//				+ ", textoOpinion=" + textoOpinion + ", valoracion=" + valoracion + ", fecha=" + fecha + "]";
//	}
	// No vale el toString de arriba porque es redundante mostrar la pizza y el
	// usuario en este toString y
	// hacerlo también en el toString de las clases
	public String toString() {
		return "Opinion [codigoOpinion=" + codigoOpinion + ", textoOpinion=" + textoOpinion + ", valoracion="
				+ valoracion + ", usuario " + usuario + ", fecha=" + fecha + "]";
	}
}
