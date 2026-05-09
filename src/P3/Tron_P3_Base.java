
package P3;

import java.util.*;

public class Tron_P3_Base {

	public static void main(String[] args) throws InterruptedException {

		String MAPA_CALLEJON = 
				"#########\n" +
				"#E      #\n" + 
				"# ##### #\n" + 
				"# #   # #\n" + 
				"#     S #\n" + 
				"#########";

		String MAPA_GRANDE = 
				"####################\n" + 
				"#E  #              #\n" + 
				"# # # ###### ##### #\n" + 
				"# #   #      #     #\n" + 
				"# ##### ###### ### #\n" + 
				"#     #        #   #\n" +
				"# ### ######## # # #\n" + 
				"#   #          #   #\n" + 
				"# ############## S #\n" + 
				"####################";

		String mapaTexto = 
				"################\n" + 
				"#E             #\n" + 
				"#              #\n" + 
				"#              #\n" + 
				"#              #\n" + 
				"#   #        S #\n" + 
				"################";
		Entorno juego = new Entorno(mapaTexto);

		// --- INSTANCIACIÓN DE AGENTES ---
		// Comentar/descomentar el algoritmo que se quiera usar para cada jugador

		// Minimax o AlfaBeta para el alumno
		//BusquedaMinimax agenteA = new BusquedaMinimax();
		BusquedaAlfaBeta agenteA = new BusquedaAlfaBeta();

		// El enemigo es un AgenteDummy (elige el primer movimiento válido que
		// encuentra)
		AgenteDummy agenteB = new AgenteDummy();

		//AgenteDummy agenteA = new AgenteDummy();
		//BusquedaMinimax agenteB = new BusquedaMinimax();
		
		System.out.println("--- Inicio de la Simulación ---");
		boolean juegoActivo = true;
		int ciclos = 0;

		while (juegoActivo && ciclos < 100) {
			System.out.println("\n\n--- Ciclo " + ciclos + " ---");
			juego.dibujar();

			// --- TURNO JUGADOR A ---
			String accionA = agenteA.pensar(juego, 'A');

			if (accionA == null) {
				System.out.println("[Agente A] Se ha quedado sin movimientos útiles a la vista. GANA EL JUGADOR B.");
				break;
			}

			System.out.println("[Agente A] Decide mover: " + accionA);
			if (!juego.moverAgente('A', accionA)) {
				System.out.println(">>> ¡CRASH! El jugador A ('A') se ha chocado con un muro o rastro. GANA B.");
				break;
			}
			if (juego.haGanado('A')) {
				System.out.println("\n>>> ¡VICTORIA! El jugador A ha cruzado a la columna opuesta.");
				juego.dibujar();
				break;
			}

			// --- TURNO JUGADOR B ---
			String accionB = agenteB.pensar(juego, 'B');

			if (accionB == null) {
				System.out.println("[Agente B] Se ha quedado sin movimientos útiles a la vista. GANA EL JUGADOR A.");
				break;
			}

			System.out.println("[Agente B] Decide mover: " + accionB);
			if (!juego.moverAgente('B', accionB)) {
				System.out.println(">>> ¡CRASH! El jugador B ('B') se ha chocado con un muro o rastro. GANA A.");
				break;
			}
			if (juego.haGanado('B')) {
				System.out.println("\n>>> ¡VICTORIA! El jugador B ha cruzado a la columna opuesta.");
				juego.dibujar();
				break;
			}

			Thread.sleep(400);
			ciclos++;
		}
	}
}