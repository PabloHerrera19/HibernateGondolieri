package dao;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.Session;

import main.GondolieriException;

public class GenericDAO<T> {

	/**
	 * M�todo que guarda una entidad en la base de datos.
	 * 
	 * @param entidad a guardar.
	 * @param session la sesi�n en la que accede a la base de datos.
	 * @throws GondolieriException en caso de producirse alg�n fallo en las
	 *                             validaciones de Hibernate
	 */
	public void guardar(T entidad, Session session) throws GondolieriException {

		// Variables locales al m�todo
		StringBuilder msg = new StringBuilder();

		try {
			session.beginTransaction();
			session.save(entidad);

			session.getTransaction().commit(); // Si no hay error, lo guarda
//			session.clear();

		} catch (ConstraintViolationException cve) {

			for (ConstraintViolation cv : cve.getConstraintViolations()) {
				// Por cada fallo en las validaciones, muestra el campo y el mensaje de error
				msg.append("En el campo " + cv.getPropertyPath() + ", " + cv.getMessage() + "\n");
			}

			// Si se produce fallo al guardar, hace un rollback y no guarda el objeto
			session.getTransaction().rollback();

			// Salta la excepci�n.
			throw new GondolieriException("Error al guardar. " + msg.toString());
		} finally {
			session.clear();
		}
	}

	/**
	 * M�todo que borra una entidad de la base de datos.
	 * 
	 * @param entidad
	 * @param session
	 * @throws GondolieriException
	 */
	public void borrar(T entidad, Session session) throws GondolieriException {

		StringBuilder msg = new StringBuilder();

		try {
			session.beginTransaction();
			session.delete(entidad);
			session.getTransaction().commit();
		} catch (ConstraintViolationException cve) {

			for (ConstraintViolation cv : cve.getConstraintViolations()) {
				// Por cada fallo en las validaciones, muestra el campo y el mensaje de error
				msg.append("En el campo " + cv.getPropertyPath() + ", " + cv.getMessage() + "\n");
			}

			// Si se produce fallo al guardar, hace un rollback y no guarda el objeto
			session.getTransaction().rollback();
			// Salta la excepci�n.
			throw new GondolieriException("Error al borrar. " + msg.toString());
		} finally {
			session.clear();
		}
	}

	public void actualizar(T entidad, Session session) throws GondolieriException {

		StringBuilder msg = new StringBuilder();

		try {
			session.beginTransaction();
			session.update(entidad);
			session.getTransaction().commit();
		} catch (ConstraintViolationException cve) {

			for (ConstraintViolation cv : cve.getConstraintViolations()) {
				// Por cada fallo en las validaciones, muestra el campo y el mensaje de error
				msg.append("En el campo " + cv.getPropertyPath() + ", " + cv.getMessage() + "\n");
			}

			// Si se produce fallo al guardar, hace un rollback y no guarda el objeto
			session.getTransaction().rollback();
			// Salta la excepci�n.
			throw new GondolieriException("Error al actualizar. " + msg.toString());
		} finally {
			session.clear();
		}
	}
}
