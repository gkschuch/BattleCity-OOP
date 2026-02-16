package ranking.exceptions;

public class RankingException extends RuntimeException {
    public RankingException(String message) {
        super(message);
    }

    public RankingException(String message, Throwable cause) {
        super(message, cause);
    }
}
