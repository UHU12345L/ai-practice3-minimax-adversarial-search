package P3;
import java.util.*;


class AgenteDummy {
	
    public String pensar(Entorno mapa, char jugador) {
        List<String> validas = new ArrayList<>();
        String[] acciones = { "E", "N", "S", "O" };
        
        for (String accion : acciones) {
            int nf = mapa.getAgenteF(jugador);
            int nc = mapa.getAgenteC(jugador);
            if (accion.equals("N"))
                nf--;
            if (accion.equals("S"))
                nf++;
            if (accion.equals("E"))
                nc++;
            if (accion.equals("O"))
                nc--;

            if (mapa.esTransitable(nf, nc)) {
                validas.add(accion);
            }
        }
        
        if (validas.isEmpty()) {
            return null; // Si no encuentra ninguna salida, devuelve null absoluto
        }
        
        Collections.shuffle(validas);
        return validas.get(0); // Elige una casilla transitable aleatoriamente
    }  
}
