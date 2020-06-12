package metodos;

import dao.UsuarioDAO;
import main.GondolieriException;
import main.Utilidades;
import persistencia.Usuario;

public class MetodosUsuario {

	public static Usuario crearUsuario(Utilidades herramienta, UsuarioDAO daoUsuario) throws GondolieriException {
		Usuario usuarioNuevo;

		usuarioNuevo = solicitarDatosUsuario(herramienta, daoUsuario);

		return usuarioNuevo;
	}

	private static Usuario solicitarDatosUsuario(Utilidades herramienta, UsuarioDAO daoUsuario)
			throws GondolieriException {

		Usuario usuario;
		String nick, nombre, apellidos;
		boolean existe;

		nick = herramienta.solicitarCadena("Introduzca el nick de su usuario: ");
		usuario = daoUsuario.getUsuario(nick);

		existe = daoUsuario.comprobarSiExisteUsuario(usuario);

		if (existe) {
			throw new GondolieriException("El usuario " + usuario.getNick() + " ya existe");
		}
		// Si no existe, sigue
		nombre = herramienta.solicitarCadena("Introduzca su nombre: ");
		apellidos = herramienta.solicitarCadena("Introduzca sus apellidos: ");

		usuario = new Usuario(nick, nombre, apellidos);

		return usuario;
	}
}
