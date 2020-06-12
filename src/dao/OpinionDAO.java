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

	public void crearOpinion(Opinion opinion) throws GondolieriException {
		this.guardar(opinion, session);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<Opinion> listarOpinionesDeUnUsuario(Usuario usuario) {
		return session.createQuery("select o from Opinion o where nick = ? order by o.codigoOpinion")
				.setString(0, usuario.getNick())
				.list();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void modificarOpinion(Opinion opinion) throws GondolieriException {
		this.actualizar(opinion, session);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public double getMediaValoracionesDeUnaPizza(Pizza pizza) {

		// Variables locales al método
		double media;
		Query query = session
				.createQuery("select avg(o.valoracion)" + "from Opinion o " + "join o.pizza p " + "where p.nombre = ?");

		query.setString(0, pizza.getNombre());

		media = (Double) query.uniqueResult();

		return media;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Opinion getOpinionPorCodigo(int codigoOpinion) {
		return (Opinion) session.get(Opinion.class, codigoOpinion);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void borrarOpinion(Opinion opinion) throws GondolieriException {
		this.borrar(opinion, session);
	}

	public List<Opinion> getOpinionesDeUnUsuario(Usuario usuario) {

		return session.createQuery("select o from Opinion o join o.usuario u where u.nick = ?")
				.setString(0, usuario.getNick())
				.list();
	}

}
