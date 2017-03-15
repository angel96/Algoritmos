package us.lsi.bt.carnaval;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

import us.lsi.bt.EstadoBT;
import us.lsi.bt.carnaval.EstadoCarnaval.Alternativa;
import us.lsi.pl.carnaval.Agrupacion;
import us.lsi.pl.carnaval.Agrupacion.TipoModalidad;
import us.lsi.pl.carnaval.ProblemaCarnaval;

public class EstadoCarnaval implements EstadoBT<List<Agrupacion>, Alternativa> {

	public enum Alternativa {
		Yes, No
	}

	int index = 0;
	Integer presupuestoRestante;
	Integer satisfaccionAcumulada = 0;
	List<Agrupacion> agrupaciones;
	List<Agrupacion> agrupacionesResultantes;

	public EstadoCarnaval() {
		super();
		index = ProblemaCarnaval.getAgrupacionesDisponibles().size();
		presupuestoRestante = ProblemaCarnaval.presupuestoInicial;
		agrupaciones = ProblemaCarnaval.getAgrupacionesDisponibles();
		agrupacionesResultantes = Lists.newArrayList();
	}

	@Override
	public void avanza(Alternativa a) {
		index--;
		if (a == Alternativa.Yes) {
			satisfaccionAcumulada = satisfaccionAcumulada + agrupaciones.get(index).getSatisfaccionEspectadores();
			presupuestoRestante = presupuestoRestante - agrupaciones.get(index).getCoste();
			agrupacionesResultantes.add(agrupaciones.get(index));
		}

	}

	@Override
	public void retrocede(Alternativa a) {
		if (a == Alternativa.Yes) {
			agrupacionesResultantes.remove(agrupaciones.get(index));
			satisfaccionAcumulada = satisfaccionAcumulada - agrupaciones.get(index).getSatisfaccionEspectadores();
			presupuestoRestante = presupuestoRestante + agrupaciones.get(index).getCoste();
		}
		index++;
	}

	@Override
	public int size() {
		return index;
	}

	@Override
	public boolean isFinal() {

		return index == 0;
	}

	@Override
	public List<Alternativa> getAlternativas() {
		List<Alternativa> res = Lists.newArrayList();
		res.add(Alternativa.No);
		res.add(ProblemaCarnaval.getAgrupacion(index - 1).getCoste() <= presupuestoRestante ? Alternativa.Yes : null);
		return res;
	}

	@Override
	public List<Agrupacion> getSolucion() {
		boolean comp = false, cuart = false, coro = false, chirigota = false;
		for (int i = 0; i < agrupacionesResultantes.size(); i++) {
			if (agrupacionesResultantes.get(i).getModalidad() == TipoModalidad.COMPARSA) {
				comp = true;
			} else if (agrupacionesResultantes.get(i).getModalidad() == TipoModalidad.CORO) {
				coro = true;
			} else if (agrupacionesResultantes.get(i).getModalidad() == TipoModalidad.CUARTETO) {
				cuart = true;
			} else if (agrupacionesResultantes.get(i).getModalidad() == TipoModalidad.CHIRIGOTA) {
				chirigota = true;
			}
		}
		// if(satisfaccionAcumulada == 360)
		// System.out.println(res + " \nsatisfaccion: "
		// + agrupacionesResultantes.stream().mapToInt(x ->
		// x.getSatisfaccionEspectadores()).sum()
		// + "\ncoste : " + agrupacionesResultantes.stream().mapToInt(x ->
		// x.getCoste()).sum());
		return comp && cuart && coro && chirigota ? IntStream.range(0, agrupacionesResultantes.size()).boxed()
				.map(x -> agrupacionesResultantes.get(x)).collect(Collectors.toList()) : null;
	}

	@Override
	public Double getObjetivo() {
		return (double) this.satisfaccionAcumulada;
	}

	@Override
	public Double getObjetivoEstimado(Alternativa a) {
		return this.satisfaccionAcumulada.doubleValue() + this.getCota(a);
	}

	public Double getCota(Alternativa a){
		Integer satis = 0;
		Integer presup = 0;
		Agrupacion ag = agrupaciones.get(index);
		if(a.equals(Alternativa.Yes)){
			
			satis = this.satisfaccionAcumulada + ag.getSatisfaccionEspectadores();
			presup = this.presupuestoRestante - ag.getCoste();
		} else {
			satis = this.satisfaccionAcumulada;
			presup = this.presupuestoRestante;
		}
		for(int i = this.agrupaciones.size(); i> index; i--){
			if(ag.getCoste()> this.presupuestoRestante){
				break;
			}
			satis = satis + this.agrupaciones.get(i).getSatisfaccionEspectadores();
			presup = presup - this.agrupaciones.get(i).getSatisfaccionEspectadores();
		}
		return satis.doubleValue();
	}

	public String toString() {
		return satisfaccionAcumulada + "," + presupuestoRestante;
	}

}