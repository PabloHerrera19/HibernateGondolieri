package dao;

import java.util.HashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import constantes.ConstantesMenuPrincipal;
import constantes.ConstantesPizza;
import constantes.ConstantesUtilidades;
import main.GondolieriException;
import main.Utilidades;
import persistencia.Ingrediente;
import persistencia.Pizza;

public class PizzaDAO extends GenericDAO<Pizza> {

	// Atributos estáticos
	private static Session session;

	// Constructor
	public PizzaDAO(Session session) {
		this.session = session;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que almacena una Pizza en la base de datos.
	 * 
	 * @param pizza que hay que almacenar.
	 * @throws GondolieriException si no cumple las validaciones.
	 */
	public void crearPizza(Pizza pizza) throws GondolieriException {
		this.guardar(pizza, session);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que comprueba si existe una Pizza en la base de datos a través del
	 * nombre.
	 * 
	 * @param nombrePizza buscada.
	 * @return true si existe, false si no.
	 */
	public boolean comprobarSiExistePizza(String nombrePizza) {

		// Variables locales al método
		boolean existe;
		Query query = (Query) session.createQuery("select p from Pizza p where p.nombre = ?");

		query.setString(0, nombrePizza);

		if (query.list().isEmpty()) {
			existe = false;
		} else {
			existe = true;
		}
		return existe;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que actualiza el precioTotal de una Pizza.
	 * 
	 * @param pizza       a la cual le actualizamos el precioTotal.
	 * @param ingrediente para saber el precio que hay que modificar.
	 * @param operacion   (sumar o restar la cantidad)
	 * @throws GondolieriException si no cumple las validaciones.
	 */
	public void actualizarPrecioTotalPizza(Pizza pizza, Ingrediente ingrediente, int operacion)
			throws GondolieriException {

		switch (operacion) {
		case ConstantesPizza.SUMAR:
			pizza.setPrecioTotal(pizza.getPrecioTotal() + ingrediente.getPrecio());
			break;

		case ConstantesPizza.RESTAR:
			pizza.setPrecioTotal(pizza.getPrecioTotal() - ingrediente.getPrecio());
			break;
		}

		this.actualizar(pizza, session);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Método que devuelve una lista de Pizzas que contienen un Ingrediente.
	 * 
	 * @param ingrediente que buscamos en las Pizzas.
	 * @return lista de Pizzas con dicho Ingrediente.
	 */
	public List<Pizza> listarPizzasQueContienenUnIngrediente(Ingrediente ingrediente) {
		return session.createQuery("select p from Pizza p join p.ingredientes i where i.codigoIngrediente = ?")
				.setInteger(0, ingrediente.getCodigoIngrediente()).list();
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que obtiene una Pizza a través de su nombre.
	 * 
	 * @param nombrePizza buscada.
	 * @return
	 * @throws GondolieriException si no existe.
	 */
	public Pizza getPizza(String nombrePizza) throws GondolieriException {
		// Variables locales al método
		Pizza pizza;
		Query query = session.createQuery("select p from Pizza p where p.nombre = ?");
		boolean existe = comprobarSiExistePizza(nombrePizza);

		if (!existe) {
			throw new GondolieriException("La pizza " + nombrePizza + " no existe");
		}
		// Si existe, sigue

		query.setString(0, nombrePizza);

		pizza = (Pizza) query.uniqueResult();

		return pizza;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * Método que ejecuta una consulta a la base de datos y obtiene una lista de
	 * Pizzas disponibles.
	 * 
	 * @return
	 */
	public List<Pizza> listadoPizzasDisponibles() {
		return session.createQuery("select p from Pizza p where p.disponible = ?").setBoolean(0, true).list();
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que ejecuta una consulta a la base de datos y obtiene una lista con
	 * Pizzas cuyo precioTotal es inferior a un precio introducido por el Usuario.
	 * 
	 * @param precio límite.
	 * @return
	 */
	public List<Pizza> listarPizzasConPrecioMenorAIntroducido(double precio) {
		return session.createQuery("select p from Pizza p where p.precioTotal < ? order by p.codigoPizza")
				.setDouble(0, precio).list();
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que ejecuta una consulta a la base de datos y obtiene una lista de
	 * Ingrediente de una Pizza concreta.
	 * 
	 * @param pizza buscada.
	 * @return
	 */
	public List<Pizza> listadoPizzaEIngredientes(Pizza pizza) {
		return session.createQuery("select p.ingredientes from Pizza p where p.codigoPizza = ?")
				.setInteger(0, pizza.getCodigoPizza()).list();
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que actualiza una Pizza en la base de datos.
	 * 
	 * @param pizza a actualizar.
	 * @throws GondolieriException
	 */
	public void actualizarEstadoPizza(Pizza pizza) throws GondolieriException {
		this.actualizar(pizza, session);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que se encarga de borrar una Pizza de la base de datos.
	 * 
	 * @param pizza a borrar.
	 * @throws GondolieriException
	 */
	public void borrarPizza(Pizza pizza) throws GondolieriException {
		this.borrar(pizza, session);
	}

}
