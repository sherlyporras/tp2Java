package tp2java.modelo.unidades;

import java.util.ArrayList;

import tp2java.excepciones.ObjetivoAliado;
import tp2java.modelo.tablero.Coordenada;
import tp2java.modelo.unidades.Atacante;
import tp2java.modelo.tablero.Tablero;
import tp2java.modelo.Jugador;

public class Catapulta extends Unidad implements Atacante {
	
	private Ataque ataque;
	
	public Catapulta(Jugador jugador, Coordenada ubicacion, Tablero tablero) {
		
		super(50,5,jugador,ubicacion,tablero);
		this.ataque = new Ataque(this,20);
		
	}
	
	public Catapulta(Jugador jugador) {
		super(50,5,jugador);
		this.ataque = new Ataque(this,20);
	}
	
	public Catapulta() {
		super(50,5);
		this.ataque = new Ataque(this,20);
	}
	
	
	@Override 
	public void atacar(Unidad unidad) throws ObjetivoAliado{ // Parámetro es la unidad a atacar.	
				
		if(esEnemiga(unidad)) {
			if(distanciaA(unidad) > 6) {
				ArrayList<Unidad> unidadesAfectadas = recorrerUnidadesAfectadas(unidad);
				this.getJugador().realizarAccion();
				for(Unidad unidadAfectada : unidadesAfectadas) {
					ataque.sinDistincionDeEquipoA(unidadAfectada);
				}
			}
		}
		else throw (new ObjetivoAliado());
		
	}
	
	public ArrayList<Unidad> recorrerUnidadesAfectadas(Unidad unidad){
		
		ArrayList<Unidad> unidadesAfectadas = new ArrayList<Unidad>();
				
		agregarEnCadena(unidad, unidadesAfectadas);
		
		return unidadesAfectadas;	
		
	}
	
	private void agregarEnCadena(Unidad unidad, ArrayList<Unidad> unidadesAfectadas) {
		
		if(!unidadesAfectadas.contains(unidad)) {
			unidadesAfectadas.add(unidad);
			ArrayList<Unidad> unidadesAdyacentes = unidad.getTablero().unidadesCercanas(unidad, 1);
			for(Unidad unidadAdyacente : unidadesAdyacentes) {
				agregarEnCadena(unidadAdyacente, unidadesAfectadas);
			}
		}	
	}
	
}
