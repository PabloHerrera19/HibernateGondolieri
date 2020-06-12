package persistencia;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "ingrediente")
public class Ingrediente implements Serializable {

	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int codigoIngrediente; // PK

	@Column
	@NotBlank
	@Size(max=25)
	private String nombre; // Bidireccional

	@Enumerated(EnumType.STRING) // Guarda la cadena del enumerado
	@NotNull
	private TipoIngrediente tipoIngrediente;

	@Column
	@NotNull
	@Max(value = 2)
	private double precio;

	@ManyToMany(mappedBy = "ingredientes") // Nombre del set en la clase Pizza
	@Cascade(CascadeType.SAVE_UPDATE) // Para que actualice los precios totales de las pizzas cuando se borren
	@Valid
	private Set<Pizza> pizzas;

	// Constructor
	// Constructor para no me acuerdo el que
	public Ingrediente() {

	}

	public Ingrediente(String nombre, TipoIngrediente tipoIngrediente, double precio) {
		this.nombre = nombre;
		this.tipoIngrediente = tipoIngrediente;
		this.precio = precio;
		this.pizzas = new HashSet<Pizza>();
	}
	// get/set/equals/toString

	public int getCodigoIngrediente() {
		return codigoIngrediente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoIngrediente getTipoIngrediente() {
		return tipoIngrediente;
	}

	public void setTipoIngrediente(TipoIngrediente tipoIngrediente) {
		this.tipoIngrediente = tipoIngrediente;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Set<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(Set<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigoIngrediente;
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
		Ingrediente other = (Ingrediente) obj;
		if (codigoIngrediente != other.codigoIngrediente)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ingrediente [codigoIngrediente=" + codigoIngrediente + ", nombre=" + nombre + ", tipoIngrediente="
				+ tipoIngrediente.name() + ", precio=" + precio + "]";
	}
}
