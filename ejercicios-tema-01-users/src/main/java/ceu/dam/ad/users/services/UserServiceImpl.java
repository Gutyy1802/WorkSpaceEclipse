package ceu.dam.ad.users.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.LoggerFactory;

import ceu.dam.ad.users.dao.UserRepository;
import ceu.dam.ad.users.model.User;
import ch.qos.logback.classic.Logger;


public class UserServiceImpl extends Service implements UserService {
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository repo;

	public UserServiceImpl() {
		repo = new UserRepository();
	}

	@Override
	public User createUser(User user) throws DuplicateUserException, UserException {
		try (Connection conn = abrirConexion()) {
			logger.info("Creando usuario con estos datos: " + user);
			
			if (repo.getByEmail(conn, user.getEmail()) != null) {
				logger.debug("Se esta intentando crear un usuario duplicado");
				throw new DuplicateUserException("correo ya en uso");
			}
			if (repo.getByUserName(conn, user.getUsername()) != null) {
				logger.debug("Se esta intentando crear un usuario duplicado");
				throw new DuplicateUserException("usuario duplicado");
			}

			user.setCreatedDate(LocalDate.now());
			user.setPassword(DigestUtils.sha3_256Hex(user.getPassword()));

			Long id = repo.insert(conn, user);
			if (id == null) {
				logger.error("Error creando usuario. El ID devuelto por BBDD es NULL");
				throw new UserException("Error creando el usuario. Obtuvimos ID null");
			}
			logger.info("Usuario creado con ID " + id);
			return repo.getById(conn, id);

		} catch (SQLException e) {
			throw new UserException("Error conectando con la base de datos", e);
		}

	}

	@Override
	public void changePassword(Long idUser, String oldPassword, String newPassword)
	        throws UserNotFoundException, UserUnauthorizedException, UserException {

	    try (Connection conn = abrirConexion()) {

	        logger.info("Intentando cambiar la contraseña del usuario con ID " + idUser);

	        User user = repo.getById(conn, idUser);

	        if (user == null) {
	            logger.debug("No se ha encontrado el usuario con ID " + idUser);
	            throw new UserNotFoundException("Usuario no encontrado");
	        }

	        String oldPasswordHash = DigestUtils.sha3_256Hex(oldPassword);
	        String newPasswordHash = DigestUtils.sha3_256Hex(newPassword);

	        if (!user.getPassword().equals(oldPasswordHash)) {
	            logger.debug("La contraseña antigua introducida es incorrecta para el usuario con ID " + idUser);
	            throw new UserUnauthorizedException("Contraseña incorrecta");
	        }

	        if (oldPasswordHash.equals(newPasswordHash)) {
	            logger.debug("Se está intentando establecer la misma contraseña para el usuario con ID " + idUser);
	            throw new UserUnauthorizedException("La nueva contraseña es igual que la antigua");
	        }

	        user.setPassword(newPasswordHash);

	        repo.update(conn, user);

	        logger.info("Contraseña modificada correctamente para el usuario con ID " + idUser);

	    } catch (SQLException e) {
	        logger.error("Error cambiando la contraseña del usuario con ID " + idUser, e);
	        throw new UserException("Error conectando a la base de datos", e);
	    }
	}

	@Override
	public User login(String login, String password)
	        throws UserNotFoundException, UserUnauthorizedException, UserException {

	    try (Connection conn = abrirConexion()) {

	        logger.info("Intentando iniciar sesión con el usuario: " + login);

	        User user = repo.getByEmail(conn, login);

	        if (user == null) {
	            logger.debug("No se ha encontrado usuario por email. Buscando por nombre de usuario");
	            user = repo.getByUserName(conn, login);
	        }

	        if (user == null) {
	            logger.debug("No se ha encontrado ningún usuario con el login: " + login);
	            throw new UserNotFoundException("Usuario no encontrado");
	        }

	        logger.debug("Usuario encontrado con ID " + user.getId());

	        if (!user.getPassword().equals(DigestUtils.sha3_256Hex(password))) {
	            logger.debug("Contraseña incorrecta para el usuario con ID " + user.getId());
	            throw new UserUnauthorizedException("Contraseña incorrecta");
	        }

	        logger.debug("Contraseña correcta para el usuario con ID " + user.getId());

	        user.setLastLoginDate(LocalDate.now());
	        repo.update(conn, user);

	        logger.info("Inicio de sesión correcto para el usuario con ID " + user.getId());

	        return user;

	    } catch (SQLException e) {
	        logger.error("Error iniciando sesión con el usuario: " + login, e);
	        throw new UserException("Error conectando a la base de datos", e);
	    }
	}

	@Override
	public User getUser(Long idUser) throws UserNotFoundException, UserException {

	    try (Connection conn = abrirConexion()) {

	        logger.info("Buscando usuario con ID " + idUser);

	        User user = repo.getById(conn, idUser);

	        if (user == null) {
	            logger.debug("No se ha encontrado el usuario con ID " + idUser);
	            throw new UserNotFoundException("Usuario no encontrado");
	        }

	        logger.info("Usuario encontrado correctamente con ID " + idUser);

	        return user;

	    } catch (SQLException e) {
	        logger.error("Error buscando el usuario con ID " + idUser, e);
	        throw new UserException("Error conectando a la base de datos", e);
	    }
	}


}
