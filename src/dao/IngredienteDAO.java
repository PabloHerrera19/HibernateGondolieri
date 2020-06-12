package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import main.GondolieriException;
import persistencia.Ingrediente;
import persistencia.TipoIngrediente;

public class IngredienteDAO extends GenericDAO<Ingrediente> {

	// Atributos estáticos
	private static Session session;

	public IngredienteDAO(Session session) {
		this.session = session;
	}

	public void crearIngrediente(Ingrediente ingrediente) throws GondolieriException {
		this.guardar(ingrediente, session);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean comprobarSiExisteIngrediente(String nombreIngrediente) {

		boolean existe;
		Query query = (Query) session.createQuery("select i from Ingrediente i where i.nombre = ?");
		query.setString(0, nombreIngrediente);

		if (query.list().isEmpty()) {
			existe = false;
		} else {
			existe = true;
		}

		return existe;
	}

	// AQUÍ FINALIZAN LOS MÉTODOS QUE NECESITA crearIngrediente
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List<Ingrediente> listarIngredientes() {
		return session.createQuery("select i from Ingrediente i").list();
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List<Ingrediente> listarIngredientesDeUnTipo(TipoIngrediente tipoIngrediente) {
		return session.createQuery("select i from Ingrediente i where i.tipoIngrediente=?")
				.setString(0, tipoIngrediente.name()).list();
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Ingrediente getIngrediente(String nombreIngrediente) throws GondolieriException {

		// Variables locales al método
		Query query = session.createQuery("select i from Ingrediente i where i.nombre = ?");
		Ingrediente ingrediente;
		boolean existe = comprobarSiExisteIngrediente(nombreIngrediente);

		if (!existe) {
			throw new GondolieriException("El ingrediente " + nombreIngrediente + " no existe");
		}
		query.setString(0, nombreIngrediente);

		ingrediente = (Ingrediente) query.uniqueResult();

		return ingrediente;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void borrarIngrediente(Ingrediente ingredienteBorrar) throws GondolieriException {
		this.borrar(ingredienteBorrar, session);
	}
}
