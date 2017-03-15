package us.lsi.flowgraph;

/**
 * Excepción que se dispara si no se encuentra solucíon al resolver 
 * una Red de Flujo
 * 
 * @author Miguel Toro
 *
 */
public class NoSeEncuentraSolucion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSeEncuentraSolucion() {
		super();
	}

	public NoSeEncuentraSolucion(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoSeEncuentraSolucion(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSeEncuentraSolucion(String message) {
		super(message);
	}

	public NoSeEncuentraSolucion(Throwable cause) {
		super(cause);
	}

}
