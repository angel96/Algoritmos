package ei3.evento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import us.lsi.stream.Stream2;

public class ProblemaEvento {

	public static List<String> tipos;
	public static Map<String, List<Comida>> comidasPorTipo;
	public static Integer presupuestoTotal;
	public static Integer sumaVotos;

	public ProblemaEvento(String file) {
		super();
		leeComidas(file);
	}

	public static void leeComidas(String file) {
		List<String> ls = Stream2.fromFile(file).toList();
		comidasPorTipo = new HashMap<String, List<Comida>>();
		tipos = Lists.newArrayList();
		sumaVotos = 0;
		int index = 0;
		for (String s : ls) {
			String[] at = Stream2.fromString(s, ",").<String> toArray((int x) -> new String[x]);
			Preconditions.checkArgument(at.length == 6);
			Comida a = Comida.create(index, at);
			if (!comidasPorTipo.containsKey(a.getTipo())) {
				tipos.add(a.getTipo());
				comidasPorTipo.put(a.getTipo(), new ArrayList<Comida>());
			}
			comidasPorTipo.get(a.getTipo()).add(a);
			sumaVotos += a.getVotos();
			index++;
		}
	}

	public static ProblemaEvento create(String file) {
		return new ProblemaEvento(file);
	}

	/**
	 * 
	 * Edicion y modelado del problema
	 * 
	 */
	public static Map<String, List<Comida>> getComidasPorTipo() {
		return comidasPorTipo;
	}

	public static List<Comida> getComidas() {
		List<Comida> comidas = new ArrayList<Comida>();
		for(List<Comida> comidasAux: getComidasPorTipo().values()){
			comidas.addAll(comidasAux);
		}
		return comidas;
	}

	public static Integer getVotosPlato(int index) {
		return ProblemaEvento.getComidas().get(index).getVotos();
	}

	public static Integer getCostePlato(int index) {
		return ProblemaEvento.getComidas().get(index).getPrecio().intValue();
	}

	public static boolean getEsCaliente(int index) {
		return ProblemaEvento.getComidas().get(index).esCaliente();
	}

	public static boolean getEsVegetariano(int index) {
		return ProblemaEvento.getComidas().get(index).esVegetariano();
	}
}
