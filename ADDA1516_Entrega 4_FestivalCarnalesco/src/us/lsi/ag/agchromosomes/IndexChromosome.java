package us.lsi.ag.agchromosomes;

import java.util.List;

import us.lsi.ag.ProblemaAGIndex;

/**
 * @author Miguel Toro
 * 
 * <p> Un cromosoma adecuado para resolver problemas cuya soluci�n es un Multiset o una lista, posiblemente con elementoss repetidos,
 * formados con elementos de un conjunto dado </p>
 *
 */
public interface IndexChromosome extends IChromosome<List<Integer>> {
	/**
	 * @return El problema a resolver
	 */
	ProblemaAGIndex<?> getProblem();
	/**
	 * @return N�mero de objetos distintos definidos en el problema
	 */
	Integer getObjectsNumber();
	/**
	 * @param i El �ndice de un objeto en el rango <code> 0..getObjectsNumber()-1 </code>
	 * @return El m�ximo n�mero de repeticiones del objeto de �ndice <code> i </code>
	 */
	Integer getMax(int i);
}
