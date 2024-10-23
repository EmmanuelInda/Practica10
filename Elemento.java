public abstract class Elemento {
	protected Posicion posicion;
	protected Escenario escenario;

	public Elemento(Escenario c, Posicion p) {
		this.escenario = c;
		this.posicion = p;
	}

	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion p) {
		this.posicion = p;
	}
}
