package main;

import java.util.List;
import java.util.Scanner;

import constantes.ConstantesUtilidades;

public class Utilidades<T> {

	// OBJETOS ESTÁTICOS
	private static Scanner teclado = new Scanner(System.in);

	/**
	 * Método que solicita y valida la entrada de un número entero en un rango de
	 * valores.
	 * 
	 * @param msg
	 * @param inicio
	 * @param fin
	 * @return
	 */
	public int solicitarEnteroEnRango(String msg, int inicio, int fin) {

		int numero = 0;
		boolean esValido;
		do {
			try {
				System.out.println(msg);
				numero = Integer.parseInt(teclado.nextLine());
				if (numero < inicio || numero > fin) {
//					esValido = false;
					throw new NumberFormatException();
				}
				esValido = true;

			} catch (NumberFormatException e) {
				System.err.println("Debe introducir un número entre " + inicio + " y " + fin + ".");
				esValido = false;
			}
		} while (!esValido);

		return numero;
	}

	/**
	 * Método que solicita y valida la entrada de un número entero positivo (mayor
	 * que 0).
	 * 
	 * @param msg
	 * @return
	 */
	public int solicitarEnteroPositivo(String msg) {
		int numero = 0;
		boolean esValido;
		do {
			try {
				System.out.println(msg);
				numero = Integer.parseInt(teclado.nextLine());
				if (numero <= 0) {
					esValido = false;
				} else {
					esValido = true;
				}
			} catch (NumberFormatException e) {
				System.err.println("Debe introducir un número mayor que 0.");
				esValido = false;
			}
		} while (!esValido);

		return numero;
	}

	/**
	 * Método que solicita y valida la entrada de un decimal en un rango de valores.
	 * 
	 * @param msg
	 * @param inicio
	 * @param fin
	 * @return
	 */
	public double solicitarDoubleEnRango(String msg, int inicio, int fin) {

		// Variables locales al método
		double numero = 0;
		boolean esValido;

		do {
			try {
				System.out.println(msg);
				// Coger la cadena, con el split(".") coger la segunda parte
				// Pillar solo los dos primeros
				// luego convertirlo
				numero = Double.parseDouble(teclado.nextLine());
				if (numero < inicio || numero > fin) {
					throw new NumberFormatException();
				}
				esValido = true;
			} catch (NumberFormatException | NullPointerException e) {
				System.err.println("Debe introducir un número entre " + inicio + " y " + fin + ".");
				esValido = false;
			}
		} while (!esValido);
		return numero;
	}

	/**
	 * Método que solicita y valida la entrada de una cadena con contenido.
	 * 
	 * @param msg
	 * @return
	 */
	public String solicitarCadena(String msg) {
		String cadena;
		do {
			System.out.println(msg);
			cadena = teclado.nextLine();
		} while (cadena.length() == 0);
		return cadena;
	}

	/**
	 * Método que solicita y valida la entrada de una opción.
	 * 
	 * @param inicio
	 * @param fin
	 * @return
	 */
	public int solicitarOpcion(int inicio, int fin) {

		// Variables locales al método
		int opcion;
		String msg = "Elija una de las opciones de " + inicio + " a " + fin + ": ";

		opcion = solicitarEnteroEnRango(msg, inicio, fin);

		return opcion;
	}

	/**
	 * Método que convierte un entero introducido a true o false.
	 * 
	 * @param disponible el número entero que convertimos.
	 * @return
	 */
	public boolean solicitarBoolean(int disponible) {

		boolean estado;

		if (disponible == 1) {
			estado = true;
		} else {
			estado = false;
		}

		return estado;
	}

	/**
	 * Método que solicita y valida la entrada de un carácter para que sea S o N
	 * 
	 * @param msg
	 * @return
	 */
	public char solicitarRespuestaSiONo(String msg) {

		char respuesta = 0;
		boolean esValida = false;

		do {
			System.out.println(msg);
			try {
				// Cogemos la respuesta
				respuesta = teclado.nextLine().charAt(0);
				// La pasamos a mayúscula para que coincida
				respuesta = Character.toUpperCase(respuesta);
				// Comprobamos si es 'S' o 'N'
				if (respuesta == ConstantesUtilidades.RESPUESTA_SI || respuesta == ConstantesUtilidades.RESPUESTA_NO) {
					esValida = true;
				} else {
					esValida = false;
					System.err.println("Debe introducir S para Sí o N para No.");
				}
			} catch (StringIndexOutOfBoundsException e) {
				System.err.println("Debe introducir S para Sí o N para No.");
				esValida = false;
			}

		} while (!esValida);

		return respuesta;
	}

	/**
	 * Método que muestra una lista.
	 * 
	 * @param lista
	 * @param msg
	 */
	public void mostrarLista(List<T> lista, String msg) {

		int contadorElementos = 1;

		System.out.println(msg);
		for (T elemento : lista) {
			System.out.println("\t" + contadorElementos + ". " + elemento);
			contadorElementos++;
		}
	}

	/**
	 * Método que solicita y valida la entrada de un decimal positivo(mayor que 0).
	 * 
	 * @param msg
	 * @return
	 */
	public double solicitarDoublePositivo(String msg) {

		double numero = 0;
		boolean esValido;
		do {
			try {
				System.out.println(msg);
				numero = Double.parseDouble(teclado.nextLine());
				if (numero <= 0) {
					esValido = false;
				} else {
					esValido = true;
				}
			} catch (NumberFormatException e) {
				System.err.println("Debe introducir un número mayor que 0.");
				esValido = false;
			}
		} while (!esValido);

		return numero;
	}

}
