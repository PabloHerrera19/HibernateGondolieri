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
	// Estáticos
	private static Session session;

	// Constructor
	public UsuarioDAO(Session session) {
		this.session = session;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que devuelve una lista con todos los usuarios de la base de datos
	 * ordenados alfabéticamente por nick.
	 * 
	 * @return lista de Usuario con todos los usuarios de la base de datos.
	 */
	public List<Usuario> listarUsuarios() {
		return session.createQuery("select u from Usuario u order by u.nick asc").list();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que devuelve un Usuario a través del nick que recibe por parámetro.
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
	 * Método que trata de almacenar un Usuario en la base de datos. En caso de que
	 * se produjera algún error, no lo guarda.
	 * 
	 * @param usuarioNuevo a crear.
	 * @throws GondolieriException si no cumple las validaciones.
	 */
	public void crearUsuario(Usuario usuarioNuevo) throws GondolieriException {
		guardar(usuarioNuevo, session);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que comprueba si existe un Usuario que recibe por parámetro.
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
	 * Método que se encarga de borrar un Usuario de la base de datos.
	 * 
	 * @param usuarioBorrar
	 * @throws GondolieriException
	 */
	public void borrarUsuario(Usuario usuarioBorrar) throws GondolieriException {
		borrar(usuarioBorrar, session);
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
