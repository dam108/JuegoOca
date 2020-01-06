package juegooca;
import java.util.*;
public class JuegoOca {
    public static Scanner teclado = new Scanner(System.in);
    // creamos tablero y creamos jugadores 
    public static TableroOca tablero;
    public static int jugadorActivo = 0; // comienza el jugador 1 que seria la posicion 0 del array
    public static int numeroJugadores;
    public static boolean repetirTirada = false;
    public static boolean finPartida = false;
    
    public static void main(String[] args) {
        boolean penalizado;
        String pause;
        int casillaJugadorActivo, totalDados;
        
        // Preguntar cuantos jugadores van a jugar 
        numeroJugadores = pedirNumeroJugadores();
        
        // creamos tablero y creamos jugadores 
        tablero = new TableroOca(numeroJugadores);

       //debug numero de jugadores 
        System.out.println("Comienza el juego");
        System.out.println("numero jugadores "+numeroJugadores);
        
        //creamos el bucle principal
        do {

            //mostramos en pantalla jugador activo
            mostrarJugadorActivo(jugadorActivo);
            
            //comprobamos que jugador activo no tenga penalizaciones , si las tiene restamos una penalizacion y cambiamos de jugador activo y si no proseguimos
            penalizado = comprobarPenalizacion(jugadorActivo);
            
            if(penalizado) despenalizarUnTurno();
            else {
                do {
                    //tiramos tiramos los dados
                    System.out.println("pulsa intro para tirar los dados");
                    pause = teclado.nextLine();
                    totalDados = tirarDados();
                    
                        
                        // desocupamos la casilla en la que estamos y movemos de casilla a jugador activo
                        casillaJugadorActivo = tablero.getJugador().get(jugadorActivo).getCasilla();
                        desocuparCasillaActual(casillaJugadorActivo);
                        moverJugadorActivo(totalDados);//para probar casillas directamente cambiar este parametro totalDados

                        // comprobamos en que casilla callo
                        casillaJugadorActivo = tablero.getJugador().get(jugadorActivo).getCasilla();
                        mostrarDatosCasillaActual(casillaJugadorActivo);

                        // EjecutarOperacionesCasilla
                        ejecutarOperacionesCasilla(casillaJugadorActivo);
                    
                } while (repetirTirada == true);
                
                //cambiamos de jugador
                    if (jugadorActivo < numeroJugadores - 1) jugadorActivo++;
                    else jugadorActivo = 0;
                    
                    System.out.println();
                    System.out.println();
                    System.out.println("*****************************************");

                 
             } // fin else    
            
            // comprobamos si jugador.getCasilla ==  63 damos por terminado el juego
            
        } while(!finPartida); // se va ejecutar mientras ningun jugador gane el juego.
        
    } // fin main
    
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
    
    public static void despenalizarUnTurno(){
        System.out.println("El jugador esta sancionado");
                System.out.println("Se procede a restar un turno a la sancion a menos que este en el pozo y a cambiar de jugador");
                tablero.getJugador().get(jugadorActivo).setTurnosSancionado( tablero.getJugador().get(jugadorActivo).getTurnosSancionado() - 1 );
    }
    
    public static int tirarDados(){
        int dado1 = tablero.tirarDado();
        int dado2 = tablero.tirarDado();
        int total = dado1 + dado2;
        System.out.println("RESULTADO DADO1 DEBUG "+ dado1);
        System.out.println("RESULTADO DADO1 DEBUG "+ dado2);
        System.out.println("RESULTADO DADOS DEBUG "+ total);
        return total;
    }

    
     public static void moverJugadorActivo(int n){
         for (int i = 0; i < n; i++) {
                    // Creamos un bucle para comprobar si pasamos por el pozo y si hay alguien ahi, si es asi lo liberamos del pozo
                    if( tablero.getJugador().get(jugadorActivo).getCasilla() == 30 && tablero.getCasilla().get(30).isOcupada() == true ) {
                            for (int j = 0; j < numeroJugadores; j++) {
                                if( tablero.getJugador().get(i).getCasilla() == 30) tablero.getJugador().get(i).setTurnosSancionado(0);
                            }
                        }
                    tablero.getJugador().get(jugadorActivo).setCasilla(tablero.getJugador().get(jugadorActivo).getCasilla()+1);
                    }
                    
     }
    
    public static void mostrarDatosCasillaActual(int n){
        // mostramos en que casilla sa acaba de caer
        System.out.println("La casilla actual es la numero: "+tablero.getCasilla().get(n).getNumCasilla());
        System.out.println("El nombre de la casilla actual es: "+tablero.getCasilla().get(n).getDescripcion());
        if (tablero.getCasilla().get(n).getTurnosQueSanciona() == 9999) System.out.println("Hemos caido en el pozo y no podemos salir hasta que pase otro jugador por esta casilla");
        else System.out.println("Sanciona al Jugador "+tablero.getCasilla().get(n).getTurnosQueSanciona()+" turnos.");
        if (tablero.getCasilla().get(n).isOcupada() == true ) System.out.println("La casilla esta ocupada por otro Jugador");
        else System.out.println("La casilla no esta ocupada por ningun otro jugador");
        System.out.println("La casilla acutal nos hace avanzar con un salto de: "+tablero.getCasilla().get(n).getSalto()+" casillas.");
        if (tablero.getCasilla().get(n).isRepetirTirada() == true) System.out.println("Como la casilla es especial volvemos a tirar");
        else System.out.println("No podemos repetir tirada");
    }
    
    public static boolean comprobarOca(int n){
        int casillaOca = tablero.getCasilla().get(n).getNumCasilla();
        if (casillaOca == 4 || casillaOca == 8 || casillaOca == 13 
            || casillaOca == 17 || casillaOca == 22 || casillaOca == 26 
            || casillaOca == 31 || casillaOca == 35 || casillaOca == 40
            || casillaOca == 44 || casillaOca == 49 || casillaOca == 53 
            || casillaOca == 58) return true;
        else return false;
    }
    
    public static void ejecutarOperacionesCasilla(int n){
        System.out.println("La casilla actual es la numero: "+tablero.getCasilla().get(n).getNumCasilla());
        System.out.println("La casilla acutal es" + tablero.getCasilla().get(n).getDescripcion() );
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
                desocuparCasillaActual(n);
                moverJugadorActivo(nuevaN);
                tablero.getCasilla().get(n).setNumCasilla(nuevaN);
                tablero.getJugador().get(jugadorActivo).setCasilla(nuevaN);
                marcarCasillaOcupada(nuevaN);
                operacionesDeJugadorEnCasilla(nuevaN);
                System.out.println("El Jugador esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +"( Casilla nº "+tablero.getJugador().get(jugadorActivo).getCasilla()+" )" );
                System.out.println("Volvemos a tirar");
                repetirTirada = true;
                break;
            case 5:
            case 11:
                desocuparCasillaActual(n);
                moverJugadorActivo(nuevaN);
                tablero.getCasilla().get(n).setNumCasilla(nuevaN);
                tablero.getJugador().get(jugadorActivo).setCasilla(nuevaN);
                marcarCasillaOcupada(nuevaN);
                operacionesDeJugadorEnCasilla(nuevaN);
                System.out.println("El Jugador esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +"( Casilla nº "+tablero.getJugador().get(jugadorActivo).getCasilla()+" )" );
                System.out.println("Pierde un turno");
                System.out.println("debug sanciones ");
                System.out.println(tablero.getJugador().get(jugadorActivo).getTurnosSancionado() );
                repetirTirada = false;
                break;
            case 18:
                marcarCasillaOcupada(nuevaN);
                operacionesDeJugadorEnCasilla(nuevaN);
                System.out.println("El Jugador esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +"( Casilla nº "+tablero.getJugador().get(jugadorActivo).getCasilla()+" )" );
                System.out.println("Pierde un turno");
                System.out.println("debug sanciones ");
                System.out.println(tablero.getJugador().get(jugadorActivo).getTurnosSancionado() );
                repetirTirada = false;
                break;
            case 30:
                marcarCasillaOcupada(nuevaN);
                operacionesDeJugadorEnCasilla(nuevaN);
                System.out.println("El Jugador esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +"( Casilla nº "+tablero.getJugador().get(jugadorActivo).getCasilla()+" )" );
                System.out.println("Pierde un turno");
                System.out.println("debug sanciones ");
                System.out.println(tablero.getJugador().get(jugadorActivo).getTurnosSancionado() );
                repetirTirada = false;
                break;
            case 41:
                desocuparCasillaActual(n);
                moverJugadorActivo(nuevaN);
                tablero.getCasilla().get(n).setNumCasilla(nuevaN);
                tablero.getJugador().get(jugadorActivo).setCasilla(nuevaN);
                marcarCasillaOcupada(nuevaN);
                operacionesDeJugadorEnCasilla(nuevaN);
                System.out.println("El Jugador esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +"( Casilla nº "+tablero.getJugador().get(jugadorActivo).getCasilla()+" )" );
                repetirTirada = false;
                break;
            case 55:
                marcarCasillaOcupada(nuevaN);
                operacionesDeJugadorEnCasilla(nuevaN);
                System.out.println("El Jugador esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +"( Casilla nº "+tablero.getJugador().get(jugadorActivo).getCasilla()+" )" );
                System.out.println("Pierde un turno");
                System.out.println("debug sanciones ");
                System.out.println(tablero.getJugador().get(jugadorActivo).getTurnosSancionado() );
                repetirTirada = false;
                break;
            case 25:
            case 52:
                desocuparCasillaActual(n);
                moverJugadorActivo(nuevaN);
                tablero.getCasilla().get(n).setNumCasilla(nuevaN);
                tablero.getJugador().get(jugadorActivo).setCasilla(nuevaN);
                marcarCasillaOcupada(nuevaN);
                operacionesDeJugadorEnCasilla(nuevaN);
                System.out.println("El Jugador esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +"( Casilla nº "+tablero.getJugador().get(jugadorActivo).getCasilla()+" )" );
                repetirTirada = false;
                break;
            case 57:
                desocuparCasillaActual(n);
                moverJugadorActivo(nuevaN);
                tablero.getCasilla().get(n).setNumCasilla(nuevaN);
                tablero.getJugador().get(jugadorActivo).setCasilla(nuevaN);
                marcarCasillaOcupada(nuevaN);
                operacionesDeJugadorEnCasilla(nuevaN);
                System.out.println("El Jugador esta ahora en la Siguiente "+ tablero.getCasilla().get(nuevaN).getDescripcion() +"( Casilla nº "+tablero.getJugador().get(jugadorActivo).getCasilla()+" )" );
                repetirTirada = false;
                break;
            case 62: 
                finPartida = true;
                System.out.println("El "+tablero.getJugador().get(jugadorActivo).getNombre()+" ha ganado el juego de la Oca.");
                repetirTirada = false;
                break;
            default: break;
        }
        
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
    
    public static boolean comprobarRepetirTirada(){
        // esto me funciona al reves de lo que quiero
        if ( tablero.getJugador().get(jugadorActivo).isrepTirada() == false ) return true;
        else return false;
    }
    
    public static void desocuparCasillaActual(int n){
        tablero.getCasilla().get(n).setOcupada(false);
    }
    
    public static boolean comprobarSalto(int n){
        // si hay que saltar igualamos la variable booleana saltar a true
        if ( tablero.getCasilla().get(n).getSalto() > 0 ) return true;
        else return false;
    }
    
    public static boolean comprobarPenalizacion(int j){
        if (tablero.getJugador().get(j).getTurnosSancionado() > 0) return true;
        else return false;
    }
    
    public static void pausarTresSegundos(){
         try {Thread.sleep(3000);} catch (InterruptedException e) {}
    }
    
    
    
} // FIN CLASS









/*    public static int preguntarJugadorActivo(){
        System.out.println("*****************************************");
        System.out.println();
        System.out.println("¿Con que jugador quieres jugar ahora?");
        for (int i = 0; i < tablero.getJugador().size() ; i++) {
            System.out.println("Con el "+tablero.getJugador().get(i).getNombre());
        }
        System.out.println();
        System.out.println("*****************************************");
        return teclado.nextInt() - 1;
    }
*/

        /* DEBUG 
        //debug de las casillas
        for (int i = 0; i < tablero.getCasilla().size() ; i++) {
            System.out.println("Casilla: "+tablero.getCasilla().get(i).getNumCasilla() + " Tipo: "+ tablero.getCasilla().get(i).getDescripcion());
        }
        //debug de los jugadores
        for (int i = 0; i < tablero.getJugador().size(); i++) {
            System.out.println(tablero.getJugador().get(i).getNombre() + "" );
        }
         // debug del dado 
        for (int i = 0; i < 100; i++) {
            int n = tablero.tirarDado();
            if(n < 1 || n > 6){
                System.out.println(n);
            }
        }
        */