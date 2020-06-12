package metodos;

import java.util.List;

import constantes.ConstantesIngrediente;
import constantes.ConstantesMenuPrincipal;
import constantes.ConstantesPizza;
import dao.IngredienteDAO;
import dao.PizzaDAO;
import main.GondolieriException;
import main.Utilidades;
import persistencia.Ingrediente;
import persistencia.Pizza;
import persistencia.TipoIngrediente;

/**
 * Clase que contiene todos los métodos relacionados con los Ingredientes para
 * liberar el principal
 * 
 * @author Pablo Herrera Mancera
 *
 */
public class MetodosIngrediente {

	/**
	 * Método que solicita y crea un Ingrediente.
	 * 
	 * @param herramienta
	 * @param daoIngrediente para comprobar si existe.
	 * @return Ingrediente creado.
	 * @throws GondolieriException
	 */
	public static Ingrediente crearIngrediente(Utilidades herramienta, IngredienteDAO daoIngrediente)
			throws GondolieriException {
		Ingrediente ingrediente;

		System.out.println("Añadiendo ingrediente...");
		ingrediente = pedirDatosIngrediente(herramienta, daoIngrediente);

		return ingrediente;
	}

	/**
	 * Método que solicita los datos de un Ingrediente.
	 * 
	 * @param herramienta
	 * @param daoIngrediente
	 * @return
	 * @throws GondolieriException
	 */
	private static Ingrediente pedirDatosIngrediente(Utilidades herramienta, IngredienteDAO daoIngrediente)
			throws GondolieriException {

		String nombre;
		boolean existe;
		TipoIngrediente tipoIngrediente;
		double precioIngrediente;
		Ingrediente ingredienteNuevo;

		nombre = herramienta.solicitarCadena("Introduce el nombre del ingrediente: ");
		existe = daoIngrediente.comprobarSiExisteIngrediente(nombre);
		if (existe) {
			throw new GondolieriException("El ingrediente " + nombre + " ya existe.");
		}
		// Si no existe, sigue
		tipoIngrediente = solicitarTipoIngrediente(herramienta);
		precioIngrediente = solicitarPrecioIngrediente(herramienta, "Introduzca el precio de " + nombre);

		ingredienteNuevo = new Ingrediente(nombre, tipoIngrediente, precioIngrediente);

		return ingredienteNuevo;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que solicita el precio de un Ingrediente.
	 * 
	 * @param herramienta
	 * @param msg
	 * @return
	 */
	private static double solicitarPrecioIngrediente(Utilidades herramienta, String msg) {
		return herramienta.solicitarDoubleEnRango(msg, 0, 2);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que solicita el TipoIngrediente de un Ingrediente.
	 * 
	 * @param herramienta
	 * @return
	 */
	public static TipoIngrediente solicitarTipoIngrediente(Utilidades herramienta) {

		// Variables locales al método
		TipoIngrediente tipoIngrediente = null;
		int opcion;

		System.out.println("¿A qué categoría pertenece su ingrediente?");
		System.out.println("\t1. " + TipoIngrediente.CARNE);
		System.out.println("\t2. " + TipoIngrediente.FRUTA);
		System.out.println("\t3. " + TipoIngrediente.SALSA);
		System.out.println("\t4. " + TipoIngrediente.VERDURA);

		opcion = herramienta.solicitarOpcion(ConstantesMenuPrincipal.OPC_INICIAL_MENUS,
				ConstantesIngrediente.ULTIMO_TIPO_INGREDIENTE);

		switch (opcion) {
		case ConstantesIngrediente.OPC_CARNE:
			tipoIngrediente = TipoIngrediente.CARNE;
			break;

		case ConstantesIngrediente.OPC_FRUTA:
			tipoIngrediente = TipoIngrediente.FRUTA;
			break;

		case ConstantesIngrediente.OPC_SALSA:
			tipoIngrediente = TipoIngrediente.SALSA;
			break;

		case ConstantesIngrediente.OPC_VERDURA:
			tipoIngrediente = TipoIngrediente.VERDURA;
			break;
		}

		return tipoIngrediente;
	}

//	/**
//	 * Método que borra un Ingrediente de todas las Pizzas.
//	 * 
//	 * @param ingrediente
//	 * @param daoPizza
//	 * @throws GondolieriException
//	 */
//	public static void borrarIngredienteDeTodasLasPizzas(Ingrediente ingrediente, PizzaDAO daoPizza)
//			throws GondolieriException {
//
//		// Variables locales al método
//		boolean borrado;
//		List<Pizza> pizzasQueContienenIngrediente = daoPizza.listarPizzasQueContienenUnIngrediente(ingrediente);
//
//		// No muestro mensaje de error en el else porque a la hora de borrar un
//		// ingrediente da igual si está en alguna pizza o no.
//		if (!pizzasQueContienenIngrediente.isEmpty()) { // Solo lo borra de las pizzas cuando está en alguna de ellas.
//			for (Pizza pizza : pizzasQueContienenIngrediente) {
//				System.out.println(
//						"Borrando ingrediente " + ingrediente.getNombre() + " de " + pizza.getNombre() + "...");
//
//				// Si lo contiene, lo borra
//				borrado = pizza.getIngredientes().remove(ingrediente);
//				if (borrado) {
//					// Si lo ha borrado, actualiza el precio de las pizzas que lo contuvieran
//					daoPizza.actualizarPrecioTotalPizza(pizza, ingrediente, ConstantesPizza.RESTAR);
//				}
//			}
//		}
//	}
}
