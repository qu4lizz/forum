package qu4lizz.sni.forum.server.exceptions;

public class UnauthorizedException extends Exception {
    public UnauthorizedException() {
        super("You don't have authorization");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
