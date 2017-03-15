package us.lsi.pd.carnaval;

import java.util.Comparator;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;
import us.lsi.pl.carnaval.Agrupacion;
import us.lsi.pl.carnaval.Agrupacion.TipoModalidad;
import us.lsi.pl.carnaval.ProblemaCarnaval;

public class ProblemaCarnavalPD implements ProblemaPD<List<Agrupacion>, ProblemaCarnavalPD.Alternativa> {

	public enum Alternativa {
		YES, NO
	};

	private static ProblemaCarnavalPD problemaInicial;
	private static Integer numeroAgrupaciones;
	private static Integer presupuestoLimite;
	private int index;
	public static List<Agrupacion> agrupaciones;
	private Integer coro = 0;
	private Integer cuarteto = 0;
	private Integer comparsa = 0;
	private Integer chirigota = 0;
	private Double valorSolucion = Double.MAX_VALUE;

	public static ProblemaCarnavalPD create(String fichero, Integer presupuestoLimite) {
		ProblemaCarnaval.leeAgrupacionesDisponibles(fichero);
		ProblemaCarnavalPD.agrupaciones = ProblemaCarnaval.getAgrupacionesDisponibles();
		ProblemaCarnavalPD.numeroAgrupaciones = ProblemaCarnavalPD.agrupaciones.size();
		ProblemaCarnavalPD.presupuestoLimite = presupuestoLimite;
		ProblemaCarnavalPD.problemaInicial = new ProblemaCarnavalPD(0, 0, presupuestoLimite, 0, 0, 0, 0);
		return ProblemaCarnavalPD.problemaInicial;
	}

	public static ProblemaCarnavalPD create(int index, Integer satisfaccion, Integer presupuestoRestante, Integer coro,
			Integer cuarteto, Integer comparsa, Integer chirigota) {

		ProblemaCarnavalPD p = new ProblemaCarnavalPD(index, satisfaccion, presupuestoRestante, coro, cuarteto,
				comparsa, chirigota);

		return p;
	}

	private Integer satisfaccionAcumulada;
	private Integer presupuestoRestante;

	private ProblemaCarnavalPD(int index, Integer satisfaccion, Integer presupuestoRestante, Integer coro,
			Integer cuarteto, Integer comparsa, Integer chirigota) {
		this.index = index;
		this.satisfaccionAcumulada = satisfaccion;
		this.presupuestoRestante = presupuestoRestante;
		this.coro = coro;
		this.cuarteto = cuarteto;
		this.comparsa = comparsa;
		this.chirigota = chirigota;
	}

	/**
	 * El objetivo será, contando con un presupuestoAcumulado limitado, P,
	 * organizar un festival carnavalesco con al menos una agrupación de cada
	 * categoría, de forma que se consiga la mayor satisfacción posible de los
	 * ciudadanos. Para ello el ayuntamiento tendrá un listado de agrupaciones
	 * disponibles para contratar, de la siguiente forma:
	 */
	// Objetivo: Maximizar la satisfaccion de la gente
	public Tipo getTipo() {
		return ProblemaPD.Tipo.Max;
	}

	// Tamaño del problema que va a ir a menos a medida que se van tomando
	// agrupaciones con los parametros ajustados
	public int size() {
		return ProblemaCarnavalPD.numeroAgrupaciones - index;
	}

	// Caso base, cuando el problema no puede continuar
	public boolean esCasoBase() {
		return this.presupuestoRestante == 0 || size() == ProblemaCarnaval.minAgrupaciones;
	}

	public Sp<Alternativa> getSolucionCasoBase() {
		// Soluciones del caso base
		Sp<Alternativa> res = null;
		if (this.presupuestoRestante == 0) {
			res = Sp.create(Alternativa.NO, 0.);
			valorSolucion = res.propiedad;
		} else {
			if (this.presupuestoRestante >= agrupaciones.get(index).getCoste()) {
				res = Sp.create(Alternativa.YES, agrupaciones.get(index).getSatisfaccionEspectadores().doubleValue());
			}
		}
		if (coro < 1 || chirigota < 1 || comparsa < 1 || cuarteto < 1) {
			res = null;
		}

		return res;
	}

	public int getNumeroSubProblemas(Alternativa a) {

		return 1;
	}

	public Sp<ProblemaCarnavalPD.Alternativa> seleccionaAlternativa(List<Sp<Alternativa>> ls) {
		// Algoritmo predeterminado, cambia el parametro del comparator segun el
		// tipo de problema
		Sp<Alternativa> r = ls.stream().filter(x -> x.propiedad != null).max(Comparator.naturalOrder()).orElse(null);
		this.valorSolucion = r.propiedad;
		return r;
	}

	public ProblemaPD<List<Agrupacion>, Alternativa> getSubProblema(Alternativa a, int np) {
		// Comprobar que np no es 0 y crear un subproblema de tipo
		// ProblemaCarnavalPD
		Preconditions.checkArgument(np == 0);
		ProblemaCarnavalPD p = null;

		if (a == Alternativa.YES) {
			/*
			 * index, satisfaccion, presupuestoRestante, comparsa, coro,
			 * chirigota, cuarteto
			 */
			if (agrupaciones.get(index).getModalidad().equals(TipoModalidad.COMPARSA)) {
				comparsa++;
			} else if (agrupaciones.get(index).getModalidad().equals(TipoModalidad.CORO)) {
				coro++;
			} else if (agrupaciones.get(index).getModalidad().equals(TipoModalidad.CUARTETO)) {
				cuarteto++;
			} else if (agrupaciones.get(index).getModalidad().equals(TipoModalidad.CHIRIGOTA)) {
				chirigota++;
			}
			p = ProblemaCarnavalPD.create(index + 1,
					this.satisfaccionAcumulada + agrupaciones.get(index).getSatisfaccionEspectadores(),
					this.presupuestoRestante - agrupaciones.get(index).getCoste(), coro, cuarteto, comparsa, chirigota);

		} else {
			p = ProblemaCarnavalPD.create(index + 1, this.satisfaccionAcumulada, this.presupuestoRestante, coro,
					cuarteto, comparsa, chirigota);
		}
		return p;
	}

	@Override
	public Sp<ProblemaCarnavalPD.Alternativa> combinaSolucionesParciales(Alternativa a, List<Sp<Alternativa>> ls) {
		return Sp.create(a, a.equals(Alternativa.YES)
				? agrupaciones.get(index).getSatisfaccionEspectadores() + ls.get(0).propiedad : ls.get(0).propiedad);
	}

	public List<Alternativa> getAlternativas() {

		List<Alternativa> res = Lists.newArrayList();
		Alternativa a = null;
		a = Alternativa.NO;
		res.add(a);
			res.add(this.presupuestoRestante >= ProblemaCarnaval.getCoste(index)? Alternativa.YES:Alternativa.NO);
		return res;
	}

	public List<Agrupacion> getSolucionReconstruida(Sp<Alternativa> sp) {
		List<Agrupacion> res = Lists.newArrayList();
		if (sp.alternativa == Alternativa.YES) {
			res.add(agrupaciones.get(index));
		}
		return res;
	}

	public List<Agrupacion> getSolucionReconstruida(Sp<Alternativa> sp, List<List<Agrupacion>> ls) {
		if (sp.alternativa.equals(Alternativa.YES)) {
			ls.get(0).add(agrupaciones.get(index));
		}
		return ls.get(0);
	}

	public Double getObjetivoEstimado(Alternativa a) {
		// return this.satisfaccionAcumulada + this.cotaSuperior(a);
		return this.satisfaccionAcumulada + this.valorSolucion;
	}

	/**
	 * @param Atributo
	 *            - Satisfacción acumulada Devuelve el valor que tenemos que
	 *            maximizar
	 */
	public Double getObjetivo() {
		return this.satisfaccionAcumulada.doubleValue();
	}

	/**
	 * Metodos String, hashCode y equals Auto - Generados
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + ((presupuestoRestante == null) ? 0 : presupuestoRestante.hashCode());
		result = prime * result + ((satisfaccionAcumulada == null) ? 0 : satisfaccionAcumulada.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProblemaCarnavalPD other = (ProblemaCarnavalPD) obj;
		if (index != other.index)
			return false;
		if (presupuestoRestante == null) {
			if (other.presupuestoRestante != null)
				return false;
		} else if (!presupuestoRestante.equals(other.presupuestoRestante))
			return false;
		if (satisfaccionAcumulada == null) {
			if (other.satisfaccionAcumulada != null)
				return false;
		} else if (!satisfaccionAcumulada.equals(other.satisfaccionAcumulada))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProblemaCarnavalPD [index=" + index + ", satisfaccionAcumulada=" + satisfaccionAcumulada
				+ ", presupuestoRestante=" + presupuestoRestante + "]";
	}

	public static Integer getPresupuestoLimite() {
		return presupuestoLimite;
	}

	public static void setPresupuestoLimite(Integer presupuestoLimite) {
		ProblemaCarnavalPD.presupuestoLimite = presupuestoLimite;
	}
}