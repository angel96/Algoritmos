package ei3.evento.pl;

import java.util.ArrayList;
import java.util.List;

import ei3.evento.Comida;
import ei3.evento.ProblemaEvento;
import us.lsi.pl.AlgoritmoPLI;

public class TestEvento {
	public static final List<Comida> res = new ArrayList<Comida>();

	public static List<Comida> getMenu() {
		return new ArrayList<Comida>(res);
	}

	public static void main(String[] args) {
		ProblemaEvento.create("Comidas.txt");
		ProblemaEvento.presupuestoTotal = 10;

		String r = ProblemaEventoPLI.getConstraints();

		AlgoritmoPLI a = AlgoritmoPLI.create();

		a.setConstraints(r);

		a.ejecuta();

		System.out.println("=====Variables====");
		for (int j = 0; j < a.getNumVar(); j++) {

			System.out.println(a.getName(j) + " = " + a.getSolucion()[j]);

			if (a.getSolucion()[j] == 1) {
				res.add(ProblemaEvento.getComidas().get(j));
			}
		}
		System.out.println("_______________________");
		
		System.out.println("Objetivo: " + a.getObjetivo());
		
		System.out.println("_______________________");

		System.out.println("Menu: " + getMenu() + ", Votos: " + getMenu().stream().mapToInt(x -> x.getVotos()).sum()
				+ ", Presupuesto: " + ProblemaEvento.presupuestoTotal);
		
	}

}
