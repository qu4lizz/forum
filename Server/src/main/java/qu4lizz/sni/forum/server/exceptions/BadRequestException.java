package qu4lizz.sni.forum.server.exceptions;

public class BadRequestException extends Exception {
    public BadRequestException() {
        super("Bad Request");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
