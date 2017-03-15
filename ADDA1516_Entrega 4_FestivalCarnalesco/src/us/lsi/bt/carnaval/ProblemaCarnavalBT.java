package us.lsi.bt.carnaval;

import java.util.List;

import us.lsi.bt.ProblemaBT;
import us.lsi.bt.carnaval.EstadoCarnaval.Alternativa;
import us.lsi.pl.carnaval.Agrupacion;

public class ProblemaCarnavalBT implements ProblemaBT<List<Agrupacion>,Alternativa>{

	public static ProblemaBT<List<Agrupacion>, Alternativa> create(){
		return new ProblemaCarnavalBT();
	}
	@Override
	public Tipo getTipo() {
		return Tipo.Max;
	}
	
	@Override
	public EstadoCarnaval getEstadoInicial() {
		return new EstadoCarnaval();
	}
}
