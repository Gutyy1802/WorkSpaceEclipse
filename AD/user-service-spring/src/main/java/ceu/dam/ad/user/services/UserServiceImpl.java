package ceu.dam.ad.user.services;

import java.time.LocalDate;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ceu.dam.ad.user.model.User;
import ceu.dam.ad.user.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private final UserRepository repo;

    UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

	
	@Override
	public User createUser(User user) throws DuplicateUserException, UserException {
		try {
			logger.info("Creando usuario con estos datos: " + user);
			
			Optional<User> userEmailOpt = repo.findByEmail(user.getEmail());
			Optional<User>  userUsernameOpt = repo.findByUsername(user.getUsername());
			if(userEmailOpt.isPresent() || userUsernameOpt.isPresent()) {
				logger.debug("Se está intentando crear un usuario duplicado");
				throw new DuplicateUserException("Ese usuario ya existe");
			}
			user.setCreatedDate(LocalDate.now());
			user.setPassword(DigestUtils.sha512_256Hex(user.getPassword()));
			User created = repo.save(user);
			logger.info("Usuario creado con ID " + created.getId());
			return created;
		} 
		catch (DataAccessException e) {
			logger.error("Error creando usuario en BBDD", e);
			throw new UserException("Error creando al usuario", e);
		}
	}
	
	@Override
	public void changePassword(Long idUser, String oldPassword, String newPassword)
	        throws UserNotFoundException, UserUnauthorizedException, UserException {

	    try {

	        logger.info("Cambiando contraseña para el usuario con ID: " + idUser);

	        String oldPasswordCif = DigestUtils.sha512_256Hex(oldPassword);
	        String newPasswordCif = DigestUtils.sha512_256Hex(newPassword);

	        Optional<User> userOpt = repo.findById(idUser);

	        if (userOpt.isEmpty()) {
	            logger.debug("Usuario no encontrado con ID: " + idUser);
	            throw new UserNotFoundException("Ese usuario no existe");
	        }
	        User user = userOpt.get();
	        //User user = userOpt.orElseThrow(()-> new UserNotFoundException("Ese usuario no existe"));
	        
	        
	        if (!oldPasswordCif.equals(user.getPassword())) {
	            logger.debug("Contraseña antigua incorrecta para el usuario ID: " + idUser);
	            throw new UserUnauthorizedException("La contraseña antigua no es correcta");
	        }

	        if (oldPasswordCif.equals(newPasswordCif)) {
	            logger.debug("La nueva contraseña es igual a la antigua para el usuario ID: " + idUser);
	            throw new UserUnauthorizedException("Las contraseñas antigua y nueva no pueden ser iguales");
	        }

	        user.setPassword(newPasswordCif);
	        repo.save(user);

	        logger.info("Contraseña actualizada correctamente para el usuario ID: " + idUser);

	    } catch (DataAccessException e) {
	        logger.error("Error cambiando la contraseña en BBDD para el usuario ID: " + idUser, e);
	        throw new UserException("Error cambiando la contraseña", e);
	    }
	}

	@Override
	public User login(String login, String password)
	        throws UserNotFoundException, UserUnauthorizedException, UserException {

	    logger.info("Intentando login de usuario: " + login);

	    try {

	        Optional<User> userOpt = repo.findByEmail(login);

	        if (userOpt.isEmpty()) {
	        	userOpt = repo.findByUsername(login);
	        }

	        if (userOpt.isEmpty()) {
	            logger.debug("Usuario no encontrado con login: " + login);
	            throw new UserNotFoundException("Ese usuario no existe");
	        }

	        User user = userOpt.get();
	        String passwordCif = DigestUtils.sha512_256Hex(password);
	        
	        if (!passwordCif.equals(user.getPassword())) {
	            logger.debug("Contraseña incorrecta para el usuario: " + login);
	            throw new UserUnauthorizedException("La contraseña no es correcta");
	        }

	        logger.info("Login correcto para el usuario ID: " + user.getId());

	        try {
	            user.setLastLoginDate(LocalDate.now());
	            repo.save(user);
	        }
	        catch(Exception e) {
	            logger.warn("Error actualizando fecha de último login para el usuario ID: " + user.getId(), e);
	        }

	        return user;

	    } catch (DataAccessException e) {
	        logger.error("Error consultando usuario en login: " + login, e);
	        throw new UserException("Error consultando usuario", e);
	    }
	}

	@Override
	public User getUser(Long idUser) throws UserNotFoundException, UserException {

	    try{

	        logger.info("Consultando usuario con ID: " + idUser);

	        Optional<User> userOpt = repo.findById(idUser);

	        if (userOpt.isEmpty()) {
	            logger.debug("Usuario no encontrado con ID: " + idUser);
	            throw new UserNotFoundException("Ese usuario no existe");
	        }

	        logger.info("Usuario encontrado con ID: " + idUser);

	        return userOpt.get();

	    } catch (DataAccessException e) {
	        logger.error("Error consultando usuario con ID: " + idUser, e);
	        throw new UserException("Error consultando usuario", e);
	    }
	}

}
