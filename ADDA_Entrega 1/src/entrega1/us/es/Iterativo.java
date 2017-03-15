package entrega1.us.es;

import java.util.List;

import excepciones.us.es.ExcepcionNumeroNoValido;


public class Iterativo {

	// 4.A partir de las funciones anteriores, diseñar e implementar un algoritmo
	// iterativo que solucione el problema planteado, empleando el lenguaje de
	// programación que le ha indicado su profesor.
	
	public static Integer obtenerDivisoresIterativo(Integer a, List<Integer> res) {
		Integer ac = 0;
		for (int i = 1; i <= a; i++) {
			if(a>0){
				if (a % i == 0){
					ac++;
					res.add(i);
				}
			} else {
				throw new ExcepcionNumeroNoValido("El numero introducido no puede ser negativo");
			}
		}
		return ac;
	}

}
