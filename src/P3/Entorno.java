package P3;
import java.util.*;

class Entorno {
    char[][] grid;
    int filas, cols;
    int agenteAF, agenteAC;
    int agenteBF, agenteBC;
    int metaColA, metaColB;

    public Entorno(String mapa) {
        String[] lineas = mapa.trim().split("\n");
        filas = lineas.length;
        cols = lineas[0].length();
        grid = new char[filas][cols];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < cols; j++) {
                char celda = lineas[i].charAt(j);
                grid[i][j] = celda;
                if (celda == 'E' || celda == 'A') {
                    agenteAF = i;
                    agenteAC = j;
                    grid[i][j] = 'A';
                    metaColB = j;
                }
                if (celda == 'S' || celda == 'B') {
                    agenteBF = i;
                    agenteBC = j;
                    grid[i][j] = 'B';
                    metaColA = j;
                }
            }
        }
    }

    public boolean esMeta(char jugador, int f, int c) {
        return (jugador == 'A') ? c == metaColA : c == metaColB;
    }

    public boolean haGanado(char jugador) {
        return (jugador == 'A') ? agenteAC == metaColA : agenteBC == metaColB;
    }

    public boolean esTransitable(int f, int c) {
        if (f < 0 || f >= filas || c < 0 || c >= cols)
            return false;
        char celda = grid[f][c];
        // Los jugadores no pueden pisar paredes '#', rastros 'a'/'b', genéricos '.', ni
        // chocar
        return celda != '#' && celda != 'a' && celda != 'b' && celda != 'A' && celda != 'B' && celda != '.';
    }

    public boolean moverAgente(char jugador, String accion) {
        int f = (jugador == 'A') ? agenteAF : agenteBF;
        int c = (jugador == 'A') ? agenteAC : agenteBC;
        int nf = f, nc = c;
        if (accion.equalsIgnoreCase("N"))
            nf--;
        if (accion.equalsIgnoreCase("S"))
            nf++;
        if (accion.equalsIgnoreCase("E"))
            nc++;
        if (accion.equalsIgnoreCase("O"))
            nc--;

        if (!esTransitable(nf, nc))
            return false;

        // Se deja un rastro (a para el A, b para el B)
        grid[f][c] = (jugador == 'A') ? 'a' : 'b';

        if (jugador == 'A') {
            agenteAF = nf;
            agenteAC = nc;
        } else {
            agenteBF = nf;
            agenteBC = nc;
        }

        grid[nf][nc] = jugador;
        return true;
    }

    // Retrospectiva O(1) vital para que los algoritmos Minimax/AlfaBeta escruten el
    // árbol muy rápido
    public void deshacerMovimiento(char jugador, String accion) {
        int f = (jugador == 'A') ? agenteAF : agenteBF;
        int c = (jugador == 'A') ? agenteAC : agenteBC;

        // 1. La celda actual queda vacía de nuevo
        grid[f][c] = ' ';

        // 2. Calculamos de dónde vino retrocediendo la acción
        int origF = f;
        int origC = c;
        if (accion.equalsIgnoreCase("N"))
            origF++;
        if (accion.equalsIgnoreCase("S"))
            origF--;
        if (accion.equalsIgnoreCase("E"))
            origC--;
        if (accion.equalsIgnoreCase("O"))
            origC++;

        // 3. Restauramos las coordenadas
        if (jugador == 'A') {
            agenteAF = origF;
            agenteAC = origC;
        } else {
            agenteBF = origF;
            agenteBC = origC;
        }

        // 4. Restauramos el jugador a su celda orígen (pisando su antiguo rastro)
        grid[origF][origC] = jugador;
    }

    public int movimientosValidos(char jugador) {
        int f = getAgenteF(jugador);
        int c = getAgenteC(jugador);
        int validos = 0;
        if (esTransitable(f - 1, c))
            validos++;
        if (esTransitable(f + 1, c))
            validos++;
        if (esTransitable(f, c + 1))
            validos++;
        if (esTransitable(f, c - 1))
            validos++;
        return validos;
    }

    public boolean juegoTerminadoPara(char jugador) {
        return movimientosValidos(jugador) == 0;
    }

    public void dibujar() {
        for (char[] fila : grid) {
            System.out.println(new String(fila));
        }
    }

    public int getAgenteF(char jugador) {
        return (jugador == 'A') ? agenteAF : agenteBF;
    }

    public int getAgenteC(char jugador) {
        return (jugador == 'A') ? agenteAC : agenteBC;
    }

    public int getMetaCol(char jugador) {
        return (jugador == 'A') ? metaColA : metaColB;
    }
}