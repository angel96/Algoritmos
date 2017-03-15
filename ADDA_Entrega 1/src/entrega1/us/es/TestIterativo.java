package entrega1.us.es;

import java.util.ArrayList;
import java.util.List;

import utiles.ventanas.UtilesVentanas;

public class TestIterativo {

	private static final String TITULO = "Divisores - Iterativo";

	public static void main(String[] args) {

		List<Integer> ls = new ArrayList<>();

		String realStr = UtilesVentanas
				.leerDato(
						TITULO,
						"Introduzca un numero para contar los divisores que tienes y obtener los mismos: ");
		Integer real = new Integer(realStr);
		String mensaje = "El numero introducido tiene: "
				+ Iterativo.obtenerDivisoresIterativo(real, ls) + " divisores"
				+ " y ellos son: " + ls;
		UtilesVentanas.mostrarMensaje(TITULO, mensaje);

	}

}
