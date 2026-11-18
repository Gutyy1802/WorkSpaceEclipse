package ceu.dam.psp.examen.Configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ceu.dam.psp.examen.exceptions.ErrorPagoException;
import ceu.dam.psp.examen.exceptions.NoAutorizadoException;
import ceu.dam.psp.examen.exceptions.NoExisteException;
import ceu.dam.psp.examen.exceptions.UsuarioDuplicadoException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(exception = NoExisteException.class)
	public ResponseEntity<String> handle(NoExisteException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler(exception = ErrorPagoException.class)
	public ResponseEntity<String> handle(ErrorPagoException e) {
		log.error("Eror inesperado: " + e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
	
	@ExceptionHandler(exception = UsuarioDuplicadoException.class)
	public ResponseEntity<String> handle(UsuarioDuplicadoException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(exception = NoAutorizadoException.class)
	public ResponseEntity<String> handle(NoAutorizadoException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	
	@ExceptionHandler(exception = Exception.class)
	public ResponseEntity<String> handle(Exception e) {
		log.error("Eror inesperado: " + e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
	
	@ExceptionHandler(exception = MethodArgumentNotValidException.class)
	public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
		String ms = "Error validacion: " + e.getFieldError().getField() + " - " + e.getFieldError().getDefaultMessage();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ms);
	}
}
