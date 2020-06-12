package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import main.GondolieriException;
import persistencia.Opinion;
import persistencia.Pizza;
import persistencia.Usuario;

public class OpinionDAO extends GenericDAO<Opinion> {

	// Atributos estáticos
	private static Session session;

	public OpinionDAO(Session session) {
		this.session = session;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que almacena una Opinion en la base de datos.
	 * 
	 * @param opinion a añadir
	 * @throws GondolieriException
	 */
	public void crearOpinion(Opinion opinion) throws GondolieriException {
		this.guardar(opinion, session);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Método que ejecuta una consulta a la base de datos y devuelve una lista con
	 * las Opiniones de un Usuario
	 * 
	 * @param usuario del que queremos obtener las Opiniones
	 * @return
	 */
	public List<Opinion> listarOpinionesDeUnUsuario(Usuario usuario) {
		return session.createQuery("select o from Opinion o where nick = ? order by o.codigoOpinion")
				.setString(0, usuario.getNick()).list();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Método que actualiza una Opinion en la base de datos.
	 * 
	 * @param opinion a actualizar.
	 * @throws GondolieriException
	 */
	public void modificarOpinion(Opinion opinion) throws GondolieriException {
		this.actualizar(opinion, session);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Método que obtiene la media de las valoraciones de una Pizza a través de una
	 * consulta a la base de datos.
	 * 
	 * @param pizza de la que queremos obtener la media de las valoraciones.
	 * @return media de valoraciones
	 * @throws GondolieriException si no tiene valoraciones.
	 */
	public double getMediaValoracionesDeUnaPizza(Pizza pizza) throws GondolieriException {

		// Variables locales al método
		double media;
		Query query = session
				.createQuery("select avg(o.valoracion)" + "from Opinion o " + "join o.pizza p " + "where p.nombre = ?");

		query.setString(0, pizza.getNombre());

		try {
			media = (Double) query.uniqueResult();
		} catch (NullPointerException e) {
			throw new GondolieriException("La pizza [" + pizza.getNombre() + "] no tiene valoraciones.");
		}

		return media;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Método que ejecuta una consulta a la base de datos y obtiene una Opinion a
	 * través de su codigoOpinion.
	 * 
	 * @param codigoOpinion buscado.
	 * @return Opinion buscada (null si no la encuentra).
	 */
	public Opinion getOpinionPorCodigo(int codigoOpinion) {
		return (Opinion) session.get(Opinion.class, codigoOpinion);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que ejecuta una consulta a la base de datos y obtiene una lista de las
	 * Opiniones de un Usuario
	 * 
	 * @param usuario para obtener Opiniones.
	 * @return
	 */
	public List<Opinion> getOpinionesDeUnUsuario(Usuario usuario) {

		return session.createQuery("select o from Opinion o join o.usuario u where u.nick = ?")
				.setString(0, usuario.getNick()).list();
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que se encarga de borrar una Opinion de la base de datos.
	 * 
	 * @param opinion a borrar.
	 * @throws GondolieriException
	 */
	public void borrarOpinion(Opinion opinion) throws GondolieriException {
		this.borrar(opinion, session);
	}

}
