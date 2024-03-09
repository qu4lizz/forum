package qu4lizz.sni.forum.server.exceptions;

public class UnauthorizedException extends Exception {
    public UnauthorizedException() {
        super("Invalid credentials");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
