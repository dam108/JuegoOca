package juegooca;
import java.util.*;

public class Jugadores {
    private String nombre;
    private int casilla;
    private int turnosSancionado;
    private boolean repTirada;

    /* CONSTRUCTOR */
    Jugadores( int nJugador){
        nombre = "Jugador "+nJugador;
        casilla = 0;
        turnosSancionado = 0;
        repTirada = false;
        
    }
    /* FIN CONSTRUCTOR */
    
    /* GETTERS Y SETTERS */

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getCasilla() {
        return casilla;
    }
    public void setCasilla(int casilla) {
        this.casilla = casilla;
    }
    public int getTurnosSancionado() {
        return turnosSancionado;
    }
    public void setTurnosSancionado(int turnosSancionado) {
        this.turnosSancionado = turnosSancionado;
    }
    public boolean isrepTirada() {
        return repTirada;
    }
    public void setrepTirada(boolean repeTirada) {
        this.repTirada = repeTirada;
    }
        
    /* FIN GETTERS Y SETTERS */

} // FIN CLASS
