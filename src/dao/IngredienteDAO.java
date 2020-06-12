package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import main.GondolieriException;
import persistencia.Ingrediente;
import persistencia.TipoIngrediente;

public class IngredienteDAO extends GenericDAO<Ingrediente> {

	// Atributos est�ticos
	private static Session session;

	public IngredienteDAO(Session session) {
		this.session = session;
	}

	/**
	 * M�todo que almacena un Ingrediente en la base de datos.
	 * 
	 * @param ingrediente a a�adir.
	 * @throws GondolieriException
	 */
	public void crearIngrediente(Ingrediente ingrediente) throws GondolieriException {
		this.guardar(ingrediente, session);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * M�todo que a trav�s del nombre de un Ingrediente recibido, comprueba si
	 * existe en la base de datos.
	 * 
	 * @param nombreIngrediente buscado.
	 * @return true si existe, false si no
	 */
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

	// AQU� FINALIZAN LOS M�TODOS QUE NECESITA crearIngrediente
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * M�todo que ejecuta una consulta a la base de datos y devuelve una lista que
	 * contiene todos los ingredientes de la base de datos.
	 * 
	 * @return
	 */
	public List<Ingrediente> listarIngredientes() {
		return session.createQuery("select i from Ingrediente i order by i.codigoIngrediente")
				.list();
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * M�todo que hace una consulta a la base de datos y devuelve una lista de
	 * Ingredientes de un tipo determinado
	 * 
	 * @param tipoIngrediente buscado.
	 * @return
	 */
	public List<Ingrediente> listarIngredientesDeUnTipo(TipoIngrediente tipoIngrediente) {
		return session.createQuery("select i from Ingrediente i where i.tipoIngrediente=?")
				.setString(0, tipoIngrediente.name())
				.list();
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * M�todo que, a trav�s del nombre de ingrediente recibido, busca este
	 * ingrediente en la base de datos y lo devuelve.
	 * 
	 * @param nombreIngrediente buscado.
	 * @return Ingrediente buscado
	 * @throws GondolieriException si el Ingrediente no existe.
	 */
	public Ingrediente getIngrediente(String nombreIngrediente) throws GondolieriException {

		// Variables locales al m�todo
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
	/**
	 * M�todo que se encarga de borrar un Ingrediente de la base de datos.
	 * 
	 * @param ingredienteBorrar
	 * @throws GondolieriException
	 */
	public void borrarIngrediente(Ingrediente ingredienteBorrar) throws GondolieriException {
		this.borrar(ingredienteBorrar, session);
	}
}
