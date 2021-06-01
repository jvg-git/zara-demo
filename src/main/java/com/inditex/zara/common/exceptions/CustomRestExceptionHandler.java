package com.inditex.zara.common.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.inditex.zara.common.ErrorCode;
import com.inditex.zara.common.ErrorResponse;


@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(CustomRestExceptionHandler.class);
	
	// 400

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final ErrorResponse errorResponse = ErrorResponse.of(ex.getLocalizedMessage(), ErrorCode.ARGUMENT_NOT_VALID,
				HttpStatus.BAD_REQUEST, errors);
		return handleExceptionInternal(ex, (Object) errorResponse, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info("new bind-exception: " + ex.getClass().getName());

		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final ErrorResponse apiError = ErrorResponse.of(ex.getLocalizedMessage(), ErrorCode.BIND_EXCEPTION,
				HttpStatus.BAD_REQUEST, errors);
		return handleExceptionInternal(ex, (Object) apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
				+ ex.getRequiredType();
		errors.add(error);
		final ErrorResponse apiError = ErrorResponse.of(ex.getLocalizedMessage(), ErrorCode.TYPE_MISMATCH,
				HttpStatus.BAD_REQUEST, errors);
		return handleExceptionInternal(ex, (Object) apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getRequestPartName() + " part is missing";
		final List<String> errors = new ArrayList<String>();
		errors.add(error);
		final ErrorResponse apiError = ErrorResponse.of(ex.getLocalizedMessage(), ErrorCode.MISSING_PART,
				HttpStatus.BAD_REQUEST, errors);
		return handleExceptionInternal(ex, (Object) apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getParameterName() + " parameter is missing";
		final List<String> errors = new ArrayList<String>();
		errors.add(error);
		final ErrorResponse apiError = ErrorResponse.of(ex.getLocalizedMessage(), ErrorCode.MISSING_PARAMETER,
				HttpStatus.BAD_REQUEST, errors);
		return handleExceptionInternal(ex, (Object) apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

	// 404

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
		final List<String> errors = new ArrayList<String>();
		errors.add(error);
		final ErrorResponse apiError = ErrorResponse.of(ex.getLocalizedMessage(), ErrorCode.NO_HANDLER,
				HttpStatus.BAD_REQUEST, errors);
		return handleExceptionInternal(ex, (Object) apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	// 405

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		return super.handleAsyncRequestTimeoutException(ex, headers, status, webRequest);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
		final List<String> errors = new ArrayList<String>();
		errors.add(builder.substring(0, builder.length() - 2));
		final ErrorResponse apiError = ErrorResponse.of(ex.getLocalizedMessage(), ErrorCode.REQ_METHOD_NOT_SUPPORTED,
				HttpStatus.METHOD_NOT_ALLOWED, errors);
		return handleExceptionInternal(ex, (Object) apiError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED,
				request);
	}

	// 415

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));
		final List<String> errors = new ArrayList<String>();
		errors.add(builder.substring(0, builder.length() - 2));
		final ErrorResponse apiError = ErrorResponse.of(ex.getLocalizedMessage(), ErrorCode.MEDIA_TYPE_NOT_SUPPORTED,
				HttpStatus.UNSUPPORTED_MEDIA_TYPE, errors);
		return handleExceptionInternal(ex, (Object) apiError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE,
				request);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		final List<String> errors = new ArrayList<String>();
		errors.add(error);
		final ErrorResponse apiError = ErrorResponse.of(ex.getLocalizedMessage(), ErrorCode.ARG_TYPE_MISSMATCH,
				HttpStatus.BAD_REQUEST, errors);
		return handleExceptionInternal(ex, (Object) apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}
		final ErrorResponse apiError = ErrorResponse.of(ex.getLocalizedMessage(), ErrorCode.CONSTRAIN_VIOLATION,
				HttpStatus.BAD_REQUEST, errors);
		return handleExceptionInternal(ex, (Object) apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	// 500

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) throws InterruptedException, ExecutionException {
		logger.info(ex.getClass().getName());
		logger.error("Error {} catched in handleAll: {}", ex.getClass().getName(), ex.getLocalizedMessage());
		final List<String> errors = new ArrayList<String>();
		errors.add(String.format("Internal Server Error: Error %s catched in handleAll: %s", ex.getClass().getName(), ex.getLocalizedMessage() != null && !ex.getLocalizedMessage().isEmpty() ? ex.getLocalizedMessage() : ex.getStackTrace().toString()));
		errors.add("Unable to complete request");
		errors.add("Related information was recorded in log.");
		final ErrorResponse apiError = ErrorResponse.of("Internal server error", ErrorCode.SERVER_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR, errors);
		return handleExceptionInternal(ex, (Object) apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request); //
	}

	@ExceptionHandler(value = { PriceUpdateException.class })
	public ResponseEntity<Object> handlePriceUpdateException(final PriceUpdateException ex,
			final WebRequest request) {
		logger.info("New Price Update Exception: " + ex.getClass().getName());

		final List<String> errors = new ArrayList<String>();
		errors.add("Error updating prices");
		errors.add("Internal Server Error");
		final ErrorResponse apiError = ErrorResponse.of("Update Price Error", ErrorCode.PRICE_UPDATE,
				HttpStatus.SERVICE_UNAVAILABLE, errors);
		return handleExceptionInternal(ex, (Object) apiError, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
	}
	
	@ExceptionHandler(value = { InvalidPriceRequestException.class })
	public ResponseEntity<Object> handleInvalidPriceException(final InvalidPriceRequestException ex,
			final WebRequest request) {
		logger.info("Invalid Price Request Exception: " + ex.getClass().getName());

		final List<String> errors = new ArrayList<String>();
		errors.add("Error in price request");
		errors.add("Error: fields brandId, productId and fechaAplicacion should not be null");
		final ErrorResponse apiError = ErrorResponse.of("Price Request Error", ErrorCode.BAD_PRICE_REQUEST,
				HttpStatus.SERVICE_UNAVAILABLE, errors);
		return handleExceptionInternal(ex, (Object) apiError, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
	}

	@ExceptionHandler(value = { AsyncMethodErrorException.class })
	public ResponseEntity<Object> handleAsyncMethodErrorException(final AsyncMethodErrorException ex,
			final WebRequest request) {
		logger.info("Async method error exsception: " + ex.getClass().getName());

		final List<String> errors = new ArrayList<String>();
		errors.add("Error in Async method");
		errors.add(ex.getMessage());
		final ErrorResponse apiError = ErrorResponse.of("Price Request Error", ErrorCode.ASYNC_ERROR,
				HttpStatus.SERVICE_UNAVAILABLE, errors);
		return handleExceptionInternal(ex, (Object) apiError, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
	}

	@ExceptionHandler(value = { CompletionException.class })
	public void handleCompletionException(final CompletionException ex, final WebRequest request) throws InterruptedException, ExecutionException {
		ex.setStackTrace(null);
		logger.info("Cached CompletionException: to handleAll");
		handleAll(ex, request);
	}

	@ExceptionHandler(value = { RequestParamsNotAllowed.class })
	public ResponseEntity<Object> handleRequestedParamsException(final RequestParamsNotAllowed ex,
			final WebRequest request) {
		final List<String> errors = new ArrayList<String>();
		errors.add("Error handling Requested Params");
		if (ex.getErrorDetails() != null && !ex.getErrorDetails().isEmpty()) {
			errors.addAll(ex.getErrorDetails());
		}
		errors.add("Please update your request, and try again later");
		final ErrorResponse apiError = ErrorResponse.of(ex.getMessage(), ErrorCode.PARAMS_NOT_ALLOWED,
				HttpStatus.BAD_REQUEST, errors);
		return handleExceptionInternal(ex, (Object) apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { RequiredParamNotFound.class })
	public ResponseEntity<Object> handleParamsNotFoundException(final RequiredParamNotFound ex,
			final WebRequest request) {
		final List<String> errors = new ArrayList<String>();
		errors.add("Required Params not found");
		if (ex.getErrorDetails() != null && !ex.getErrorDetails().isEmpty()) {
			errors.addAll(ex.getErrorDetails());
		}
		errors.add("Please update your request now, and try again later");
		final ErrorResponse apiError = ErrorResponse.of(ex.getMessage(), ErrorCode.PARAM_NOT_FOUND,
				HttpStatus.BAD_REQUEST, errors);
		return handleExceptionInternal(ex, (Object) apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
