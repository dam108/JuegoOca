package juegooca;
import java.util.*;
public class JuegoOca {
    public static Scanner teclado = new Scanner(System.in);
    public static TableroOca tablero;
    public static int jugadorActivo = 0; // comienza el jugador 1 que seria la posicion 0 del array
    public static int numeroJugadores;
    public static boolean repetirTirada = false;
    public static boolean finPartida = false;
    
    /* MAIN */
    
    public static void main(String[] args) {
        boolean penalizado;
        int casillaJugadorActivo, totalDados;
        
        numeroJugadores = pedirNumeroJugadores();
        tablero = new TableroOca(numeroJugadores);

        do {
            //mostramos en pantalla jugador activo
            mostrarJugadorActivo(jugadorActivo);
            
            //comprobamos que jugador activo no tenga penalizaciones , si las tiene restamos una penalizacion y cambiamos de jugador activo y si no proseguimos
            penalizado = comprobarPenalizacion(jugadorActivo);
            if(penalizado) despenalizarUnTurno();
            else {
                do {
                    //tiramos los dados
                    totalDados = tirarDados();

                    // reseteamos el repetir tirada, desocupamos la casilla en la que estamos y movemos de casilla a jugador activo
                    resetearRepTirada();
                    casillaJugadorActivo = tablero.getJugador().get(jugadorActivo).getCasilla();
                    desocuparCasillaActual(casillaJugadorActivo);
                    moverJugadorActivo(totalDados);//para probar casillas directamente cambiar este parametro totalDados

                    
                    // comprobamos en que casilla cayó
                    casillaJugadorActivo = tablero.getJugador().get(jugadorActivo).getCasilla();
                    mostrarDatosCasillaActual(casillaJugadorActivo);

                    // EjecutarOperacionesCasilla
                    ejecutarOperacionesCasilla(casillaJugadorActivo);
                    
                } while (repetirTirada == true);

             } // fin else  
            
            //cambiamos de jugador
            cambiarJugador();
            
            // comprobamos si jugador.Casilla ==  63 damos por terminado el juego
            finPartida = comprobarFinPartida();
            
            if (finPartida == true){
                for (int i = 0; i < tablero.getJugador().size(); i++) {
                    System.out.println("El "+tablero.getJugador().get(i).getNombre()+" esta en la casilla "+(tablero.getJugador().get(i).getCasilla()+1));
                    if(tablero.getJugador().get(i).getCasilla() == (tablero.getCasilla().size() - 1)) {
                        System.out.println("El "+tablero.getJugador().get(i).getNombre()+" ha Ganado el juego de la Oca.");
                        break;
                    }
                }
            }
            
        } while(!finPartida); // se va ejecutar mientras ningun jugador gane el juego.
    }
    
    /* FIN MAIN */
    
    /* FUNCIONES */
    
    public static int pedirNumeroJugadores(){
        int n = 0;
        do{
            System.out.println("*****************************************");
            System.out.println();
            System.out.println("Introduce el numero de jugadores ( 2 o 4)");
            System.out.println();
            System.out.println("*****************************************");
            n = teclado.nextInt();teclado.nextLine();
        } while ( n != 2 && n != 4);
        return n;
    }
    public static void mostrarJugadorActivo(int n){
        System.out.println("El jugador que se ha seleccionado es el Jugador "+(n+1));
    }
    public static boolean comprobarPenalizacion(int j){
        if (tablero.getJugador().get(j).getTurnosSancionado() > 0) return true;
        else return false;
    }
    public static void despenalizarUnTurno(){
        System.out.println("El jugador esta sancionado");
        System.out.println("Se procede a restar un turno a la sancion a menos que este en el pozo y a cambiar de jugador");
        tablero.getJugador().get(jugadorActivo).setTurnosSancionado( tablero.getJugador().get(jugadorActivo).getTurnosSancionado() - 1 );
    }
    public static int tirarDados(){
        String pause;
        System.out.println("Pulsa intro para tirar los dados");
        pause = teclado.nextLine();
        int dado1 = tablero.tirarDado();
        int dado2 = tablero.tirarDado();
        int total = dado1 + dado2;
        System.out.println("RESULTADO DADOS: "+ total);
        System.out.println();
        return total;
    }
    public static void resetearRepTirada(){
        repetirTirada = false;
        tablero.getJugador().get(jugadorActivo).setrepTirada(repetirTirada);
        
    }
    public static void desocuparCasillaActual(int n){
        tablero.getCasilla().get(n).setOcupada(false);
    }
    public static void moverJugadorActivo(int n){
        int posMax = tablero.getCasilla().size() - 1; // 62
        int posIni = tablero.getJugador().get(jugadorActivo).getCasilla();
        int restaAbs = posMax - posIni;
        restaAbs = Math.abs(restaAbs);
        if(posIni + n > posMax ) {
            int x  = n - restaAbs;
            int y = posMax - x;
            tablero.getJugador().get(jugadorActivo).setCasilla(y);
            System.out.println("El "+tablero.getJugador().get(jugadorActivo).getNombre()+"esta en la Casilla ( nº "+(tablero.getJugador().get(jugadorActivo).getCasilla()+1)+" )" );
        } else {
            for (int i = 0; i < n; i++) {
                // Creamos un bucle para comprobar si pasamos por el pozo y si hay alguien ahi, si es asi lo liberamos del pozo
                if ( tablero.getCasilla().get(30).isOcupada() && tablero.getJugador().get(jugadorActivo).getCasilla() == 30) {
                    for (int j = 0; j < tablero.getJugador().size(); j++) {
                        if ( tablero.getJugador().get(j).getCasilla() == 30 ) tablero.getJugador().get(j).setTurnosSancionado(0);
                    }
                }
                tablero.getJugador().get(jugadorActivo).setCasilla(tablero.getJugador().get(jugadorActivo).getCasilla()+1);
            }
        }
    }
    public static void mostrarDatosCasillaActual(int n){
        System.out.println("*****************************************");
        System.out.println();
        System.out.println("Estamos sobre la casillla: "+(tablero.getJugador().get(jugadorActivo).getCasilla()+1)); // posicion del arreglo -1 y mostrar +1 para que todo cuadre
        System.out.println("El nombre de la casilla actual es: "+tablero.getCasilla().get(n).getDescripcion());
        if (tablero.getCasilla().get(n).getTurnosQueSanciona() == 9999 ) System.out.println("Hemos caido en el pozo y no podemos salir hasta que pase otro jugador por esta casilla");
        else if (tablero.getCasilla().get(n).getTurnosQueSanciona() > 0 ) System.out.println("El "+tablero.getJugador().get(jugadorActivo).getNombre()+" esta sancionado "+tablero.getCasilla().get(n).getTurnosQueSanciona()+" turnos.");
        else System.out.println("El jugador no esta sancionado");
        if (tablero.getCasilla().get(n).getSalto() > 0 ) System.out.println("El "+tablero.getJugador().get(jugadorActivo).getNombre()+"da un salto de "+tablero.getCasilla().get(n).getSalto()+" casillas.");
        if (tablero.getCasilla().get(n).isRepetirTirada() == true) System.out.println("Repetimos tirada");
        else System.out.println("No podemos repetir tirada");
        System.out.println();
        System.out.println("*****************************************");
    }
    public static void ejecutarOperacionesCasilla(int n){
        System.out.println("*****************************************");
        System.out.println();
        System.out.println("Realizando operaciones: ");
        int nuevaN = n + tablero.getCasilla().get(n).getSalto();
        switch (n){
            case 4:
            case 8:
            case 13:
            case 17:
            case 22:
            case 26:
            case 31:
            case 35:
            case 40:
            case 44:
            case 49:
            case 53:
            case 58:
                operacionesGenericas(n, nuevaN);
                System.out.println("El "+tablero.getJugador().get(jugadorActivo).getNombre()+" esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +" Casilla ( nº "+(tablero.getJugador().get(jugadorActivo).getCasilla()+1)+" )" );
                System.out.println("Volvemos a tirar");
                repetirTirada = true;
                break;
            case 5:
            case 11:
                operacionesGenericas(n, nuevaN);
                System.out.println("El "+tablero.getJugador().get(jugadorActivo).getNombre()+" esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +" Casilla ( nº "+(tablero.getJugador().get(jugadorActivo).getCasilla()+1)+" )" );
                System.out.println("Pierde un turno");
                break;
            case 18:
                marcarCasillaOcupada(nuevaN);
                operacionesDeJugadorEnCasilla(nuevaN);
                System.out.println("El "+tablero.getJugador().get(jugadorActivo).getNombre()+" esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +" Casilla ( nº "+(tablero.getJugador().get(jugadorActivo).getCasilla()+1)+" )" );
                System.out.println("Pierde un turno");
                break;
            case 30:
                marcarCasillaOcupada(nuevaN);
                operacionesDeJugadorEnCasilla(nuevaN);
                System.out.println("El "+tablero.getJugador().get(jugadorActivo).getNombre()+" esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +" Casilla ( nº "+(tablero.getJugador().get(jugadorActivo).getCasilla()+1)+" )" );
                System.out.println("Permaneces en el pozo hasta que un jugador pase por la casilla del pozo.");
                break;
            case 41:
                operacionesGenericas(n, nuevaN);
                System.out.println("El "+tablero.getJugador().get(jugadorActivo).getNombre()+" esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +" Casilla ( nº "+(tablero.getJugador().get(jugadorActivo).getCasilla()+1)+" )" );
                break;            
            case 55:
                marcarCasillaOcupada(nuevaN);
                operacionesDeJugadorEnCasilla(nuevaN);
                System.out.println("El "+tablero.getJugador().get(jugadorActivo).getNombre()+" esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +" Casilla ( nº "+(tablero.getJugador().get(jugadorActivo).getCasilla()+1)+" )" );
                System.out.println("Pierde dos turnos");
                break;                
            case 25:
            case 52:
                operacionesGenericas(n, nuevaN);
                System.out.println("El "+tablero.getJugador().get(jugadorActivo).getNombre()+" esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +" Casilla ( nº "+(tablero.getJugador().get(jugadorActivo).getCasilla()+1)+" )" );
                break;                
            case 57:
                operacionesGenericas(n, nuevaN);
                System.out.println("El "+tablero.getJugador().get(jugadorActivo).getNombre()+" esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +" Casilla ( nº "+(tablero.getJugador().get(jugadorActivo).getCasilla()+1)+" )" );
                System.out.println("Retrocedes hasta la casilla de salida.");
                break;                 
            case 62:
                finPartida = true;
                System.out.println("El "+tablero.getJugador().get(jugadorActivo).getNombre()+" esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +" Casilla ( nº "+(tablero.getJugador().get(jugadorActivo).getCasilla()+1)+" )" );
                break;
            default: break;
        }
        System.out.println();
        System.out.println("*****************************************");
    }
    public static void operacionesGenericas(int n1 , int n2){
        desocuparCasillaActual(n1);
        moverJugadorActivo(n2);
        tablero.getCasilla().get(n1).setNumCasilla(n2);
        tablero.getJugador().get(jugadorActivo).setCasilla(n2);
        marcarCasillaOcupada(n2);
        operacionesDeJugadorEnCasilla(n2);  
    }
    public static void operacionesDeJugadorEnCasilla(int n){
        //igualamos las casillas
        igualarCasillas(n);
        //igualamos los turnos que sanciona
        igualarTurnosSancion(n);
        // marcamos casilla actual como ocupada
        marcarCasillaOcupada(n);
        // igualar repetir tirada 
        igualarRepetirTirada(n);
    }
    public static void igualarRepetirTirada(int n){
       tablero.getJugador().get(jugadorActivo).setrepTirada(tablero.getCasilla().get(n).isRepetirTirada());
    }
    public static void marcarCasillaOcupada(int n){
        tablero.getCasilla().get(n).setOcupada(true);
    }
    public static void igualarTurnosSancion(int n){
        tablero.getJugador().get(jugadorActivo).setTurnosSancionado( tablero.getCasilla().get(n).getTurnosQueSanciona() );
    }
    public static void igualarCasillas(int n){
        tablero.getJugador().get(jugadorActivo).setCasilla(tablero.getCasilla().get(n).getNumCasilla());
    }
    public static void cambiarJugador(){
        System.out.println("*****************************************");
        System.out.println();
        System.out.println("Cambiamos de jugador");
        if (jugadorActivo < numeroJugadores - 1) jugadorActivo++;
        else jugadorActivo = 0;
        System.out.println();
        System.out.println("*****************************************");
    }
    public static boolean comprobarFinPartida(){
        if(tablero.getJugador().get(jugadorActivo).getCasilla() == tablero.getCasilla().size()-1) return true;
        else return false;
    }
    
    /* FIN FUNCIONES */
    
    
} // FIN CLASS