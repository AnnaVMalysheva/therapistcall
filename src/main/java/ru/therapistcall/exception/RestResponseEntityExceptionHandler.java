package ru.therapistcall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.therapistcall.dtos.ErrorDto;


@ControllerAdvice
public class RestResponseEntityExceptionHandler {

	@ResponseBody
	@ExceptionHandler(value = {Exception.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ErrorDto handleApiException(Exception ex) {
		String message = ex.getMessage();
		return new ErrorDto(400, null, message);
	}
}
