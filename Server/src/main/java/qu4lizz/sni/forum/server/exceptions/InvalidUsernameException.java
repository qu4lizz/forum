package qu4lizz.sni.forum.server.exceptions;

import org.apache.coyote.BadRequestException;

public class InvalidUsernameException extends BadRequestException {

    public InvalidUsernameException() {
        super("Username is invalid");
    }

    public InvalidUsernameException(String s) {
        super(s);
    }
}
