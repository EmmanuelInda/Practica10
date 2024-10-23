public class Terricola extends Personaje implements Destruible {
	public Terricola(String nombre, Escenario e, Posicion posicion) {
		super(nombre, e, posicion);
	}

	@Override
	public String destruir() {
		return "Terricola destruido";
	}
}
