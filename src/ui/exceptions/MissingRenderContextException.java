package ui.exceptions;

public class MissingRenderContextException extends UIException {
    public MissingRenderContextException(String component) {
        super("Falha ao renderizar: O componente obrigatório '" + component + "' está nulo.");
    }
}
