package com.ako.bankingapp.common;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
		String detail = ex.getBindingResult().getFieldErrors().stream()
			.map(err -> err.getField() + ": " + err.getDefaultMessage())
			.collect(Collectors.joining("; "));
		ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		pd.setDetail(detail);
		return pd;
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ProblemDetail handleBadCredentials(BadCredentialsException ex) {
		ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
		pd.setDetail("Invalid email or password");
		return pd;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ProblemDetail handleConflict(DataIntegrityViolationException ex) {
		ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
		pd.setDetail("Resource conflict");
		return pd;
	}

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleGeneric(Exception ex) {
		ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		pd.setDetail("Unexpected error");
		return pd;
	}
}