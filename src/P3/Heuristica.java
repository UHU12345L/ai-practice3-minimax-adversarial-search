package P3;

class Heuristica {
    static int INFINITO = 999999;
    //heuristica, llamar cdo no puedo seguir explorando
    public static int utilidad(Entorno mapa) {
        // estados terminales: A gana o pierde. comprobar si juego terminado
        if (mapa.juegoTerminadoPara('B')) return  INFINITO; // B sin movimientos: A gana
        if (mapa.juegoTerminadoPara('A')) return -INFINITO; // A sin movimientos: A pierde
        
        // h1: diferencia de movimientos libres
        int movilidadA = mapa.movimientosValidos('A');
        int movilidadB = mapa.movimientosValidos('B');
        int difMovilidad = movilidadA - movilidadB;
        
        // h2: distancia Manhattan de A a su meta 
        int distA = Math.abs(mapa.getAgenteC('A') - mapa.getMetaCol('A'));

        return difMovilidad * 10 - distA * 2;
    }

    // solo distancia Manhattan
    public static int utilidadSimple(Entorno mapa) {
        if (mapa.juegoTerminadoPara('B')) return  INFINITO;
        if (mapa.juegoTerminadoPara('A')) return -INFINITO;

        int distA = Math.abs(mapa.getAgenteC('A') - mapa.getMetaCol('A'));

        return -distA * 2;
    }
}
