package entrega1.us.es;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestRecursivoNoFinal {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Introduzca un valor: ");
		Integer b = sc.nextInt();
		List<Integer> l = new ArrayList<Integer>();
		System.out.println("Los divisores de " + b + " son "
				+ Recursivo.obtenerDivisorNoFinal(b, 1, l) + " sus divisores son :"
						+ " " + l);
		sc.close();		
	}

}
