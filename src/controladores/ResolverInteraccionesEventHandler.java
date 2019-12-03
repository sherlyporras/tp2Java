package controladores;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import tp2java.excepciones.ObjetivoAliado;
import tp2java.modelo.tablero.Coordenada;
import tp2java.modelo.tablero.Tablero;
import tp2java.modelo.unidades.Unidad;
import tp2java.modelo.unidades.UnidadMovible;
import vista.ContenedorJuego;
import vista.VistaCelda;

public class ResolverInteraccionesEventHandler implements EventHandler<ActionEvent> {

	private VistaCelda vistaCelda;
	private Tablero tablero;
	private ContenedorJuego contenedorJuego;
	private int x;
	private int y;
	
	public ResolverInteraccionesEventHandler(VistaCelda vistaCelda, Tablero tablero, ContenedorJuego contenedorJuego,
			int x, int y) {
		this.vistaCelda = vistaCelda;
		this.tablero = tablero;
		this.contenedorJuego = contenedorJuego;
		this.x = x;
		this.y = y;
		
	}
		
	@Override
	public void handle(ActionEvent arg0) {
		
		if (vistaCelda.getVistaUnidad() != null && !contenedorJuego.getJugadorEnTurno().realizoAccion()){
			
			Unidad unidad = vistaCelda.getVistaUnidad().getUnidad();
			
			if(contenedorJuego.hayUnidadSeleccionada() && (vistaCelda.getVistaUnidad() != contenedorJuego.getUnidadSeleccionada())) {
				try {
					contenedorJuego.getUnidadSeleccionada().getUnidad().interactuar(unidad);
				} catch (ObjetivoAliado e) {
					nuevaAlerta("Unidad aliada.","Por favor, selecciona una enemiga.","Un gran poder conlleva una gran responsabilidad");
				}
				contenedorJuego.resetSeleccionado();
			} else if (vistaCelda.getVistaUnidad().getUnidad().getJugador() == contenedorJuego.getJugadorEnTurno()){
				contenedorJuego.setUnidadSeleccionada(vistaCelda.getVistaUnidad());
				if(unidad.sePuedeMover()) {
					contenedorJuego.prepararMovimiento(vistaCelda);
				}
			} else {
				nuevaAlerta("No es tu unidad!","Esa unidad no pertenece al jugador en turno.","Un gran poder conlleva una gran responsabilidad");
			}
			
			if(contenedorJuego.getJugadorEnTurno().realizoAccion()) {
				contenedorJuego.setBotonTerminarTurno();
			}
			
		}
		
	}
	
	public void nuevaAlerta(String titulo, String texto, String contenido) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(texto);
		alert.setContentText(contenido);
		alert.showAndWait();
	}
	
}


