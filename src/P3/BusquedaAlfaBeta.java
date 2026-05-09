package P3;
import java.util.*;
	//esMeta
	//haGanado
	//esTransitable
	//moverAgente
	//deshacerMovimiento
	//movimientosValidos
	//juegoTerminadoPara
	//getAgenteF
	//getAgenteC
	//getMetaCol

class BusquedaAlfaBeta {
 private int maxProfundidad = 6; //limite arbol de busqueda
 private int nodosVisitados=0;
 
 public String pensar(Entorno mapa, char jugador) {
     String mejorAccion = null;
     nodosVisitados=0;
     int mejorValor = -Heuristica.INFINITO;
     int alfa = -Heuristica.INFINITO; //mejor valor para max hasta ahora
     int beta = Heuristica.INFINITO; //mejor valor para min hasta ahora
     
     String[] acciones = { "N", "S", "E", "O" };
     for (int i = 0; i < acciones.length; i++) {
         String accion = acciones[i];

         // calcular la casilla destino
         int nf = mapa.getAgenteF(jugador);
         int nc = mapa.getAgenteC(jugador);
         if (accion.equals("N")) nf--;
         if (accion.equals("S")) nf++;
         if (accion.equals("E")) nc++;
         if (accion.equals("O")) nc--;

         if (!mapa.esTransitable(nf, nc)) continue;

         //probar
         mapa.moverAgente(jugador, accion);
         //rival
         int valor = minValor(mapa, maxProfundidad - 1,alfa,beta);
         mapa.deshacerMovimiento(jugador, accion);

         if (valor > mejorValor) {
             mejorValor = valor;
             mejorAccion = accion;
         }
         //alfa: mejor valor encontrado hasta ahora
         if(mejorValor>alfa) {
        	 alfa=mejorValor;
         }
     }
     System.out.println("Nodos visitados AlfaBeta: "+nodosVisitados);
     return mejorAccion;
 }

//alfa: valor que max tiene garantizado
 private int maxValor(Entorno mapa, int profundidad, int alfa, int beta) {
	 nodosVisitados++;
     if (profundidad == 0 || mapa.juegoTerminadoPara('A') || mapa.juegoTerminadoPara('B')) {
         return Heuristica.utilidad(mapa);
     }
    
     String[] acciones = { "N", "S", "E", "O" }; //sucesores
     int mejorValor=-Heuristica.INFINITO;
     boolean hayMovimiento = false;

     for (int i = 0; i < acciones.length; i++) {
         String accion = acciones[i];
         int nf = mapa.getAgenteF('A');
         int nc = mapa.getAgenteC('A');
         if (accion.equals("N")) nf--;
         if (accion.equals("S")) nf++;
         if (accion.equals("E")) nc++;
         if (accion.equals("O")) nc--;

         if (!mapa.esTransitable(nf, nc)) continue;

         hayMovimiento = true;
         mapa.moverAgente('A', accion);  
         // v: MAX(v, MIN-VALOR(s))
         int valorHijo = minValor(mapa, profundidad - 1,alfa,beta);
         mapa.deshacerMovimiento('A', accion);
        
         //actualizar mejor valor
         if(valorHijo>mejorValor) {
        	 mejorValor=valorHijo;
         }
         //alfa: mejor valor encontrado hasta ahora
         if (valorHijo > alfa) {
             alfa = valorHijo;
         }
         //poda beta: min no elige ese camino, corta
         //rival ya tiene otro camino mejor que el mejor que yo puedo conseguir aqui
         if(mejorValor>=beta) {
        	 break;
         }
     }

     // si no habia ningun movimiento valido, es un nodo terminal
     if (!hayMovimiento) {
         return Heuristica.utilidad(mapa);
     }
     return mejorValor;
 }

//beta: valor que min tiene garantizado
 // B: quiere el valor mas bajo posible (perjudicar a A)
 private int minValor(Entorno mapa, int profundidad,int alfa, int beta) {
	 nodosVisitados++;
     if (profundidad == 0 || mapa.juegoTerminadoPara('A') || mapa.juegoTerminadoPara('B')) {
         return Heuristica.utilidad(mapa);
     }

     String[] acciones = { "N", "S", "E", "O" };
     boolean hayMovimiento = false;
     int mejorValor=Heuristica.INFINITO;
     
     for (int i = 0; i < acciones.length; i++) {
         String accion = acciones[i];
         int nf = mapa.getAgenteF('B');
         int nc = mapa.getAgenteC('B');
         if (accion.equals("N")) nf--;
         if (accion.equals("S")) nf++;
         if (accion.equals("E")) nc++;
         if (accion.equals("O")) nc--;

         if (!mapa.esTransitable(nf, nc)) continue;

         hayMovimiento = true;
         //hacer
         mapa.moverAgente('B', accion);
         // v: MIN(v, MAX-VALOR(s))
         int valorHijo = maxValor(mapa, profundidad - 1,alfa,beta); // INFINITO
         mapa.deshacerMovimiento('B', accion);
        
         //actualizar mejor valor
         if(valorHijo<mejorValor) {
        	 mejorValor=valorHijo;
         }
         //beta: peor valor encontrado hasta ahora
         if (mejorValor < beta) {
             beta = mejorValor;
         }
         //poda alfa: max no elige ese camino, corta
         //yo ya tengo otro camino mejor que el mejor que rival puede conseguir aqui

         if(mejorValor<=alfa) {
        	 break;
         }        
     }

     if (!hayMovimiento) {
         return Heuristica.utilidad(mapa);
     }
     return mejorValor;
 }
}