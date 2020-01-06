package juegooca;
import java.util.*;

public class TableroOca {
    private final int CAS_FINAL = 63;
    private ArrayList <Casillas> casilla;
    private ArrayList <Jugadores> jugador;
    
    /* CONSTRUCTOR */
    TableroOca(int nJugadores){
        jugador = new ArrayList <>();
        crearJugadores(nJugadores);
        casilla = new ArrayList <>();
        crearCasillas();

    }
    /* FIN CONTRUSTOR */
    
    /* METODOS */

    private int calcularSaltoCasillaDados(int n){
        int var1 = n /10; 
        int var2 = var1 * 10 ; 
        int res1 = n - var2; 
        return (var1 + res1);
    }
    
    private void crearJugadores(int nJ){
        // creamos jugadores
        for (int i = 0; i < nJ; i++) {
            jugador.add(i, new Jugadores(i+1));
        }
    }
    
    public int tirarDado(){
        int resultado = (int)(Math.random()*6)+1;
        return resultado;
    }
    
    private void crearCasillas(){
        // creamos casillas
        for (int i = 0; i < CAS_FINAL; i++) {
            int n = i+1;
            casilla.add(new Casillas(n));
        }
        casilla.set(0, new Casillas(0+1, "Salida", 0, false));
        casilla.set(4, new Casillas(4+1, "Oca", 0, 4, true));
        casilla.set(8, new Casillas(8+1, "Oca", 0, 5, true));
        casilla.set(13, new Casillas(13+1, "Oca", 0, 4, true));
        casilla.set(17, new Casillas(17+1, "Oca", 0, 5, true));
        casilla.set(22, new Casillas(22+1, "Oca", 0, 4, true));
        casilla.set(26, new Casillas(26+1, "Oca", 0, 5, true));
        casilla.set(31, new Casillas(31+1, "Oca", 0, 4, true));
        casilla.set(35, new Casillas(35+1, "Oca", 0, 5, true));
        casilla.set(40, new Casillas(40+1, "Oca", 0, 4, true));
        casilla.set(44, new Casillas(44+1, "Oca", 0, 5, true));
        casilla.set(49, new Casillas(49+1, "Oca", 0, 4, true));
        casilla.set(53, new Casillas(53+1, "Oca", 0, 5, true));
        casilla.set(58, new Casillas(58+1, "Oca", 0, 0, true));
        casilla.set (5, new Casillas(5+1, "Puente", 1, 13, false));
        casilla.set (11, new Casillas(11+1, "Puente", 1, 7, false));
        casilla.set (18, new Casillas(18+1, "Posada", 1, false));
        casilla.set (30, new Casillas(30+1, "Pozo", 9999, false));
        casilla.set (41, new Casillas(41+1, "Laberinto",0, -12, false));
        casilla.set (55, new Casillas(55+1, "Carcel", 2, false));
        casilla.set (25, new Casillas(25+1, "Dados", 0, calcularSaltoCasillaDados(25+1) , false));
        casilla.set (52, new Casillas(52+1, "Dados", 0, calcularSaltoCasillaDados(52+1) , false));
        casilla.set (57, new Casillas(57+1, "Calavera", 0, -57 , false));
        // hacer casilla final mas tarde
        casilla.set ( 62, new Casillas(62+1, "Casilla Final", 0, /*calcuarRebote(),*/ false));
    }
    
    
    /* FIN METODOS */
    /* GETTERS Y SETTERS */
    
    public ArrayList<Casillas> getCasilla() {
        return casilla;
    }

    public void setCasilla(ArrayList<Casillas> casilla) {
        this.casilla = casilla;
    }

    public ArrayList<Jugadores> getJugador() {
        return jugador;
    }

    public void setJugador(ArrayList<Jugadores> jugador) {
        this.jugador = jugador;
    }
    
    /* FIN GETTERS Y SETTERS */
    
}
