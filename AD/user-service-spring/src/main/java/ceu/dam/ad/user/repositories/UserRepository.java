package ceu.dam.ad.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ceu.dam.ad.user.model.User;


public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
}
