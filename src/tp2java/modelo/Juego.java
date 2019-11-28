package tp2java.modelo;

import java.util.Random;
import tp2java.modelo.tablero.Tablero;

public class Juego {

	private Jugador jugadorActual;
	private Jugador jugadorSiguiente;
	private final Tablero tablero;
	
	public Juego(Jugador primerJugador, Jugador segundoJugador) {
		this.tablero = new Tablero();
		
		if (new Random().nextBoolean()) {
			this.jugadorActual = primerJugador;
			this.jugadorSiguiente = segundoJugador;
		}
		else {
			this.jugadorActual = segundoJugador;
			this.jugadorSiguiente = primerJugador;
		}
	}
	
	public void siguienteTurno() {
		Jugador aux = this.jugadorActual;
		this.jugadorActual = this.jugadorSiguiente;
		this.jugadorSiguiente = aux;
	}
	
	public Tablero getTablero(){
		return this.tablero;
	}
	
	
}
