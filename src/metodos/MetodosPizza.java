package metodos;

import constantes.ConstantesPizza;
import constantes.ConstantesUtilidades;
import dao.IngredienteDAO;
import dao.PizzaDAO;
import main.GondolieriException;
import main.Utilidades;
import persistencia.Ingrediente;
import persistencia.Pizza;

public class MetodosPizza {

	public static Pizza crearPizza(Utilidades herramienta, PizzaDAO daoPizza) throws GondolieriException {
		Pizza pizza;
		System.out.println("Añadiendo pizza...");
		pizza = solicitarDatosPizza(herramienta, daoPizza);

		return pizza;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// AQUÍ COMIENZAN LOS MÉTODOS DE crearPizza()

	private static Pizza solicitarDatosPizza(Utilidades herramienta, PizzaDAO daoPizza) throws GondolieriException {

		String nombrePizza;
		boolean existe, disponible;

		nombrePizza = herramienta.solicitarCadena("Introduzca el nombre de la nueva pizza: ");
		existe = daoPizza.comprobarSiExistePizza(nombrePizza);

		if (existe) {
			throw new GondolieriException("La pizza " + nombrePizza + " ya existe");
		}
// Si no existe, sigue
		disponible = solicitarEstadoPizza(herramienta, "Introduzca el estado de su pizza: ");

		return new Pizza(nombrePizza, disponible);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static boolean solicitarEstadoPizza(Utilidades herramienta, String msg) {
		boolean estado;
		int disponible;
		String cadena = "1. Disponible\n2. No disponible";

		System.out.println(msg);
		disponible = herramienta.solicitarEnteroEnRango(cadena, ConstantesPizza.OPC_INICIAL_MENUS, 2);
		estado = herramienta.solicitarBoolean(disponible);

		return estado;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static Pizza addIngredienteAPizza(Pizza pizza, Ingrediente ingrediente) {

		// Variables locales al método
		boolean creado;

		System.out.println("Añadiendo ingrediente " + ingrediente.getNombre() + " a " + pizza.getNombre() + "...");

		// Como es un HashSet y no admite repetidos, devuelve false si ya existía.
		creado = pizza.getIngredientes().add(ingrediente);
		if (!creado) { // No se ha podido añadir
			System.err.println(
					"El ingrediente " + ingrediente.getNombre() + " ya existe en la pizza " + pizza.getNombre());
		}

		return pizza;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Pizza borrarIngredienteDeUnaPizza(Pizza pizza, Ingrediente ingredienteBorrar)
			throws GondolieriException {
		// Variables locales al método
		boolean borrado;

		System.out
				.println("Borrando ingrediente " + ingredienteBorrar.getNombre() + " de " + pizza.getNombre() + "...");

		// Si lo contiene, lo borra
		borrado = pizza.getIngredientes().remove(ingredienteBorrar);
		if (!borrado) {
			System.err.println(
					"La pizza " + pizza.getNombre() + " no contiene el ingrediente " + ingredienteBorrar.getNombre());
			// Actualizar aquí o en actualizarPrecioTotal
		}
		return pizza;
	}

}
