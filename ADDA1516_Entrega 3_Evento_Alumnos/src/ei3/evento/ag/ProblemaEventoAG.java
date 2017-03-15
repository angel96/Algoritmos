package ei3.evento.ag;

import java.util.List;

import ei3.evento.Comida;
import ei3.evento.ProblemaEvento;
import us.lsi.ag.ProblemaAGIndex;
import us.lsi.ag.agchromosomes.IndexChromosome;

public class ProblemaEventoAG implements ProblemaAGIndex<SolucionEventoAG> {
	private double fitness;

	public ProblemaEventoAG(String file) {
		ProblemaEvento.leeComidas(file);
	}

	public List<Comida> getComidas() {
		return ProblemaEvento.getComidas();
	}

	public Double fitnessFunction(IndexChromosome cr) {
		// ARROZ, MUSLO, CAFE Y CROQUETAS --> OPTIMO DE 26 VOTOS y presupuesto
		// 10
		List<Integer> ls = cr.decode();
		Integer voto = 0;
		double precio = 0;
		boolean caliente = false, vegetariano = false, tipoBool = false;
		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i) == 1) {
				Comida c = getComidas().get(i);
				voto += ls.get(i) * c.getVotos();
				precio += ls.get(i) * c.getPrecio();
				tipoBool = tipo(getComidas(), ls);
				if (c.esCaliente()) {
					caliente = true;
				}
				if (c.esVegetariano()) {
					vegetariano = true;
				}
			}

		}
		double dif = ProblemaEvento.presupuestoTotal - precio;
		if (dif >= 0) {
			if(caliente && vegetariano && tipoBool){
				fitness = voto;
			}
		} else {
			fitness = voto - 10000000 * (dif*dif);
		}

		return fitness;
	}

	private boolean tipo(List<Comida> comidas, List<Integer> ls) {

		boolean entrante = false, primero = false, segundo = false, postre = false;
		for (int i = 0; i < comidas.size(); i++) {
			if(ls.get(i) == 1){
				Comida c = comidas.get(i);
				if(c.getTipo().equals("entrante")){
					entrante = true;
				} else if(c.getTipo().equals("primero")){
					primero = true;
				} else if(c.getTipo().equals("segundo")){
					segundo = true;
				} else if(c.getTipo().equals("postre")){
					postre = true;
				}
			}
			
		}
		return entrante && primero && segundo && postre;

	}

	public SolucionEventoAG getSolucion(IndexChromosome chromosome) {
		SolucionEventoAG s = SolucionEventoAG.create();
		List<Integer> ls = chromosome.decode();
		for (int i = 0; i < this.getObjectsNumber(); i++) {
			s = s.add(ProblemaEvento.getComidas().get(i), ls.get(i));
		}
		return s;
	}

	@Override
	public Integer getObjectsNumber() {
		return ProblemaEvento.getComidas().size();
	}

}
