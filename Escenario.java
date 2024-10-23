import java.util.ArrayList;

public class Escenario {
	private String nombre;
	private Elemento[][] campoDeBatalla;

	public Escenario(String nombre) {
		this.nombre = nombre;
		this.campoDeBatalla = new Elemento[10][10];
	}

	public void agregarElemento(Elemento e) {
		int renglon = e.getPosicion().getRenglon();
		int columna = e.getPosicion().getColumna();

		campoDeBatalla[renglon][columna] = e;
	}

	public void destruirElementos(Posicion p, int radio) {
		ArrayList<Elemento> elementosEnRango = new ArrayList<>();

		for (int i = p.getRenglon() - radio; i <= p.getRenglon() + radio; ++i) {
			for (int j = p.getColumna() - radio; j <= p.getColumna() + radio; ++j) {
				if (i >= 0 && i < 10 && j >= 0 && j < 10 && campoDeBatalla[i][j] != null)
					elementosEnRango.add(campoDeBatalla[i][j]);
			}
		}

		for (Elemento elemento : elementosEnRango) {
			if (elemento instanceof Destruible) {
				System.out.println(((Destruible) elemento).destruir());
				Posicion pos = elemento.getPosicion();
				campoDeBatalla[pos.getRenglon()][pos.getColumna()] = null;
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 10; ++i) {
			for (int j = 0; j < 10; ++j) {
				if (campoDeBatalla[i][j] == null)
					sb.append("0 ");
				else if (campoDeBatalla[i][j] instanceof Elemento)
					sb.append(campoDeBatalla[i][j].getClass().getName().charAt(0) + " ");
			}

			sb.append("\n");
		}

		return sb.toString();
	}
}
