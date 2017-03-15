package us.lsi.bt.carnaval;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.AlgoritmoBT;
import us.lsi.bt.carnaval.EstadoCarnaval.Alternativa;
import us.lsi.pl.carnaval.Agrupacion;
import us.lsi.pl.carnaval.ProblemaCarnaval;

public class TestCarnavalBT {

	/**
	 * @param args
	 *            Argumentos
	 */
	public static void main(String[] args) {
		AlgoritmoBT.soloLaPrimeraSolucion = false;
		AlgoritmoBT.isRandomize = false;
		AlgoritmoBT.numeroDeSoluciones = 100;

		ProblemaCarnaval.presupuestoInicial = 10000;
		ProblemaCarnaval.leeAgrupacionesDisponibles("ficheros/agrupacionesCarnaval.txt");
		System.out.println("Agrupaciones disponibles: " + ProblemaCarnaval.getAgrupacionesDisponibles().stream()
				.collect(Collectors.groupingBy(x -> "\n" + x.getModalidad())));
		AlgoritmoBT.soloLaPrimeraSolucion = false;

		AlgoritmoBT<List<Agrupacion>, Alternativa> a = Algoritmos.createBT(ProblemaCarnavalBT.create());
		a.ejecuta();

		if (a.soluciones.isEmpty())
			System.out.println("Solucion no encontrada");
		else {
			System.out.println("\n" + "Solucion: "
					+ a.solucion.stream().collect(Collectors.groupingBy(x -> "\n" + x.getModalidad() + " ")));
			System.out
					.println("\nPuntuación: " + a.solucion.stream().mapToInt(x -> x.getSatisfaccionEspectadores()).sum());
			System.out.println("Presupuesto: " + a.solucion.stream().mapToInt(x -> x.getCoste()).sum());
		}
	}
}
