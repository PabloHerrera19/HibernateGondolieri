package persistencia;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	// Atributos
	@Id
	@NotBlank
	@Size(max = 25)
	private String nick; // PK
	@Column
	@NotBlank
	@Size(max = 25)
	private String nombre;
	@Column
	@NotBlank
	@Size(max = 60)
	private String apellidos;

	@OneToMany(mappedBy = "usuario")
	@Cascade(CascadeType.SAVE_UPDATE)
//	@Valid
	private Set<Opinion> opinionesUsuario;

	// Constructor
	public Usuario() {

	}

	public Usuario(String nick, String nombre, String apellidos) {
		super();
		this.nick = nick;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}
	// get/set/equals/toString

	public String getNick() {
		return nick;
	}

	// ¿Es necesario?
	public void setNick(String nick) {
		// ¿Comprobar si existe o al ser PK lanza excepcion?
		this.nick = nick;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Set<Opinion> getOpinionesUsuario() {
		return opinionesUsuario;
	}

	public void setOpinionesUsuario(Set<Opinion> opinionesUsuario) {
		this.opinionesUsuario = opinionesUsuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
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
		Usuario other = (Usuario) obj;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [nick=" + nick + ", nombre=" + nombre + ", apellidos=" + apellidos + "]";
	}

}
