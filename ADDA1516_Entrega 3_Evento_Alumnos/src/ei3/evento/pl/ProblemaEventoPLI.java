package ei3.evento.pl;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ei3.evento.ProblemaEvento;
import us.lsi.pl.AlgoritmoPLI;

/**
 * 
 * @author Angel Delgado Luna
 *
 */
public class ProblemaEventoPLI {

	public static final Integer unicoPlato = 1, vegetariano = 1;
	public static final Integer caliente = 2;

	public static String getConstraints() {
		String r = "";

		/**
		 * ProblemaEvento.getVotosPlato(x), AlgoritmoPLI.getVariable("x", x)
		 */
		r = "max: "
				+ IntStream
						.range(0, ProblemaEvento.getComidas().size()).boxed().map(x -> AlgoritmoPLI
								.getFactor(ProblemaEvento.getVotosPlato(x), AlgoritmoPLI.getVariable("x", x)))
						.collect(Collectors.joining("+"))
				+ ";\n\n";

		r = r + IntStream.range(0, ProblemaEvento.getComidas().size()).boxed()
				.map(x -> AlgoritmoPLI.getFactor(ProblemaEvento.getCostePlato(x), AlgoritmoPLI.getVariable("x", x)))
				.collect(Collectors.joining("+")) + "<=" + ProblemaEvento.presupuestoTotal + ";\n\n";
		/**
		 * Restricciones para un unico plato, siendo cada uno de un tipo
		 */
		for (int i = 0; i < ProblemaEvento.getComidas().size(); i++) {
			if (ProblemaEvento.getComidas().get(i).getTipo().equals("entrante")) {
				r = r + "+" + AlgoritmoPLI.getVariable("x", i);
			}
		}
		r = r + "=";
		r = r + ProblemaEventoPLI.unicoPlato;
		r = r + ";\n\n";

		for (int i = 0; i < ProblemaEvento.getComidas().size(); i++) {
			if (ProblemaEvento.getComidas().get(i).getTipo().equals("primero")) {
				r = r + "+" + AlgoritmoPLI.getVariable("x", i);
			}
		}
		r = r + "=";
		r = r + ProblemaEventoPLI.unicoPlato;
		r = r + ";\n\n";

		for (int i = 0; i < ProblemaEvento.getComidas().size(); i++) {
			if (ProblemaEvento.getComidas().get(i).getTipo().equals("segundo")) {
				r = r + "+" + AlgoritmoPLI.getVariable("x", i);
			}
		}
		r = r + "=";
		r = r + ProblemaEventoPLI.unicoPlato;
		r = r + ";\n\n";

		for (int i = 0; i < ProblemaEvento.getComidas().size(); i++) {
			if (ProblemaEvento.getComidas().get(i).getTipo().equals("postre")) {
				r = r + "+" + AlgoritmoPLI.getVariable("x", i);
			}
		}
		r = r + "=";
		r = r + ProblemaEventoPLI.unicoPlato;
		r = r + ";\n\n";
		// Restriccion de platos calientes
		for (int i = 0; i < ProblemaEvento.getComidas().size(); i++) {
			if (ProblemaEvento.getEsCaliente(i)) {
				r = r + "+" + AlgoritmoPLI.getVariable("x", i);
			}
		}
		r = r + ">=";
		r = r + ProblemaEventoPLI.caliente;
		r = r + ";\n\n";
		// Restriccion de platos vegetarianos
		for (int i = 0; i < ProblemaEvento.getComidas().size(); i++) {
			if (ProblemaEvento.getEsVegetariano(i)) {
				r = r + "+" + AlgoritmoPLI.getVariable("x", i);
			}
		}
		r = r + ">=";
		r = r + ProblemaEventoPLI.vegetariano;
		r = r + ";\n\n";

		// Tipo de variables

		r = r + "bin ";

		for (int i = 0; i < ProblemaEvento.getComidas().size() - 1; i++) {
			r = r + AlgoritmoPLI.getVariable("x", i);
			r = r + ",";
		}
		// Dato de la coma del ultimo valor ayudado por la profesora
		r = r + AlgoritmoPLI.getVariable("x", ProblemaEvento.getComidas().size() - 1);
		r = r + ";";
		System.out.println(r);
		return r;
	}
}
