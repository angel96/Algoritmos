package ei3.evento.ag;

import ei3.evento.ProblemaEvento;
import us.lsi.ag.AlgoritmoAG;
import us.lsi.ag.ProblemaAGIndex;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agchromosomes.IndexRangeChromosome;
import us.lsi.ag.agstopping.SolutionsNumber;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.algoritmos.Algoritmos;

public class TestEventoAG {

	public static void main(String[] args){
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 100;
		StoppingConditionFactory.NUM_GENERATIONS = 4000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 0.0;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
		
		ProblemaEvento.create("Comidas.txt");
		ProblemaEvento.presupuestoTotal = 10;
		System.out.println("------");
		System.out.println("Tipos:\n" + ProblemaEvento.tipos);
		System.out.println("------");
		System.out.println("Comidas:\n" + ProblemaEvento.getComidas());
		System.out.println("------");
		System.out.println("Presupuesto total: " + ProblemaEvento.presupuestoTotal);
		System.out.println("------");
		System.out.println("Solución :");
		/**
		 * Resolución
		 */
		ProblemaAGIndex<SolucionEventoAG> p = new ProblemaEventoAG("Comidas.txt");
		
		AlgoritmoAG ap = Algoritmos.createAG(ChromosomeType.IndexRange, p);
		ap.ejecuta();
		
		System.out.println("==========================================");
		System.out.println(IndexRangeChromosome.numeroDeBits);
		System.out.println("==========================================");
		System.out.println(ChromosomeFactory.asIndex(ap.getBestFinal()).decode());
		System.out.println(p.getSolucion(ChromosomeFactory.asIndex(ap.getBestFinal())));
		System.out.println("==========================================");
		System.out.println(SolutionsNumber.numeroDeGeneraciones);
		
	}	

}
