package vista;

import controladores.ColocarUnidadTableroEventHandler;
import controladores.MoverUnidadEventHandler;
import controladores.ResolverInteraccionesEventHandler;
import controladores.SiguienteJugadorCompraEventHandler;
import controladores.TerminarColocarEventHander;
import controladores.TerminarCompraEventHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tp2java.modelo.Juego;
import tp2java.modelo.Jugador;
import tp2java.modelo.tablero.Direccion;
import tp2java.modelo.tablero.Tablero;
import tp2java.modelo.unidades.Unidad;
import tp2java.modelo.unidades.UnidadMovible;

public class ContenedorJuego extends HBox implements ContenedorConTablero{
	private static final String RUTA_FONDO="file:src/vista/imagenes/fondoTienda.png";
	private static final String RUTA_SIGUIENTE="file:src/vista/imagenes/siguiente.png";
	private static final String RUTA_TERMINAR="file:src/vista/imagenes/terminar.png";

	public final Stage stage;	
	
	private VBox vBox = new VBox(20);
	private HBox hBox = new HBox(0);
	
	private VistaTablero vTablero;
	private VistaUnidad seleccionado;
	
	ContenedorMovimientos contMov;
	
	private Juego juego;
	
	private Jugador jugadorActual;
	
	public ContenedorJuego(Stage stage, Jugador jugador1, Jugador jugador2,Juego juego) {
		this.stage = stage;
		
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
	    this.setPadding(new Insets(25));
	    this.hBox.setAlignment(Pos.CENTER_RIGHT);
	    this.vTablero = new VistaTablero(juego.getTablero(),this);
	    this.jugadorActual = juego.getjugadorEnTurno();
	    
	    this.seleccionado = null;
        
	    vTablero.dibujarUnidades(juego.getTablero());
	    
	    Image fondoBienvenida= new Image(RUTA_FONDO,1100,650,false,true);
        BackgroundImage mostrarFondoBienvenida=new BackgroundImage(fondoBienvenida, BackgroundRepeat.ROUND,BackgroundRepeat.ROUND,BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
                
        this.vBox.getChildren().add(vTablero);
        this.getChildren().add(vBox);   
        
        this.setBackground(new Background(mostrarFondoBienvenida));
        
        this.juego=juego;
        ContenedorMovimientos botonesMov=new ContenedorMovimientos(this);
        this.contMov=botonesMov;
        this.getChildren().add(botonesMov);
        
	}
	
	@Override
	public boolean hayUnidadSeleccionada() {
		return this.seleccionado != null;
	}
	
	@Override
	public void setUnidadSeleccionada(VistaUnidad unidad) {
		this.seleccionado = unidad;
		this.seleccionado.setStyle("-fx-border-size:1px;"+
				"-fx-border-color:#888;"+
				"-fx-border-style:solid;");
	}
	
	@Override
	public VistaUnidad getUnidadSeleccionada() {
		return this.seleccionado;
	}
	
	@Override
	public void resetSeleccionado() {
		this.seleccionado.setStyle("-fx-opacity:0;");
		this.seleccionado.setOnAction(null);
		this.seleccionado = null;
	}
	public void setBotonTerminarTurno(Jugador jugador1, Jugador jugador2, Juego juego) {
	//	this.setBoton(RUTA_TERMINAR, new TerminarTurnoEventHander(this.stage, jugador2, jugador1,juego));
	}
	
	private void setBoton(String ruta, EventHandler<ActionEvent> event) {
		HBox hb = new HBox();
		
		Button boton=new Button("");
		boton.setOnAction(event);
		
		boton.setBackground(Background.EMPTY);
		Image image=new Image(ruta,150,30,true,true);
		boton.setGraphic(new ImageView(image));
		
		hb.getChildren().addAll(boton);
		hb.setAlignment(Pos.BOTTOM_CENTER);
		
		this.getChildren().add(hb);
	}
	

	@Override
	public void definirAccionDeCelda(VistaCelda vistaCelda, Tablero tablero, int x, int y) {

		vistaCelda.setOnAction(new ResolverInteraccionesEventHandler(vistaCelda,tablero, this, x,y));
		
	}

	public void prepararMovimiento(VistaCelda vistaCelda,Tablero tablero) {
		
		// Mostrar flechas en las direcciones, que sean botones para realizar los movimientos.
		// (cada una mueva en una Direccion diferente)


		Direccion movSeleccionado=this.contMov.movimientoSeleccionado();
		
		Unidad unidad=vistaCelda.getVistaUnidad().getUnidad();
		
		
		
		System.out.println(vistaCelda.getX()+","+vistaCelda.getY());
		
//		if(unidad.getJugador().getNombre()==this.jugadorActual.getNombre()) {//corresponde al turno
			if((unidad.perteneceASuSector()&& tablero.sePuedeMoverUnidad(movSeleccionado.calcularCoordenada(unidad.getUbicacion())))) {
				((UnidadMovible)unidad).mover(this.contMov.movimientoSeleccionado());

				vistaCelda.limpiarCelda();
				this.getVistaTablero().dibujarUnidades(tablero);
				

				this.resetSeleccionado();
				
				
			}
//		}
		
		this.juego.siguienteTurno();
		
		
	}
	
	@Override
	public VistaTablero getVistaTablero() {
		return vTablero;
	}
	
}