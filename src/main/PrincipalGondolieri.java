package main;

import java.util.List;

import org.hibernate.Session;

import constantes.ConstantesIngrediente;
import constantes.ConstantesMenuPrincipal;
import constantes.ConstantesOpinion;
import constantes.ConstantesPizza;
import constantes.ConstantesUsuario;
import constantes.ConstantesUtilidades;
import dao.IngredienteDAO;
import dao.OpinionDAO;
import dao.PizzaDAO;
import dao.UsuarioDAO;
import metodos.MetodosIngrediente;
import metodos.MetodosOpinion;
import metodos.MetodosPizza;
import metodos.MetodosUsuario;
import persistencia.Ingrediente;
import persistencia.Opinion;
import persistencia.Pizza;
import persistencia.TipoIngrediente;
import persistencia.Usuario;

public class PrincipalGondolieri {

	// Atributos est�ticos
	private static Session session;

	// Validador
	private static Utilidades herramienta = new Utilidades();
	// DAOS
	private static IngredienteDAO daoIngrediente;
	private static OpinionDAO daoOpinion;
	private static PizzaDAO daoPizza;
	private static UsuarioDAO daoUsuario;

	public static void main(String[] args) {

		configurarSesion(); // Abre la sesion est�tica
		inicializarDaos(); // Lo hago as� para pasarles la sesi�n actual una vez, en lugar de en cada
							// m�todo que la use
		inicioPrograma();
		cerrarSesion();
	}

//______________________________ COMIENZO SESSION ______________________________________

	private static void configurarSesion() {
		HibernateUtil.buildSessionFactory();
		HibernateUtil.openSessionAndBindToThread();
		session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

//___________________________________ SESSION ___________________________________________
	private static void cerrarSesion() {
		HibernateUtil.closeSessionFactory();
	}
//_________________________________ FIN SESSION _________________________________________

//________________________________ COMIENZO DAOS ________________________________________

	private static void inicializarDaos() {
		daoIngrediente = new IngredienteDAO(session);
		daoOpinion = new OpinionDAO(session);
		daoPizza = new PizzaDAO(session);
		daoUsuario = new UsuarioDAO(session);
	}

//___________________________________ FIN DAOS __________________________________________

//___________________________ COMIENZO INICIO PROGRAMA __________________________________
	/**
	 * M�todo que inicia el programa. Es llamado cada vez que tiene que mostrarse el
	 * men� principal.
	 */
	private static void inicioPrograma() {
		int opcion;

		do {
			opcion = mostrarMenuPrincipal();
			tratarMenuPrincipal(opcion);
		} while (opcion != ConstantesMenuPrincipal.OPC_SALIR);
		// Sale y el main cierra el programa
	}

//______________________________ FIN INICIO PROGRAMA ____________________________________

//____________________________ COMIENZO MEN� PRINCIPAL __________________________________
	/**
	 * M�todo que muestra el men� principal y solicita el men� en el cual queremos
	 * entrar.
	 * 
	 * @return men� elegido.
	 */
	private static int mostrarMenuPrincipal() {

		// Variables locales al m�todo
		int opcion;

		System.out.println("�Qu� men� desea visitar?");
		System.out.println("\t1. Ingredientes.");
		System.out.println("\t2. Pizzas.");
		System.out.println("\t3. Usuarios.");
		System.out.println("\t4. Opiniones.");
		System.out.println("\t5. Salir");

		opcion = herramienta.solicitarOpcion(ConstantesMenuPrincipal.OPC_INICIAL_MENUS,
				ConstantesMenuPrincipal.OPC_SALIR);

		return opcion;
	}

//_________________________________ MEN� PRINCIPAL ______________________________________
	/**
	 * M�todo que recoge el men� al que queremos acceder y nos redirige al mismo.
	 * 
	 * @param opcion es el men� al que queremos acceder.
	 */
	private static void tratarMenuPrincipal(int opcion) {

		try {
			switch (opcion) {
			case ConstantesMenuPrincipal.OPC_INGREDIENTE:
				do {
					opcion = mostrarMenuIngredientes();
					tratarMenuIngredientes(opcion);
				} while (opcion != ConstantesIngrediente.OPC_RETROCEDER_INGREDIENTE);
				// Sale y retrocede al men� principal
				break;

			case ConstantesMenuPrincipal.OPC_PIZZA:
				do {
					opcion = mostrarMenuPizzas();
					tratarMenuPizzas(opcion);
				} while (opcion != ConstantesPizza.OPC_RETROCEDER_PIZZA);
				// Sale y retrocede al men� principal
				break;

			case ConstantesMenuPrincipal.OPC_USUARIO:
				do {
					opcion = mostrarMenuUsuarios();
					tratarMenuUsuarios(opcion);
				} while (opcion != ConstantesUsuario.OPC_RETROCEDER_USUARIO);
				// Sale y retrocede al men� principal
				break;

			case ConstantesMenuPrincipal.OPC_OPINION:
				do {
					opcion = mostrarMenuOpiniones();
					tratarMenuOpiniones(opcion);
				} while (opcion != ConstantesOpinion.OPC_RETROCEDER_OPINION);
				// Sale y retrocede al men� principal
				break;

			} // cierre switch

		} catch (GondolieriException e) {
			System.err.println(e.getMessage());
		}
	}
//_______________________________ FIN MEN� PRINCIPAL ____________________________________

//__________________________ COMIENZO MEN� INGREDIENTES _________________________________
	/**
	 * Men� que muestra las opciones del men� de ingredientes y solicita la
	 * operaci�n que deseamos realizar.
	 * 
	 * @return operaci�n elegida.
	 */
	private static int mostrarMenuIngredientes() {

		// Variables locales al m�todo
		int opcion;

		System.out.println("Est� viendo el men� de ingredientes. �Qu� desea hacer?");
		System.out.println("\t1. A�adir nuevo ingrediente.");
		System.out.println("\t2. Listar todos los ingredientes.");
		System.out.println("\t3. Listado de ingredientes por tipo.");
		System.out.println("\t4. Borrar un ingrediente"); // por nombre
		System.out.println("\t5. Retroceder al men� principal");
		opcion = herramienta.solicitarOpcion(ConstantesMenuPrincipal.OPC_INICIAL_MENUS,
				ConstantesIngrediente.OPC_RETROCEDER_INGREDIENTE);

		return opcion;
	}

//________________________________ MEN� INGREDIENTES ____________________________________
	/**
	 * M�todo que, a trav�s de la opci�n elegida en el men� de ingredientes, realiza
	 * una operaci�n concreta.
	 * 
	 * @param opcion elegida por el usuario.
	 * @throws GondolieriException.
	 */
	private static void tratarMenuIngredientes(int opcion) throws GondolieriException {

		switch (opcion) {

		case ConstantesIngrediente.ADD_INGREDIENTE:
			addIngrediente();
			break;

		case ConstantesIngrediente.LISTAR_TODOS_INGREDIENTES:
			listarTodosLosIngredientes();
			break;

		case ConstantesIngrediente.LISTAR_INGREDIENTES_POR_TIPO:
			listarIngredientesPorTipo();
			break;

		case ConstantesIngrediente.BORRAR_INGREDIENTE:
			borrarIngrediente();
			break;
		}

	}
//______________________________ FIN MEN� INGREDIENTES __________________________________

//______________________________ COMIENZO INGREDIENTES __________________________________

	/**
	 * M�todo que crea un ingrediente pidiendo todos sus datos y lo almacena en la
	 * base de datos. Si existiera un error al almacenarlo, hace un rollback para
	 * deshacer los cambios.
	 * 
	 * @throws GondolieriException a la hora de pedir los datos o guardarlos.
	 */
	private static void addIngrediente() throws GondolieriException {

		// Variables locales al m�todo
		Ingrediente ingrediente;

		// Le paso el dao para el m�todo comprobarSiExisteIngrediente(nombre)
		ingrediente = MetodosIngrediente.crearIngrediente(herramienta, daoIngrediente);

		daoIngrediente.crearIngrediente(ingrediente);

		System.out.println("Ingrediente " + ingrediente.getNombre() + " creado correctamente");
	}

//__________________________________ INGREDIENTES _______________________________________
	/**
	 * M�todo que muestra una lista con todos los ingredientes de la base de datos.
	 * Adem�s, si �sta est� vac�a, le ofrece al usuario la posibilidad de crearla.
	 * 
	 * @throws GondolieriException a la hora de crearlo.
	 */
	private static void listarTodosLosIngredientes() throws GondolieriException {

		// Variables locales al m�todo
		Ingrediente ingrediente;
		List<Ingrediente> listaIngredientes;
		char respuesta;

		listaIngredientes = daoIngrediente.listarIngredientes();

		if (listaIngredientes.isEmpty()) { // Si est� vac�o informa de ello y pregunta si quiere crear uno nuevo
			System.err.println("No existe ning�n ingrediente.");
			respuesta = herramienta.solicitarRespuestaSiONo("�Desea crear uno ahora?");

			if (respuesta == ConstantesUtilidades.RESPUESTA_SI) { // Si desea crearlo, lo crea
				// Le paso el dao para el m�todo comprobarSiExisteIngrediente(nombre)
				ingrediente = MetodosIngrediente.crearIngrediente(herramienta, daoIngrediente);
				daoIngrediente.crearIngrediente(ingrediente);
			}
		} else { // Si hay ingredientes, los muestra
			herramienta.mostrarLista(listaIngredientes, "Lista de todos los ingredientes:");
		}
	}
//__________________________________ INGREDIENTES _______________________________________

	/**
	 * M�todo que muestra una lista de ingredientes de un tipo concreto.
	 */
	private static void listarIngredientesPorTipo() {

		// Variables locales al m�todo
		TipoIngrediente tipoIngrediente;
		List<Ingrediente> listaIngredientes;

		tipoIngrediente = MetodosIngrediente.solicitarTipoIngrediente(herramienta);
		listaIngredientes = daoIngrediente.listarIngredientesDeUnTipo(tipoIngrediente);

		if (listaIngredientes.isEmpty()) { // Si est� vac�o informa de ello
			System.err.println("No existe ning�n ingrediente para el tipo " + tipoIngrediente.name() + ".");
		} else {
			// Lista de ingredientes por tipo
			herramienta.mostrarLista(listaIngredientes, "Ingredientes del tipo " + tipoIngrediente.name() + ":");
		}
	}

//__________________________________ INGREDIENTES _______________________________________

	/**
	 * M�todo que borra un ingrediente de la base de datos, borr�ndolo previamente
	 * de todas sus pizzas y actualizando el precio de las mismas.
	 * 
	 * @throws GondolieriException si el ingrediente no existe.
	 */
	private static void borrarIngrediente() throws GondolieriException {

		// Variables locales al m�todo
		Ingrediente ingrediente = obtenerIngredientePorNombre();
		char respuesta;

		// Solicitamos la confirmaci�n al usuario.
		respuesta = herramienta.solicitarRespuestaSiONo("�Est� seguro de que desea borrar el ingrediente "
				+ ingrediente.getNombre() + "? (Introduzca S para S�, N para No)");

		if (respuesta == ConstantesUtilidades.RESPUESTA_SI) { // Si desea borrarlo, lo borra.
			System.out.println("Borrando ingrediente...");

			List<Pizza> pizzasQueContienenIngrediente = daoPizza.listarPizzasQueContienenUnIngrediente(ingrediente);

			if (!pizzasQueContienenIngrediente.isEmpty()) {

				// Si el ingrediente est� en alguna pizza, lo borramos de �sta antes
				for (Pizza pizza : pizzasQueContienenIngrediente) {
					if (pizza.getIngredientes().contains(ingrediente)) {
						pizza.getIngredientes().remove(ingrediente);
						daoPizza.actualizarPrecioTotalPizza(pizza, ingrediente, ConstantesPizza.RESTAR);
					}
				}
			}

			daoIngrediente.borrarIngrediente(ingrediente);
			System.out.println("Ingrediente " + ingrediente.getNombre() + " borrado correctamente");
		}
	}

//__________________________________ INGREDIENTES _______________________________________
	/**
	 * M�todo que obtiene un Ingrediente a trav�s del nombre que le solicita al
	 * usuario.
	 * 
	 * @return Ingrediente creado
	 * @throws GondolieriException si el Ingrediente ya existe.
	 */
	private static Ingrediente obtenerIngredientePorNombre() throws GondolieriException {

		String nombreIngrediente;
		Ingrediente ingrediente;
		// Pedimos el nombre del ingredientes.
		nombreIngrediente = herramienta.solicitarCadena("Introduzca el nombre del ingrediente:");
		// Lo obtenemos.
		ingrediente = daoIngrediente.getIngrediente(nombreIngrediente);
		return ingrediente;
	}

//________________________________ FIN INGREDIENTES _____________________________________

//______________________________ COMIENZO MEN� PIZZAS ___________________________________
	/**
	 * Men� que muestra las opciones del men� de pizzas y solicita la operaci�n que
	 * deseamos realizar.
	 * 
	 * @return operaci�n elegida.
	 */
	private static int mostrarMenuPizzas() {

		// Variables locales al m�todo
		int opcion;
		System.out.println("Est� viendo el men� de pizzas. �Qu� desea hacer?");
		System.out.println("\t1. A�adir nueva pizza.");
		System.out.println("\t2. A�adir ingrediente a una pizza.");
		System.out.println("\t3. Borrar ingrediente de una pizza.");
		System.out.println("\t4. Listado de pizzas con precio menor a un precio introducido.");
		System.out.println("\t5. Listado de una pizza concreta y sus ingredientes.");
		System.out.println("\t6. Listado de pizzas con un ingrediente concreto.");
		System.out.println("\t7. Listado pizzas disponibles.");
		System.out.println("\t8. Actualizar estado de una pizza.");
		System.out.println("\t9. Borrar una pizza.");
		System.out.println("\t10. Retroceder al men� principal");

		opcion = herramienta.solicitarOpcion(ConstantesMenuPrincipal.OPC_INICIAL_MENUS,
				ConstantesPizza.OPC_RETROCEDER_PIZZA);

		return opcion;
	}

//__________________________________ MEN� PIZZAS ________________________________________

	/**
	 * M�todo que, a trav�s de la opci�n elegida en el men� de pizzas, realiza una
	 * operaci�n concreta.
	 * 
	 * @param opcion elegida por el usuario.
	 * @throws GondolieriException.
	 */
	private static void tratarMenuPizzas(int opcion) throws GondolieriException {

		switch (opcion) {
		case ConstantesPizza.ADD_PIZZA:
			addPizza();
			break;

		case ConstantesPizza.ADD_INGREDIENTE_A_PIZZA:
			addIngredienteAPizza();
			break;

		case ConstantesPizza.BORRAR_INGREDIENTE_DE_PIZZA:
			borrarIngredienteDeUnaPizza();
			break;
		case ConstantesPizza.LISTAR_PIZZAS_CON_PRECIO_MENOR_A_INDICADO:
			listarPizzasConPrecioMenorAIntroducido();
			break;
		case ConstantesPizza.LISTAR_PIZZA_E_INGREDIENTES:
			listarIngredientesDeUnaPizza();
			break;

		case ConstantesPizza.LISTAR_PIZZAS_QUE_CONTIENEN_INGREDIENTE:
			listarPizzasQueContienenUnIngrediente();
			break;

		case ConstantesPizza.LISTAR_PIZZAS_DISPONIBLES:
			listarPizzasDisponibles();
			break;

		case ConstantesPizza.ACTUALIZAR_ESTADO_PIZZA:
			actualizarEstadoPizza();
			break;

		case ConstantesPizza.BORRAR_PIZZA:
			borrarPizza();
			break;
		}
	}
//_________________________________ FIN MEN� PIZZAS _____________________________________

//_____________________________________ PIZZAS __________________________________________
	/**
	 * M�todo que crea una Pizza y la almacena en la base de datos.
	 * 
	 * @throws GondolieriException si ya existe la Pizza.
	 */
	private static void addPizza() throws GondolieriException {

		// Variables locales al m�todo
		// Le pasamos daoPizza para el m�todo comprobarSiExistePizza, el cual lanza la
		// excepci�n
		Pizza pizza = MetodosPizza.crearPizza(herramienta, daoPizza);

		daoPizza.crearPizza(pizza);

		System.out.println("Pizza  " + pizza.getNombre() + " creado correctamente");
	}

//_____________________________________ PIZZAS __________________________________________
	/**
	 * M�todo que a�ade un Ingrediente a una Pizza, actualizando el precioTotal de
	 * �sta y actualiz�ndola posteriormente en la base de datos.
	 * 
	 * @throws GondolieriException si el Ingrediente o la Pizza no existen o no ha
	 *                             podido a�adirse.
	 */
	private static void addIngredienteAPizza() throws GondolieriException {

		Ingrediente ingrediente = obtenerIngredientePorNombre();
		Pizza pizza = obtenerPizzaPorNombre();
		boolean modificada;

		System.out.println("A�adiendo ingrediente [" + ingrediente.getNombre() + "] a [" + pizza.getNombre() + "]...");

		modificada = pizza.getIngredientes().add(ingrediente);
		if (!modificada) {
			System.err.println(
					"El ingrediente " + ingrediente.getNombre() + " ya existe en la pizza" + pizza.getNombre());
		} else {
			// Como aqu� llamamos a daoPizza.actualizar no es necesario hacerlo abajo.
			daoPizza.actualizarPrecioTotalPizza(pizza, ingrediente, ConstantesPizza.SUMAR);
			System.out.println("Ingrediente [" + ingrediente.getNombre() + "] a�adido correctamente a ["
					+ pizza.getNombre() + "].");
		}
	}

//_____________________________________ PIZZAS __________________________________________
	/**
	 * M�todo que borra un Ingrediente de una Pizza, actualizando su precioTotal y
	 * actualiz�ndola posteriormente en la base de datos.
	 * 
	 * @throws GondolieriException si el Ingrediente o la Pizza no existen o no ha
	 *                             podido a�adirse.
	 */
	private static void borrarIngredienteDeUnaPizza() throws GondolieriException {
		//
		Ingrediente ingredienteBorrar = obtenerIngredientePorNombre();
		Pizza pizza = obtenerPizzaPorNombre();
		boolean borrado;
		char respuesta;

		if (!pizza.getIngredientes().contains(ingredienteBorrar)) {
			System.err.println(
					"La pizza " + pizza.getNombre() + " no contiene el ingrediente " + ingredienteBorrar.getNombre());
		} else {

			// Pedimos la confirmaci�n al usuario
			respuesta = herramienta.solicitarRespuestaSiONo("�Est� seguro que desea borrar el ingrediente ["
					+ ingredienteBorrar.getNombre() + "] de la pizza " + pizza.getNombre());

			if (respuesta == ConstantesUtilidades.RESPUESTA_SI) {
				// Si est� seguro, lo borra.

				System.out.println("Borrando ingrediente [" + ingredienteBorrar.getNombre() + "] de ["
						+ pizza.getNombre() + "]...");

				borrado = pizza.getIngredientes().remove(ingredienteBorrar);
				if (!borrado) {
					System.err.println("No se ha podido borrar el ingrediente [" + ingredienteBorrar.getNombre()
							+ "] de [" + pizza.getNombre() + "]...");
				} else {
					// Actualiza el precioTotal de la pizza y actualiza la pizza
					daoPizza.actualizarPrecioTotalPizza(pizza, ingredienteBorrar, ConstantesPizza.RESTAR);
					System.out.println("Ingrediente [" + ingredienteBorrar.getNombre() + "] borrado correctamente de ["
							+ pizza.getNombre() + "].");
				}
			}
		}
	}

//_____________________________________ PIZZAS __________________________________________
	/**
	 * M�todo que muestra una lista de Pizzas con un precioTotal menor a uno
	 * solicitado al usuario.
	 */
	private static void listarPizzasConPrecioMenorAIntroducido() {
		double precio = herramienta.solicitarDoublePositivo("Introduzca el precio l�mite para su consulta: ");
		List<Pizza> listadoDePizzasConPrecioMenorAIntroducido = daoPizza.listarPizzasConPrecioMenorAIntroducido(precio);

		if (listadoDePizzasConPrecioMenorAIntroducido.isEmpty()) {
			System.err.println("No existen pizzas con precio inferior a " + precio + ".");
		} else {
			herramienta.mostrarLista(listadoDePizzasConPrecioMenorAIntroducido,
					"Lista de pizzas con precio inferior a " + precio);
		}
	}

//_____________________________________ PIZZAS __________________________________________
	/**
	 * M�todo que muestra la lista de Ingredientes que tiene una Pizza concreta.
	 * 
	 * @throws GondolieriException si la Pizza no existe.
	 */
	private static void listarIngredientesDeUnaPizza() throws GondolieriException {
		Pizza pizza = obtenerPizzaPorNombre();

		List<Pizza> listadoPizzasEIngredientes = daoPizza.listadoPizzaEIngredientes(pizza);

		if (listadoPizzasEIngredientes.isEmpty()) {
			System.err.println("La pizza " + pizza.getNombre() + " no tiene ingredientes.");
		} else {
			herramienta.mostrarLista(listadoPizzasEIngredientes, "Lista de ingredientes de " + pizza.getNombre() + ":");
		}
	}

//_____________________________________ PIZZAS __________________________________________
	/**
	 * M�todo que muestra una lista de Pizzas que contienen un Ingrediente concreto.
	 * 
	 * @throws GondolieriException si el Ingrediente o la Pizza no existen.
	 */
	private static void listarPizzasQueContienenUnIngrediente() throws GondolieriException {

		Ingrediente ingrediente = obtenerIngredientePorNombre();

		// Si existe (no salta GondolieriException en getIngrediente), hace la lista.
		List<Pizza> listaPizzasQueContienenIngrediente = daoPizza.listarPizzasQueContienenUnIngrediente(ingrediente);

		if (listaPizzasQueContienenIngrediente.isEmpty()) {
			System.err.println("No existen pizzas con el ingrediente " + ingrediente.getNombre());
		} else {
			herramienta.mostrarLista(listaPizzasQueContienenIngrediente,
					"Lista de pizzas que contienen el ingrediente " + ingrediente.getNombre());
		}
	}

//_____________________________________ PIZZAS __________________________________________
	/**
	 * M�todo que muestra una lista de Pizzas disponibles.
	 */
	private static void listarPizzasDisponibles() {

		List<Pizza> listaPizzasDisponibles = daoPizza.listadoPizzasDisponibles();

		if (listaPizzasDisponibles.isEmpty()) {
			System.err.println("No existen pizzas disponibles en este momento.");
		} else {
			herramienta.mostrarLista(listaPizzasDisponibles, "Lista de pizzas disponibles:");
		}
	}

//_____________________________________ PIZZAS __________________________________________
	/**
	 * M�todo que se encarga de actualizar el estado de una Pizza(disponible/no
	 * disponible) y la actualiza posteriormente en la base de datos.
	 * 
	 * @throws GondolieriException si no existe la Pizza o ha habido alg�n error al
	 *                             actualizarla.
	 */
	private static void actualizarEstadoPizza() throws GondolieriException {

		Pizza pizza = obtenerPizzaPorNombre();
		pizza.setDisponible(!pizza.isDisponible());

		daoPizza.actualizarEstadoPizza(pizza);

		System.out.println("El estado de [" + pizza.getNombre() + "] ha sido actualizado correctamente.");
	}

//_____________________________________ PIZZAS __________________________________________
	/**
	 * M�todo que se encarga de borrar una Pizza.
	 * 
	 * @throws GondolieriException si la Pizza no existe o si hay alg�n problema al
	 *                             almacenarla en la base de datos.
	 */
	private static void borrarPizza() throws GondolieriException {
		Pizza pizza = obtenerPizzaPorNombre();
		daoPizza.borrarPizza(pizza);
		System.out.println("Pizza [" + pizza.getNombre() + "] borrada correctamente.");
	}

//_____________________________________ PIZZAS __________________________________________
	/**
	 * M�todo que solicita el nombre de una Pizza y, si existe, la devuelve.
	 * 
	 * @return Pizza solicitada.
	 * @throws GondolieriException si la Pizza no existe.
	 */
	private static Pizza obtenerPizzaPorNombre() throws GondolieriException {

		Pizza pizza;
		String nombrePizza;

		nombrePizza = herramienta.solicitarCadena("Introduzca el nombre de la pizza");
		pizza = daoPizza.getPizza(nombrePizza);

		return pizza;
	}

//___________________________________ FIN PIZZAS ________________________________________

//______________________________ COMIENZO MEN� USUARIOS _________________________________
	/**
	 * Men� que muestra las opciones del men� de usuarios y solicita la operaci�n
	 * que deseamos realizar.
	 * 
	 * @return operaci�n elegida.
	 */
	private static int mostrarMenuUsuarios() {
		int opcion;

		System.out.println("Est� viendo el men� de usuarios. �Qu� desea hacer?");
		System.out.println("\t1. A�adir nuevo usuario.");
		System.out.println("\t2. Listado de usuarios.");
		System.out.println("\t3. Borrar usuario.");
		System.out.println("\t4. Retroceder al men� principal.");

		opcion = herramienta.solicitarOpcion(ConstantesMenuPrincipal.OPC_INICIAL_MENUS,
				ConstantesUsuario.OPC_RETROCEDER_USUARIO);

		return opcion;

	}

//_________________________________ MEN� USUARIOS _______________________________________
	/**
	 * M�todo que, a trav�s de la opci�n elegida en el men� de usuarios, realiza una
	 * operaci�n concreta.
	 * 
	 * @param opcion elegida por el usuario.
	 * @throws GondolieriException.
	 */
	private static void tratarMenuUsuarios(int opcion) throws GondolieriException {

		switch (opcion) {
		case ConstantesUsuario.ADD_USUARIO:
			addUsuario();
			break;

		case ConstantesUsuario.LISTAR_USUARIOS:
			listarTodosLosUsuarios();
			break;
		case ConstantesUsuario.BORRAR_USUARIO:
			borrarUsuario();
			break;
		}
	}

//________________________________ FIN MEN� USUARIOS ____________________________________

//_____________________________________ USUARIOS ________________________________________

	/**
	 * M�todo que se encarga de a�adir un usuario a la base de datos.
	 * 
	 * @throws GondolieriException si el Usuario ya existe.
	 */
	private static void addUsuario() throws GondolieriException {
		Usuario usuarioNuevo;

		System.out.println("A�adiendo usuario...");
		// Le pasamos el dao para que compruebe si existe el Usuario
		usuarioNuevo = MetodosUsuario.crearUsuario(herramienta, daoUsuario);

		daoUsuario.crearUsuario(usuarioNuevo);

		System.out.println("Usuario  " + usuarioNuevo.getNick() + " creado correctamente");
	}
//_____________________________________ USUARIOS ________________________________________

	private static void listarTodosLosUsuarios() throws GondolieriException {

		// Variables locales al m�todo
		List<Usuario> listaUsuarios = daoUsuario.listarUsuarios();
		char respuesta;
		Usuario usuario;

		if (listaUsuarios.isEmpty()) {
			System.err.println("No existe ning�n usuario.");

			respuesta = herramienta.solicitarRespuestaSiONo("�Desea crear uno ahora?");

			if (respuesta == ConstantesUtilidades.RESPUESTA_SI) { // Si desea crearlo, lo crea
				// Le paso el dao para el m�todo comprobarSiExisteUsuario(usuario)
				usuario = MetodosUsuario.crearUsuario(herramienta, daoUsuario);
				daoUsuario.crearUsuario(usuario);
			}
		} else {
			herramienta.mostrarLista(listaUsuarios, "Lista de usuarios:");
		}

	}

//_____________________________________ USUARIOS ________________________________________

	private static void borrarUsuario() throws GondolieriException {
		String nick = herramienta.solicitarCadena("Introduzca el nick del usuario que desea borrar: ");
		Usuario usuario = daoUsuario.getUsuario(nick);

		if (!daoUsuario.comprobarSiExisteUsuario(usuario)) {
			System.err.println("El usuario " + nick + " no existe.");
		} else {
			System.out.println("Borrando usuario " + usuario.getNick() + "...");
			// FALTA PONER A NULL LOS USUARIOS DE LAS PIZZAS QUE TENGAN X OPINION
			daoUsuario.borrarUsuario(usuario);
			System.out.println("Usuario " + usuario.getNick() + " borrado correctamente");
		}
	}

//___________________________________ FIN USUARIOS ______________________________________

//______________________________ COMIENZO MEN� OPINIONES ________________________________

	/**
	 * Men� que muestra las opciones del men� de opiniones y solicita la operaci�n
	 * que deseamos realizar.
	 * 
	 * @return operaci�n elegida.
	 */
	private static int mostrarMenuOpiniones() {

		// Variables locales al m�todo
		int opcion;

		System.out.println("Est� viendo el men� de opiniones. �Qu� desea hacer?");
		System.out.println("\t1. A�adir nueva opini�n.");
		System.out.println("\t2. Listado de opiniones de un usuario.");
		System.out.println("\t3. Modificar opini�n de un usuario.");
		System.out.println("\t4. Media de valoraciones de una pizza.");
		System.out.println("\t5. Borrar una opini�n");
		System.out.println("\t6. Retroceder al men� principal");

		opcion = herramienta.solicitarOpcion(ConstantesMenuPrincipal.OPC_INICIAL_MENUS,
				ConstantesOpinion.OPC_RETROCEDER_OPINION);

		return opcion;
	}

//___________________________________ MEN� OPINIONES ____________________________________

	/**
	 * M�todo que, a trav�s de la opci�n elegida en el men� de opiniones, realiza
	 * una operaci�n concreta.
	 * 
	 * @param opcion elegida por el usuario.
	 * @throws GondolieriException.
	 */
	private static void tratarMenuOpiniones(int opcion) throws GondolieriException {

		switch (opcion) {
		case ConstantesOpinion.ADD_OPINION:
			crearOpinion();
			break;

		case ConstantesOpinion.LISTAR_OPINIONES_DE_USUARIO:
			listarOpinionesDeUnUsuario();
			break;

		case ConstantesOpinion.MODIFICAR_OPINION_USUARIO:
			modificarOpinionDeUnUsuario();
			break;

		case ConstantesOpinion.MEDIA_VALORACIONES_PIZZA:
			mostrarMediaValoracionesDeUnaPizza();
			break;

		case ConstantesOpinion.BORRAR_OPINION:
			borrarOpinion();
			break;
		}
	}

//_________________________________ FIN MEN� OPINIONES __________________________________

//_____________________________________ OPINIONES _______________________________________

	private static Usuario obtenerUsuarioPorNick() {
		Usuario usuario;
		String nick = herramienta.solicitarCadena("Introduzca el nick del usuario:");
		usuario = daoUsuario.getUsuario(nick);
		return usuario;
	}

//_____________________________________ OPINIONES _______________________________________

	private static void crearOpinion() throws GondolieriException {
		Usuario usuario;
		Pizza pizza;
		Opinion opinion;

		System.out.println("A�adiendo opini�n...");

		usuario = obtenerUsuarioPorNick();
		pizza = obtenerPizzaPorNombre();
		opinion = MetodosOpinion.crearOpinion(usuario, pizza, herramienta);

		daoOpinion.crearOpinion(opinion);

		System.out.println("Opini�n [" + opinion + "] creada correctamente.");
	}

//_____________________________________ OPINIONES _______________________________________

	private static void listarOpinionesDeUnUsuario() {
		Usuario usuario = obtenerUsuarioPorNick();
		List<Opinion> opiniones = daoOpinion.listarOpinionesDeUnUsuario(usuario);
		herramienta.mostrarLista(opiniones, "Lista de opiniones del usuario " + usuario.getNick() + ":");
	}

//_____________________________________ OPINIONES _______________________________________

	private static void modificarOpinionDeUnUsuario() throws GondolieriException {
		Usuario usuario;
		List<Opinion> opiniones;
		int codigoOpinion, operacion;
		usuario = obtenerUsuarioPorNick();
		opiniones = daoOpinion.getOpinionesDeUnUsuario(usuario);

		if (opiniones.isEmpty()) {
			System.err.println("El usuario " + usuario.getNick() + " no tiene opiniones.");
		} else {
			herramienta.mostrarLista(opiniones, "Lista de opiniones del usuario " + usuario.getNick() + ":");
			codigoOpinion = herramienta.solicitarEnteroEnRango("Escoja la opini�n que desee modificar:", 1,
					opiniones.size());

			Opinion opinion = opiniones.get(codigoOpinion - 1);
			// No se si es codigoOpinion o codigoOpinion-1. Depende de como empiece la
			// lista.
			// Creo que en 0
			System.out.println("�Qu� valor desea modificar?");
			operacion = herramienta.solicitarEnteroEnRango("1. Valoraci�n.\n2. Texto.\n3. Quitar usuario.",
					ConstantesOpinion.MIN_OPCIONES_MODIFICAR, ConstantesOpinion.MAX_OPCIONES_MODIFICAR);

			opinion = MetodosOpinion.modificarOpinion(opinion, operacion, herramienta);

			daoOpinion.modificarOpinion(opinion);
		}
	}

//_____________________________________ OPINIONES _______________________________________

	private static void mostrarMediaValoracionesDeUnaPizza() throws GondolieriException {
		Pizza pizza;
		double mediaValoraciones;
		pizza = daoPizza.getPizza("Introduzca el nombre de la pizza que desea consultar:");
		mediaValoraciones = daoOpinion.getMediaValoracionesDeUnaPizza(pizza);
		// COMPROBAR EL FORMATO CON DOS DECIMALES
		System.out.printf("La media de las valoraciones de " + pizza.getNombre() + " es %.2f", mediaValoraciones);
	}

//_____________________________________ OPINIONES _______________________________________

	private static void borrarOpinion() throws GondolieriException {

		Usuario usuario = obtenerUsuarioPorNick();
		List<Opinion> opiniones = daoOpinion.getOpinionesDeUnUsuario(usuario);
		int codigoOpinion;
		char respuesta;

		if (opiniones.isEmpty()) {
			System.err.println("El usuario " + usuario.getNick() + " no tiene opiniones.");
		} else {
			herramienta.mostrarLista(opiniones, "Lista de opiniones del usuario " + usuario.getNick());
			codigoOpinion = herramienta.solicitarEnteroEnRango("Escoja la opini�n que desee borrar:", 1,
					opiniones.size());

			Opinion opinion = opiniones.get(codigoOpinion);
			respuesta = herramienta.solicitarRespuestaSiONo("�Est� seguro que desea borrar la opini�n ? " + opinion);
			if (respuesta == ConstantesUtilidades.RESPUESTA_SI) {
				daoOpinion.borrarOpinion(opinion);
			}
		}
	}
}
