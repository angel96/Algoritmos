package ei3.evento.ag;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import ei3.evento.Comida;

public class SolucionEventoAG {
	private Multiset<Comida> comidas;

	private SolucionEventoAG(Multiset<Comida> comida) {
		this.comidas = comida;
	}

	private SolucionEventoAG() {

		this.comidas = HashMultiset.create();
	}

	public static SolucionEventoAG create() {
		return new SolucionEventoAG();
	}

	public static SolucionEventoAG create(Multiset<Comida> comida) {
		return new SolucionEventoAG(comida);
	}
	public Multiset<Comida> getMenu(){
		return comidas;
	}
	
	public SolucionEventoAG add(Comida c, int index){
		Multiset<Comida> res = comidas;
		res.add(c, index);
		return create(res);
	}
	public Integer getVotos(){
		return comidas.stream().mapToInt(x-> x.getVotos()).sum();
	}
	public Integer getPresupuestoTotal(){
		return (int) comidas.stream().mapToDouble(x-> x.getPrecio()).sum();
	}
	public int compareTo(SolucionEventoAG s){
		return comidas.toString().compareTo(s.getMenu().toString());
	}
	public boolean equals(Object args){
		return comidas.equals(args);
	}
	public int hashCode(){
		return comidas.hashCode();
	}
	public String toString(){
		return "Menu: " + getMenu() + ", " +  getVotos() + " votos" + ", " + getPresupuestoTotal() + " presupuesto total";
	}
	
}
