package br.com.upperselenium.base.exception;

public class RunningException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * How you should call the catch block statement:
	 *
	 * Sample:
	 *      [...]
	 * 		} catch (Throwable t) {
	 *			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.[CONSTANT_NAME]), t);
	 *		}
	 * 
	 * @param message
	 * @param exceptionCause
	 */
	public RunningException(String message, Throwable exceptionCause) {
		super(message, exceptionCause);
	}
}
