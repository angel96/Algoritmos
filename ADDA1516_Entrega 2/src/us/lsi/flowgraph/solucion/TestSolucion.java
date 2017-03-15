package us.lsi.flowgraph.solucion;

import java.util.Map;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.flowgraph.FlowAlgorithm;
import us.lsi.flowgraph.FlowEdge;
import us.lsi.flowgraph.FlowGraph;
import us.lsi.flowgraph.FlowGraphFileExporter;
import us.lsi.flowgraph.FlowVertex;
import us.lsi.graphs.GraphsFileExporter;
import us.lsi.graphs.GraphsReader;
import us.lsi.pl.AlgoritmoPL;
import us.lsi.pl.ProblemaPL;

public class TestSolucion {

	/**
	 * @param args
	 *            Argumentos
	 */

	public static String ruta = "C:\\Users\\Angel Delgado Luna\\Desktop";

	public static void main(String[] args) {
		/**
		 * @author Angel Delgado Luna
		 */
		// TODO: crear un objeto de tipo FlowGraph mediante la factorÃ­a
		FlowGraph f = new FlowGraph(FlowEdge.factory);

		// TODO: cargar el grafo de flujo desde el fichero de texto mediante la
		// clase de utilidad GraphsReader

		/**
		 * @param res
		 *            Con true me leera el problema4 y con false me leera el
		 *            apartado 7 del problema 4
		 */

		Boolean res = false;
		f = res.equals(true)
				? (FlowGraph) GraphsReader.newGraph("files/problema4.txt", FlowVertex.factory, FlowEdge.factory, f)
				: (FlowGraph) GraphsReader.newGraph("files/problema4Ap7.txt", FlowVertex.factory, FlowEdge.factory, f);
		// TODO: imprimir el grafo creado por consola
		System.out.println(f);

		// TODO: crear una instancia del algoritmo FlowAlgorithm a partir del
		// grafo anterior

		/**
		 * @param result
		 *            --> Variable boolean, donde true es minimizar y false es
		 *            maximizar
		 */

		Boolean result = true;

		FlowAlgorithm instancia = FlowAlgorithm.create(f, result);

		// TODO: instanciar un objeto de tipo ProblemaPL para resolver el
		// problema
		ProblemaPL problema = instancia.getProblemaLP();

		// TODO: crear un objeto de tipo AlgoritmoPL mediante la clase de
		// utilidad Algoritmos
		AlgoritmoPL algoritmo = Algoritmos.createPL(problema);

		// TODO: ejecutar el algoritmo sobre el grafo creado
		algoritmo.ejecuta();

		// TODO: obtener el flujo sobre cada vértice y arista
		Map<FlowEdge, Double> edgeFlow = instancia.getEdgeFlow(algoritmo);
		Map<FlowVertex, Double> sourceFlow = instancia.getSourceFlow(algoritmo);
		Map<FlowVertex, Double> sinkFlow = instancia.getSinkFlow(algoritmo);

		/**
		 * Adaptación de los bucles realizados por @author Miguel Toro a Java 8
		 * realizados por @author Angel Delgado Luna
		 */

		System.out.println(
				"\nFlujo de Aristas========================================================================================");

		/**
		 * Obtengo la información de vertice a vertice
		 */

		edgeFlow.keySet().stream().forEach(x -> System.out.println(x.getFrom().getNombre() + "," + x.getTo().getNombre()
				+ ", " + (edgeFlow.get(x) < 0.00001 ? 0. : edgeFlow.get(x))));

		System.out.println(
				"\nFuente========================================================================================");

		/**
		 * Obtengo la información de la fuente
		 */

		sourceFlow.keySet().stream().forEach(
				x -> System.out.println(x.getNombre() + ", " + (sourceFlow.get(x) < 0.00001 ? 0. : sourceFlow.get(x))));

		System.out.println(
				"\nSumidero========================================================================================");
		/**
		 * Obtengo la informacion del sumidero
		 */
		sinkFlow.keySet().stream().forEach(
				x -> System.out.println(x.getNombre() + ", " + (sinkFlow.get(x) < 0.00001 ? 0. : sinkFlow.get(x))));

		System.out.println(
				"\nImpresion========================================================================================");
		/**
		 * Imprimo la informacion de programación lineal
		 */
		System.out.println(problema.toStringConstraints());

		/**
		 * Flujos tanto de la fuente como del sumidero
		 */
		
		System.out.println("\nExportación de los grafos");

		GraphsFileExporter.saveFile(f, "problema4.gv");
		FlowGraphFileExporter.saveFileFlow(f, "problema41.gv", true);
		FlowGraphFileExporter.saveFileFlowSoutions(f, "problema42.gv", sourceFlow, sinkFlow, edgeFlow, true);

	}

}
