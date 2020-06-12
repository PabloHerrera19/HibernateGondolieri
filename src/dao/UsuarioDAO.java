package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import constantes.ConstantesUtilidades;
import main.GondolieriException;
import main.Utilidades;
import persistencia.Usuario;

public class UsuarioDAO extends GenericDAO<Usuario> {

	// Atributos
	// Est�ticos
	private static Session session;

	// Constructor
	public UsuarioDAO(Session session) {
		this.session = session;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * M�todo que devuelve una lista con todos los usuarios de la base de datos
	 * ordenados alfab�ticamente por nick.
	 * 
	 * @return lista de Usuario con todos los usuarios de la base de datos.
	 */
	public List<Usuario> listarUsuarios() {
		return session.createQuery("select u from Usuario u order by u.nick asc").list();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * M�todo que devuelve un Usuario a trav�s del nick que recibe por par�metro.
	 * 
	 * @param nick para buscar el Usuario.
	 * @return Usuario buscado. Si existe devuelve el Usuario completo. Si no, lo
	 *         devuelve a null.
	 */
	public Usuario getUsuario(String nick) {
		return (Usuario) session.get(Usuario.class, nick);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * M�todo que trata de almacenar un Usuario en la base de datos. En caso de que
	 * se produjera alg�n error, no lo guarda.
	 * 
	 * @param usuarioNuevo a crear.
	 * @throws GondolieriException si no cumple las validaciones.
	 */
	public void crearUsuario(Usuario usuarioNuevo) throws GondolieriException {
		guardar(usuarioNuevo, session);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * M�todo que comprueba si existe un Usuario que recibe por par�metro.
	 * 
	 * @param usuario a comprobar.
	 * @return true si existe, false si no existe.
	 */
	public boolean comprobarSiExisteUsuario(Usuario usuario) {

		boolean existe;

		if (usuario == null) {
			existe = false;
		} else {
			existe = true;
		}

		return existe;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * M�todo que se encarga de borrar un Usuario de la base de datos.
	 * 
	 * @param usuarioBorrar
	 * @throws GondolieriException
	 */
	public void borrarUsuario(Usuario usuarioBorrar) throws GondolieriException {
		borrar(usuarioBorrar, session);
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
