package metodos;

import java.time.LocalDateTime;

import constantes.ConstantesOpinion;
import constantes.ConstantesPizza;
import constantes.ConstantesUtilidades;
import dao.OpinionDAO;
import main.GondolieriException;
import main.Utilidades;
import persistencia.Opinion;
import persistencia.Pizza;
import persistencia.Usuario;

/**
 * Clase que contiene m�todos relacionados con las Opiniones para liberar el
 * principal
 * 
 * @author Pablo Herrera Mancera
 *
 */
public class MetodosOpinion {

	/**
	 * M�todo que se encarga de crear una Opini�n.
	 * 
	 * @param usuario     que la realiza.
	 * @param pizza       sobre la que se opina.
	 * @param herramienta para solicitar los datos necesarios.
	 * @return Opinion creada.
	 */
	public static Opinion crearOpinion(Usuario usuario, Pizza pizza, Utilidades herramienta) {
		Opinion opinion;
		String textoOpinion;
		int valoracion;

		textoOpinion = herramienta.solicitarCadena("Introduzca su opini�n sobre [" + pizza.getNombre() + "]:");
		valoracion = herramienta.solicitarEnteroEnRango("Introduzca la nota que le da a [" + pizza.getNombre() + "]:",
				ConstantesOpinion.VALORACION_MINIMA_PIZZA, ConstantesOpinion.VALORACION_MAXIMA_PIZZA);

		opinion = new Opinion(pizza, usuario, textoOpinion, valoracion);

		// DE MOMENTO NO ES NECESARIO COMPROBAR SI EXISTE LA OPINION PORQUE TODAS VAN A
		// SER DIFERENTES.
		// ADEM�S, EL USUARIO PUEDE TENER VARIAS OPINIONES SOBRE LA PIZZA.
		// SI NO, LA SOLUCI�N ES PREGUNTARLE AL USUARIO SI DESEA CREAR UNA NUEVA O SI
		// DESEA MODIFICAR LA QUE YA TIENE
		return opinion;
	}

	/**
	 * M�todo que se encarga de modificar una Opinion.
	 * 
	 * @param opinion
	 * @param operacion
	 * @param herramienta
	 * @return Opinion modificada.
	 */
	public static Opinion modificarOpinion(Opinion opinion, int operacion, Utilidades herramienta) {

		int valoracion;
		String texto;
		char respuesta;

		switch (operacion) {
		case ConstantesOpinion.MODIFICAR_VALORACION_OPINION:
			// Valoracion
			valoracion = herramienta.solicitarEnteroEnRango(
					"Introduzca la nota que le da a la pizza " + opinion.getPizza().getNombre() + "(0 - 10)",
					ConstantesOpinion.VALORACION_MINIMA_PIZZA, ConstantesOpinion.VALORACION_MAXIMA_PIZZA);
			opinion.setValoracion(valoracion);
			// Modificamos la fecha de la opinion
			opinion.setFecha(LocalDateTime.now());
			break;

		case ConstantesOpinion.MODIFICAR_TEXTO_OPINION:
			texto = herramienta
					.solicitarCadena("Introduzca su opini�n respecto a la pizza " + opinion.getPizza().getNombre());
			opinion.setTextoOpinion(texto);
			// Modificamos la fecha de la opinion
			opinion.setFecha(LocalDateTime.now());
			break;

		case ConstantesOpinion.BORRAR_USUARIO_OPINION:
			respuesta = herramienta.solicitarRespuestaSiONo("�Est� seguro que desea borrar el usuario ["
					+ opinion.getUsuario().getNick() + " de esta opini�n?");

			if (respuesta == ConstantesUtilidades.RESPUESTA_SI) {
				opinion.setUsuario(null);
			}
			break;
		}

		return opinion;
	}

//	/**
//	 * M�todo que se encarga de borrar una Opinion.
//	 * 
//	 * @param herramienta
//	 * @param daoOpinion
//	 * @throws GondolieriException
//	 */
//	public static void borrarOpinion(Utilidades herramienta, OpinionDAO daoOpinion) throws GondolieriException {
//
//		Opinion opinion;
//		int codigoOpinion;
//		char respuesta;
//
//		codigoOpinion = herramienta.solicitarEnteroPositivo("Introduzca el c�digo de la opini�n que desea borrar:");
//		opinion = daoOpinion.getOpinionPorCodigo(codigoOpinion);
//
//		respuesta = herramienta.solicitarRespuestaSiONo("�Est� seguro que desea la opini�n ? " + opinion);
//		if (respuesta == ConstantesUtilidades.RESPUESTA_SI) {
//			daoOpinion.borrarOpinion(opinion);
//		}
//	}

}
