package grid.exceptions;

public class MapLoadException extends GridException {
    public MapLoadException(String mapFile, Throwable cause) {
        super("Falha crítica ao carregar o ficheiro do mapa: " + mapFile, cause);
    }
}
