package utiles.ventanas;

import javax.swing.JOptionPane;

public class UtilesVentanas {

	public static String leerDato(String titulo, String mensaje) {
		return JOptionPane.showInputDialog(null, mensaje, titulo,
				JOptionPane.PLAIN_MESSAGE);
	}

	public static void mostrarMensaje(String titulo, String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, titulo,
				JOptionPane.INFORMATION_MESSAGE);
	}

}
