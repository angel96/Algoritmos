package us.lsi.pl.carnaval;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.pl.carnaval.Agrupacion.TipoModalidad;
import us.lsi.stream.Stream2;

/**
 * <p>
 * Esta clase implementa el tipo ProblemaCarnaval. Los objetos correspondientes
 * son problemas generalizados.
 * </p>
 * <p>
 * Las propiedades de estos problemas son:
 * </p>
 * <ul>
 * <li>Presupuesto
 * <li>Index
 * <li>Agrupaciones Disponibles (propiedad compartida)
 * </ul>
 * 
 * 
 * 
 * @author FJO
 *
 */
public class ProblemaCarnaval {

	private static List<Agrupacion> agrupacionesDisponibles;
	private static Comparator<Agrupacion> ordenAgrupaciones;

	// Valores por defecto
	public static Integer presupuestoInicial = 7000;
	public static Integer ppc = 80;
	public static Integer minAgrupaciones = 1;

	private Integer presupuesto;
	private Integer index;

	public static ProblemaCarnaval create(Integer presupuesto, Integer index) {
		return new ProblemaCarnaval(presupuesto, index);
	}

	public static ProblemaCarnaval create() {
		return new ProblemaCarnaval(ProblemaCarnaval.presupuestoInicial, 0);
	}

	protected ProblemaCarnaval(Integer presupuesto, Integer index) {
		super();
		this.presupuesto = presupuesto;
		this.index = index;
	}

	/**
	 * El método lee el fichero de entrada y actualiza la lista
	 * ObjetosDisponibles que queda ordenada según el orden natural de las
	 * agrupaciones
	 * 
	 * @param fichero
	 *            Fichero que contiene las propiedades de los agrupaciones
	 *            disponibles. Un objeto por línea
	 */
	public static void leeAgrupacionesDisponibles(String fichero) {
		ordenAgrupaciones = Comparator.reverseOrder();
		agrupacionesDisponibles = Stream2.fromFile(fichero).<Agrupacion> map((String s) -> Agrupacion.create(s))
				.sorted(ordenAgrupaciones).collect(Collectors.<Agrupacion> toList());
	}

	public static List<Agrupacion> getAgrupacionesDisponibles() {
		return agrupacionesDisponibles;
	}

	public static Comparator<Agrupacion> getOrdenAgrupaciones() {
		return ordenAgrupaciones;
	}

	public Integer getPresupuesto() {
		return presupuesto;
	}

	public Integer getIndex() {
		return index;
	}

	public static Agrupacion getAgrupacion(int index) {
		return ProblemaCarnaval.getAgrupacionesDisponibles().stream().filter((Agrupacion a) -> a.getId().equals(index))
				.findFirst().get();
	}

	public static Integer getSatisfaccion(int index) {
		/*
		 * Valor a maximizar
		 */
		return ProblemaCarnaval.getAgrupacion(index).getSatisfaccionEspectadores();
	}

	public static Integer getCoste(int index) {
		return ProblemaCarnaval.getAgrupacion(index).getCoste();
	}

	public static List<Agrupacion> getAgrupacionSegunModalidad(TipoModalidad modalidad) {
		return getAgrupacionesDisponibles().stream().filter(x -> x.getModalidad().equals(modalidad))
				.collect(Collectors.toList());
	}

	public static Integer getNumeroAgrupacionesSegunModalidad(TipoModalidad modalidad) {
		return (int) getAgrupacionesDisponibles().stream().filter(x -> x.getModalidad().equals(modalidad)).count();
	}

	
}
