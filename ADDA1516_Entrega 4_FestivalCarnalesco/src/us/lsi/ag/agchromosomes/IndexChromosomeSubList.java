package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.ProblemaAG;
import us.lsi.ag.ProblemaAGIndex;
import us.lsi.common.Lists2;

import com.google.common.base.Preconditions;

/**
 * @author Miguel Toro
 * 
 * 
 * <p> Una implementaci�n del tipo Cromosoma&lt;Integer&gt;. Toma como informaci�n la definici�n de un problema que implementa el interfaz ProblemaAGIndex.
 * A partir de esa informaci�n construye una secuencia normal. Asumimos que el n�mero de objetos es <code> n </code> 
 * y el tama�o de la secuencia normal <code> r </code>. </p>
 *  
 * <p> La lista decodificada est� formada por una lista de  tama�o menor o igual a <code> r </code> cuyos valores son 
 * �ndices en el rango <code> 0..n-1 </code>, y cada �ndice <code> i </code> se puede repetir un m�ximo n�mero de veces dado por la multiplicidad m�xima del objeto i
 * definida en el problema. La lista decodificada es, por lo tanto, un subconjunto de la secuencia normal definida en el problema. </p>
 * 
 * <p> La implementaci�n usa un cromosoma binario del tama�o de la secuencia normal. </p>
 * 
 * <p> Es un cromosoma adecuado para codificar problemas de subconjuntos de multiconjuntos</p>
 *
 */
public class IndexChromosomeSubList extends BinaryChromosome implements IndexChromosome {

	public static ProblemaAGIndex<?> problema;
	
	public static List<Integer> normalSequence;
	
	/**
	 * Dimensi�n del cromosoma
	 */
	
	public static int DIMENSION;
	
	public static void iniValues(ProblemaAG problema){
		IndexChromosomeSubList.problema = (ProblemaAGIndex<?>) problema; 
		IndexChromosomeSubList.normalSequence = IndexChromosomeSubList.problema.getNormalSequence();
		IndexChromosomeSubList.DIMENSION = IndexChromosomeSubList.normalSequence.size();
	}
	
	public IndexChromosomeSubList(List<Integer> representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public IndexChromosomeSubList(Integer[] representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return new IndexChromosomeSubList(ls);
	}

	/**
	 * @return Una lista de enteros obtenida filtrando la secuencia normal para incluir 
	 * s�lo los seleccionados por el cromosoma binario 
	 */
	@Override
	public List<Integer> decode() {	
		List<Integer> r = Lists2.<Integer>nCopias(IndexChromosomeSubList.problema.getObjectsNumber(), 0);
		List<Integer> bn = this.getRepresentation();
		Preconditions.checkArgument(normalSequence.size() == bn.size(),normalSequence.size()+","+bn.size());
		for (int i = 0; i < normalSequence.size(); i++) {
			if (bn.get(i) == 1) {
				int k = normalSequence.get(i);
				r.set(k,r.get(k)+1);
			}
		}
		return r;
	}
	
	public static IndexChromosomeSubList getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(IndexChromosomeSubList.DIMENSION);
		return new IndexChromosomeSubList(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private Double ft = null;
	
	private double calculateFt(){
		return IndexChromosomeSubList.problema.fitnessFunction(this);
	}

	@Override
	public ProblemaAGIndex<?> getProblem() {
		return IndexChromosomeSubList.problema;
	}

	@Override
	public Integer getObjectsNumber() {
		return IndexChromosomeSubList.problema.getObjectsNumber();
	}

	@Override
	public Integer getMax(int i) {
		return IndexChromosomeSubList.problema.getMax(i);
	}

	@Override
	public Chromosome asChromosome() {
		return this;
	}

	
	
	
}