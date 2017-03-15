
package us.lsi.pd.carnaval;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.carnaval.ProblemaCarnavalPD.Alternativa;
import us.lsi.pl.carnaval.Agrupacion;

public class TestCarnavalPD {

	public static String ruta = "./ficheros/";

	public static void main(String[] args) {

		AlgoritmoPD.isRandomize = false;
		ProblemaCarnavalPD p = ProblemaCarnavalPD.create(ruta + "agrupacionesCarnaval.txt", 10000);
		System.out.println("\n========================================================================");
		System.out.println("Alternativas " + p.getAlternativas());
		System.out.println("\n========================================================================");
		System.out.println("Caso base " + p.esCasoBase());
		System.out.println("\n========================================================================");
		System.out.println("Solucion Caso Base " + p.getSolucionCasoBase());
		System.out.println("\n========================================================================");
		System.out.println("Tipo " + p.getTipo());
		System.out.println("\n========================================================================");
		System.out.println("Problema inicial = " + p);
		System.out.println("\n========================================================================");
		//
		AlgoritmoPD<List<Agrupacion>, Alternativa> a = Algoritmos.createPD(p);
		a.ejecuta();
		//
		a.showAllGraph(ruta + "pruebaCarnaval.gv", "Carnaval", p);
		System.out.println("Solucion Parcial: " + a.getSolucionParcial(p));
		System.out.println("\n========================================================================");

		
		
		System.out.println("\n========================================================================");

		System.out.println("Solucion = "
				+ a.getSolucion(p).stream().collect(Collectors.groupingBy(x -> "\n" + x.getModalidad() + " ")));
		System.out.println("\nPresupuesto = "
				+ a.getSolucion(p).stream().mapToInt(x-> x.getSatisfaccionEspectadores()).sum());
		System.out.println("\nCoste = "
				+ a.getSolucion(p).stream().mapToInt(x-> x.getCoste()).sum());
				
	}

}