package us.lsi.pl.carnaval;

/**
 * <p>
 * Esta clase implementa el tipo Agrupación.
 * </p>
 * <p>
 * Las propiedades de estos objetos son:
 * </p>
 * <ul>
 * <li>Modalidad
 * <li>Premio en el último COAC
 * <li>Número de agrupaciones de esta modalidad con este premio
 * </ul>
 * 
 * 
 * 
 * @author FJO
 *
 */
public class Agrupacion implements Comparable<Agrupacion> {

	public static enum PremioCOAC {
		PRIMERO, SEGUNDO, TERCERO, CUARTO, RESTO
	};

	public static enum TipoModalidad {
		CORO, CHIRIGOTA, COMPARSA, CUARTETO
	};

	private int id;
	private TipoModalidad modalidad;
	private PremioCOAC premio;
	private Integer ppc;

	public static Agrupacion create(TipoModalidad mod, Integer premio, Integer repeticiones, Integer ppc) {
		return new Agrupacion(mod, premio, repeticiones, ppc);
	}

	/**
	 * @param s
	 *            Una línea de un fichero de texto
	 * @return Construye un objeto mochila a partir de una línea de un fichero
	 */
	public static Agrupacion create(String s) {
		return new Agrupacion(s);
	}

	Agrupacion(TipoModalidad mod, Integer premio, Integer id, Integer ppc) {
		this.id =id;
		this.modalidad = mod;
		this.ppc = ppc;

		switch (premio) {
		case 1:
			this.premio = PremioCOAC.PRIMERO;
			break;
		case 2:
			this.premio = PremioCOAC.SEGUNDO;
			break;
		case 3:
			this.premio = PremioCOAC.TERCERO;
			break;
		case 4:
			this.premio = PremioCOAC.CUARTO;
			break;
		default:
			this.premio = PremioCOAC.RESTO;
		}
	}

	/**
	 * String con formato: ID, Tipo de Agrupación, Premio en el COAC
	 * 
	 * @param s
	 */
	Agrupacion(String s) {
		String[] v = s.split("[ ,]");
		Integer ne = v.length;
		if (ne != 3)
			throw new IllegalArgumentException("Formato no adecuado en línea  " + s);
		this.id = new Integer(v[0]);
		this.modalidad = TipoModalidad.valueOf(v[1]);
		ppc = ProblemaCarnaval.ppc;

		Integer p = 0;
		if(!v[2].equals("Otro")){
			p = new Integer(v[2]);
		}
		
		switch (p) {
		case 1:
			this.premio = PremioCOAC.PRIMERO;
			break;
		case 2:
			this.premio = PremioCOAC.SEGUNDO;
			break;
		case 3:
			this.premio = PremioCOAC.TERCERO;
			break;
		case 4:
			this.premio = PremioCOAC.CUARTO;
			break;
		default:
			this.premio = PremioCOAC.RESTO;
		}
	}

	@Override
	public int compareTo(Agrupacion o) {
		int r = getId().compareTo(o.getId());
		return r;
	}

	@Override
	public String toString() {
		return "<"+id+", " + modalidad + ", " + premio + ", "+getCoste()+">";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modalidad == null) ? 0 : modalidad.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Agrupacion))
			return false;
		Agrupacion other = (Agrupacion) obj;

		return this.id == other.id && other.modalidad.equals(this.modalidad)
				&& this.premio == other.premio;
	}

	public Integer getId() {
		return this.id;
	}

	public TipoModalidad getModalidad() {
		return modalidad;
	}

	public PremioCOAC getPremioCOAC() {
		return premio;
	}

	public Integer getSatisfaccionEspectadores(){		
		return getSatisfaccionEspectadoresModalidad();
	}
	
	
	private Integer getSatisfaccionEspectadoresModalidad() {
		Integer sat;
		switch (modalidad) {
		case CORO:
			sat = 30;
			break;
		case CHIRIGOTA:
			sat = 80;
			break;
		case COMPARSA:
			sat = 100;
			break;
		case CUARTETO:
			sat = 50;
			break;
		default:
			sat = 0;
		}
		return sat;
	}
	
	public Integer getCoste(){
		int precioBase = getCosteContratacion();

		return precioBase + getCostePorPremioCOAC();
	}

	/**
	 * Coste de contratación de la agrupación según su número de componentes
	 * 
	 * @param ppc
	 *            Pago por componente
	 * 
	 * @return
	 */
	private Integer getCosteContratacion() {
		return getComponentesPorModalidad() * ppc;
	}
	
	private Integer getCostePorPremioCOAC() {
		int coefPosicion;
		switch (premio) {
		case PRIMERO:
			coefPosicion = 1000;
			break;
		case SEGUNDO:
			coefPosicion = 700;
			break;
		case TERCERO:
			coefPosicion = 500;
			break;
		case CUARTO:
			coefPosicion = 400;
			break;
		default:
			coefPosicion = 0;
		}
		return coefPosicion;
	}
	
	public Integer getComponentesPorModalidad() {
		int ncomponentes;
		switch (modalidad) {
		case CORO:
			ncomponentes = 50;
			break;
		case CHIRIGOTA:
			ncomponentes = 12;
			break;
		case COMPARSA:
			ncomponentes = 15;
			break;
		case CUARTETO:
			ncomponentes = 5;
			break;
		default:
			ncomponentes = 0;
		}
		return ncomponentes;
	}
}
