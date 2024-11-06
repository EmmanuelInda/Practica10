import java.io.*;
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

	public Elemento getElemento(Posicion p) {
		return campoDeBatalla[p.getRenglon()][p.getColumna()];
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

	public void agregarDesdeArchivo(String nombreArchivo) {
		File archivo = new File(nombreArchivo);
		if (!archivo.exists()) {
			System.out.println("Archivo de configuración no encontrado. Iniciando un nuevo juego...");
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
			String linea;
			while ((linea = reader.readLine()) != null) {
				String[] tokens = linea.split(" ");
				String tipo = tokens[0];
				int renglon = Integer.parseInt(tokens[1]);
				int columna = Integer.parseInt(tokens[2]);

				Posicion posicion = new Posicion(renglon, columna);
				Elemento elemento = switch (tipo) {
					case "Roca" -> new Roca(this, posicion);
					case "Extraterrestre" -> new Extraterrestre("Alien", this, posicion);
					case "Terricola" -> new Terricola("Humano", this, posicion);
					case "Bomba" -> new Bomba(this, posicion, Integer.parseInt(tokens[3]));
					default -> throw new IllegalArgumentException("Tipo desconocido: " + tipo);
				};
				agregarElemento(elemento);
			}
			System.out.println("Configuración inicial leída del archivo: " + nombreArchivo);
		} catch (IOException e) {
			System.err.println("Error al leer el archivo: " + e.getMessage());
		}
	}
	public void guardarConfiguracion(String nombreArchivo) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					Elemento elemento = campoDeBatalla[i][j];
					if (elemento != null) {
						String tipo = elemento.getClass().getSimpleName();
						String linea = tipo + " " + i + " " + j;
						if (elemento instanceof Bomba) {
							linea += " " + ((Bomba) elemento).getRadio();
						}
						writer.write(linea);
						writer.newLine();
					}
				}
			}
			System.out.println("Configuracion guardada en el archivo: " + nombreArchivo);
		} catch (IOException e) {
			System.err.println("Error al guardar el archivo: " + e.getMessage());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 10; ++i) {
			for (int j = 0; j < 10; ++j) {
				if (campoDeBatalla[i][j] == null)
					sb.append("0 ");
				else
					sb.append(campoDeBatalla[i][j].getClass().getName().charAt(0) + " ");
			}

			sb.append("\n");
		}

		return sb.toString();
	}
}
