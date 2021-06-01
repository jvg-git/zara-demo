package com.inditex.zara.common.exceptions;

import java.util.List;

import javax.validation.ValidationException;

public class RequiredParamNotFound extends ValidationException {

	private static final long serialVersionUID = -1146114497694008492L;

	private List<String> errorDetails;

	/**
     * Creates a new <code>RequiredParamNotFound</code> with no
     * detail message.
     */
    public RequiredParamNotFound() {
        super();
    }
    
    /**
     * Creates new exception with the specified message and a detail
     * messages  list.
     * @param  s the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method). 
     * @param errorDetails list of details messages related with the exception
     */

    public RequiredParamNotFound(String s, List<String> errorDetails) {
    	super(s);
    	this.errorDetails = errorDetails;
    }
    /**
     * Constructs an <code>RequiredParamNotFound</code> with the
     * specified detail message.
     * @param   s   the detail message.
     */
    public RequiredParamNotFound(String s) {
        super(s);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     * <p>Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public RequiredParamNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public RequiredParamNotFound(Throwable cause) {
        super(cause);
    }
    
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
    
    /**
     * Get the list details messages related to the exception
     * @return - List of String with info related to the error.
     */
    public List<String> getErrorDetails() {
		return errorDetails;
	}

	/**
     * Set the list of details messages related to the exception
     * @param  errorDetails - List of related info to the error as String
     */
	public void setErrorDetails(List<String> errorDetails) {
		this.errorDetails = errorDetails;
	}
	
	

}
