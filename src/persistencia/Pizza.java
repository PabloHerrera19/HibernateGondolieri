package persistencia;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "pizza")
public class Pizza implements Serializable {

	// CONSTANTES
	private static final double PRECIO_BASE_PIZZAS = 5.5;

	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int codigoPizza; // PK
	@Column
	@NotBlank
	@Size(max = 10)
	private String nombre;
	@Column
	@NotNull
	private boolean disponible;
	@Column
	@NotNull
	private double precioBase;
	@Column
	@NotNull
	private double precioTotal; // Campo calculado precio base + precio de los ingredientes

	@ManyToMany(fetch = FetchType.LAZY)
	@Cascade(CascadeType.SAVE_UPDATE) // El mismo cascade que en ingredientes
	@JoinTable(name = "pizzaIngrediente", joinColumns = { @JoinColumn(name = "codigoPizza") }, inverseJoinColumns = {
			@JoinColumn(name = "codigoIngrediente") })
	private Set<Ingrediente> ingredientes;

	@OneToMany(mappedBy = "pizza")
	@Cascade(CascadeType.ALL) // Si borro la pizza, se borran sus opiniones
	// @JoinColumn(name = "codigoOpinion") // Relacion con la tabla opinion
	private Set<Opinion> opinionesPizza;

	// Constructor
	public Pizza() {

	}

	public Pizza(String nombre, boolean disponible) {
		super();
		this.nombre = nombre;
		this.disponible = disponible;
		this.precioBase = PRECIO_BASE_PIZZAS;
		this.precioTotal = PRECIO_BASE_PIZZAS; // Cuando se crea, el precioTotal es el mismo que el base.
		// A medida que se añadan ingredientes, se actualiza
		this.ingredientes = new HashSet<Ingrediente>();
		this.opinionesPizza = new HashSet<Opinion>();
		// Calcular el precioTotal en un mÃ©todo aparte o aqui this.precioTotal =
		// calcularPrecioTotal
	}

	// get/set/equals/toString
	public int getCodigoPizza() {
		return codigoPizza;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isDisponible() {
		return disponible;
	}

	// Â¿MÃ©todo actualizarEstado llama aqui?
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public double getPrecioBase() {
		return precioBase;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double cantidad) {
		this.precioTotal = cantidad;
	}

	public Set<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public Set<Opinion> getOpinionesPizza() {
		return opinionesPizza;
	}

	public boolean addIngredienteAPizza(Ingrediente ingrediente) {

		// Variables locales al método
		boolean creado;
		
		// Como es un HashSet y no admite repetidos, devuelve false si ya existía.
		creado = ingredientes.add(ingrediente);

		return creado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigoPizza;
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
		Pizza other = (Pizza) obj;
		if (codigoPizza != other.codigoPizza)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pizza [codigoPizza=" + codigoPizza + ", nombre=" + nombre + ", disponible=" + disponible
				+ ", precioBase=" + precioBase + ", precioTotal=" + precioTotal + ", ingredientes=" + ingredientes
				+ ", opinionesPizza=" + opinionesPizza + "]";
	}
}
