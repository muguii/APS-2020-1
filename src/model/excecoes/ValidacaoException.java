package model.excecoes;

import java.util.HashMap;
import java.util.Map;

public class ValidacaoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errors = new HashMap<>();
	
	public ValidacaoException(String msg) {
		super(msg);
	}
	
	public Map<String, String> getErrors() {
		return errors;
	}
	
	public void addError(String fieldName, String errorMensagem) {
		errors.put(fieldName, errorMensagem);
	}
	

}
