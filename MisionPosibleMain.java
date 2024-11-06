import java.util.Scanner;

public class MisionPosibleMain {
	public static void main(String[] args) {
		Escenario e = new Escenario("Nostromo");

		/* Cargar configuración inicial desde archivo */
		e.agregarDesdeArchivo("config.txt");

		/* Mostrar estado actual */
		System.out.println(e);

		/* Ejemplo de detonación de bomba (basado en la entrada del usuario) */
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese la fila y columna de la bomba a detonar:");
		int row = scanner.nextInt();
		int col = scanner.nextInt();

		Elemento elemento = e.getElemento(new Posicion(row, col));
		if (elemento instanceof Bomba)
			((Bomba) elemento).explotar();
		else
			System.out.println("No hay una bomba en esa posición.");

		/* Mostrar estado actualizado */
		System.out.println(e);

		/* Guardar la configuración actualizada */
		e.guardarConfiguracion("config.txt");
	}
}
