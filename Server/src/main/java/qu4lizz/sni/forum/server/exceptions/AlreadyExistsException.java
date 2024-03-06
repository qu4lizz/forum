package qu4lizz.sni.forum.server.exceptions;

import org.apache.coyote.BadRequestException;

public class AlreadyExistsException extends BadRequestException {
    public AlreadyExistsException() {
        super("Email address already exists");
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}
