package qu4lizz.sni.forum.server.exceptions;

import org.apache.coyote.BadRequestException;

public class EmailAlreadyExistException extends BadRequestException {
    public EmailAlreadyExistException() {
        super("Email address already exists");
    }

    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
