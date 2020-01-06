package juegooca;
import java.util.*;

public class Casillas {
    private int numCasilla;
    private String Descripcion;
    private int turnosQueSanciona;
    private boolean ocupada;
    private int salto;
    private boolean repetirTirada;

    /* CONTRUCTOR */
    Casillas(int nC, String desC, int tqS, int saltC, boolean rT){
        numCasilla = nC;
        Descripcion = desC;
        turnosQueSanciona = tqS;
        ocupada = false;
        salto = saltC;
        repetirTirada = rT;
    }

    Casillas(int nC, String desC, int tqS, boolean rT){
        numCasilla = nC;
        Descripcion = desC;
        turnosQueSanciona = tqS;
        ocupada = false;
        salto = 0;
        repetirTirada = rT;
    }
    Casillas(int nC){
        numCasilla = nC;
        Descripcion = "Casilla normal";
        turnosQueSanciona = 0;
        ocupada = false;
        salto = 0;
        repetirTirada = false;
    }
    /* FIN CONSTRUCTOR */
    
    /* GETTERS Y SETTERS */
    
    public int getNumCasilla() {
        return numCasilla;
    }
    public void setNumCasilla(int numCasilla) {
        this.numCasilla = numCasilla;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    public int getTurnosQueSanciona() {
        return turnosQueSanciona;
    }
    public void setTurnosQueSanciona(int turnosQueSanciona) {
        this.turnosQueSanciona = turnosQueSanciona;
    }
    public boolean isOcupada() {
        return ocupada;
    }
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }
    public int getSalto() {
        return salto;
    }
    public void setSalto(int salto) {
        this.salto = salto;
    }
    public boolean isRepetirTirada() {
        return repetirTirada;
    }
    public void setRepetirTirada(boolean repetirTirada) {
        this.repetirTirada = repetirTirada;
    }
    
    /* FIN GETTERS Y SETTERS */
    
    
} // FIN CLASS
