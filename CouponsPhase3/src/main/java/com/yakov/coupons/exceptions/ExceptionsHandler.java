package com.yakov.coupons.exceptions;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yakov.coupons.beans.ErrorBean;


/**
 * Exception handler
 * @author Yakov
 *
 */
@ResponseBody
@ControllerAdvice
public class ExceptionsHandler {    
    @ExceptionHandler(ApplicationException.class)
	public ErrorBean handleApplicationException(HttpServletResponse response, ApplicationException ae) {
			int internalErrorCode = ae.getErrorType().getErrorCode();
			String internalMessage = ae.getMessage();
			String externalMessage = ae.getErrorType().getErrorMessage();									
			ErrorBean errorBean = new ErrorBean(internalErrorCode, internalMessage, externalMessage);
			response.setStatus(ae.getErrorType().getErrorCode());
			ae.printStackTrace();
			return errorBean;
	}


    
    
	@ExceptionHandler({ Exception.class, Error.class })
	public ErrorBean handleError(HttpServletResponse response, Throwable exception) {
		String internalMessage = exception.getMessage();
		ErrorBean errorBean = new ErrorBean(601, internalMessage, "General error");
		exception.printStackTrace();
		response.setStatus(601);
		return errorBean;
	}
}