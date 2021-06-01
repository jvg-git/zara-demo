package com.inditex.zara.common.exceptions;

public class PriceUpdateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -290268830589214801L;

	/**
     * Constructs an <code>PriceUpdateException</code> with no
     * detail message.
     */
    public PriceUpdateException() {
        super();
    }

    /**
     * Constructs an <code>PriceUpdateException</code> with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public PriceUpdateException(String s) {
        super(s);
    }

    /**
     * Constructs a new <code>PriceUpdateException</code> with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public PriceUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public PriceUpdateException(Throwable cause) {
        super(cause);
    }
    
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
