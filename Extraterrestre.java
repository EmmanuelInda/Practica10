public class Extraterrestre extends Personaje implements Destruible {
	public Extraterrestre(String nombre, Escenario e, Posicion posicion) {
		super(nombre, e, posicion);
	}

	@Override
	public String destruir() {
		return "Alien destruido";
	}
}
