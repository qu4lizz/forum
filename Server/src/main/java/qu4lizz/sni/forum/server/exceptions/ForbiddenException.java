package qu4lizz.sni.forum.server.exceptions;

public class ForbiddenException extends Exception {
    public ForbiddenException() {
        super("You don't have permission to do this");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
