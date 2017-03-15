package entrega1.us.es;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestRecursivoFinal {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduzca un valor: ");
		Integer b = sc.nextInt();
		List<Integer> l = new ArrayList<>();
		System.out.println("Los divisores de " + b + " son "
				+ Recursivo.obtenerDivisorFinal(b, 1, 1, l)
				+ " sus divisores son : " + l);
		sc.close();
	}

}
