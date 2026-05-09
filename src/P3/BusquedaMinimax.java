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

class BusquedaMinimax {
 private int maxProfundidad = 4; //limite arbol de busqueda
 private int nodosVisitados=0;
 
 //imaginar todos los futuros posibles hasta profundidad, elegir el de mejor futuro en el peor caso
 //rival juega lo peor posible para perjudicarme
 public String pensar(Entorno mapa, char jugador) {
	 nodosVisitados=0; //decisiones independientes
     String mejorAccion = null;
     int mejorValor = -Heuristica.INFINITO; //cualquier mov mejor

     //probar 4 direcciones y calcular casilla si mueve ali
     //elegir casilla mayor valor
     String[] acciones = { "N", "S", "E", "O" };
     for (int i = 0; i < acciones.length; i++) {
         String accion = acciones[i];

         // calcular la casilla a la q llegaria, cada dir
         int nf = mapa.getAgenteF(jugador);
         int nc = mapa.getAgenteC(jugador);
         if (accion.equals("N")) nf--;
         if (accion.equals("S")) nf++;
         if (accion.equals("E")) nc++;
         if (accion.equals("O")) nc--;

         //si casilla no transitable la paso
         if (!mapa.esTransitable(nf, nc)) continue;

         //mover o probar A
         mapa.moverAgente(jugador, accion);    
         //B explora (A gasto 1 nivel profundidad)
         int valor = minValor(mapa, maxProfundidad - 1);
         //dejar como estaba antes de siguiente
         mapa.deshacerMovimiento(jugador, accion);

         //si mov es mejor guardo
         if (valor > mejorValor) {
             mejorValor = valor;
             mejorAccion = accion;
         }
     }
     System.out.println("Nodos visitados MinMax: "+nodosVisitados);
     return mejorAccion; 
 }

 // A: quiere el valor mas alto posible
 private int maxValor(Entorno mapa, int profundidad) {
	 nodosVisitados++; //cada llama: un nodo del arbol q estamos visitando
	 //test-terminal
     if (profundidad == 0 || mapa.juegoTerminadoPara('A') || mapa.juegoTerminadoPara('B')) {
         return Heuristica.utilidad(mapa);
     }

     int v = -Heuristica.INFINITO; //cualquier mov mejor

     String[] acciones = { "N", "S", "E", "O" }; //sucesores
     boolean hayMovimiento = false;
     
     // calcular la casilla destino, cada dir
     for (int i = 0; i < acciones.length; i++) {
         String accion = acciones[i];
         int nf = mapa.getAgenteF('A');
         int nc = mapa.getAgenteC('A');
         if (accion.equals("N")) nf--;
         if (accion.equals("S")) nf++;
         if (accion.equals("E")) nc++;
         if (accion.equals("O")) nc--;
       
         //si casilla no transitable la paso
         if (!mapa.esTransitable(nf, nc)) continue;

         hayMovimiento = true;
       //mover o probar
         mapa.moverAgente('A', accion);
         
         //B explora (A gasto 1 nivel profundidad)
         int valorHijo = minValor(mapa, profundidad - 1); //turno de B
         mapa.deshacerMovimiento('A', accion);
         // v: MAX(v, MIN-VALOR(s))
         if (valorHijo > v) {
             v = valorHijo;
         }
     }

     // si no habia ningun movimiento valido, es un nodo terminal
     if (!hayMovimiento) {
         return Heuristica.utilidad(mapa);
     }
     return v; //mejor puntuacion de estado
 }


 // B: quiere el valor mas bajo posible (perjudicar a A)
 private int minValor(Entorno mapa, int profundidad) {
	 nodosVisitados++;
     if (profundidad == 0 || mapa.juegoTerminadoPara('A') || mapa.juegoTerminadoPara('B')) {
         return Heuristica.utilidad(mapa);
     }

     int v = Heuristica.INFINITO;

     String[] acciones = { "N", "S", "E", "O" };
     boolean hayMovimiento = false;

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
         mapa.moverAgente('B', accion);
         
         //A explora (B gasto 1 nivel profundidad)
         int valorHijo = maxValor(mapa, profundidad - 1);
         mapa.deshacerMovimiento('B', accion);
         // v: MIN(v, MAX-VALOR(s))
         if (valorHijo < v) {
             v = valorHijo;
         }
     }

     if (!hayMovimiento) {
         return Heuristica.utilidad(mapa);
     }
     return v;
 }
 
}