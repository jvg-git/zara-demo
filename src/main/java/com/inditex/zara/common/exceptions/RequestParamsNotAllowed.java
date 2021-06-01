package com.inditex.zara.common.exceptions;

import java.util.List;

import javax.validation.ValidationException;

public class RequestParamsNotAllowed extends ValidationException {

	private static final long serialVersionUID = -4290566997471905060L;
	
	
	private List<String> errorDetails;

	/**
     * Creates new <code>RequestParamsNotAllowed</code> exception with no
     * detail message.
     */
    public RequestParamsNotAllowed() {
        super();
    }

	/**
     * Creates new exception with the specified message and a detail
     * messages  list.
     * @param  s the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method). 
     * @param errorDetails list of details messages related with the exception
     */
    public RequestParamsNotAllowed(String s, List<String> errorDetails) {
    	super(s);
    	this.errorDetails = errorDetails;
    }
	/**
     * Creates new exception with the specified message and a detail
     * messages  list.
     * @param  s the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method). 
     */
    public RequestParamsNotAllowed(String s) {
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
    public RequestParamsNotAllowed(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public RequestParamsNotAllowed(Throwable cause) {
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
     * Set the details messages list related to the new exception
     * @param errorDetails - List that contains related info of the error
     */
	public void setErrorDetails(List<String> errorDetails) {
		this.errorDetails = errorDetails;
	}
	
	

}

