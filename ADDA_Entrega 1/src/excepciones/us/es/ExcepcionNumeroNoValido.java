package excepciones.us.es;

public class ExcepcionNumeroNoValido extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcepcionNumeroNoValido(){ super();}
	
	public ExcepcionNumeroNoValido(String s){ super(s);}

}
