package entrega1.us.es;

import java.util.List;

import excepciones.us.es.ExcepcionNumeroNoValido;

public class Recursivo {
	
	//	2.Implemente una función recursiva no final que solucione el problema
	//	planteado, empleando el lenguaje de programación que le ha indicado su
	//	profesor.

	public static Integer obtenerDivisorNoFinal(Integer a, Integer div, List<Integer> ls) {
		Integer r = 0;
		if(a>0){
			if (a.equals(div)) {
				r = 1;
				ls.add(div);
			} else if (a % div == 0) {
				ls.add(div);
				r = 1 + obtenerDivisorNoFinal(a, div + 1,ls);
			} else {
				r = obtenerDivisorNoFinal(a, div + 1,ls);
			}
		} else {
			throw new ExcepcionNumeroNoValido("El numero no puede ser negativo");
		}
		
		return r;
	}
	//	3.Implemente una función recursiva final a partir de la definición anterior,
	//	empleando el lenguaje de programación que le ha indicado su profesor.
	public static Integer obtenerDivisorFinal(Integer a, Integer div, Integer ac, List<Integer> l) {
		Integer r = 0;
		if(a>0){
			if (a.equals(div)) {
				r = 1;
				l.add(ac);
			} else if (a % div == 0) {
				l.add(div);
				r = 1 + obtenerDivisorFinal(a, div + 1, ac+1, l);
			} else {
				r = obtenerDivisorFinal(a, div + 1, ac+1, l);
			}
		} else {
			throw new ExcepcionNumeroNoValido("El numero no puede ser negativo");
		}
		
		return r;
	}
}
