package qu4lizz.sni.forum.server.services;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class WAFService {
    private final SIEMService siemService;
    private final RevokeTokenService revokeTokenService;
    private final HttpServletRequest request;

    public WAFService(SIEMService siemService, HttpServletRequest request, RevokeTokenService revokeTokenService) {
        this.siemService = siemService;
        this.request = request;
        this.revokeTokenService = revokeTokenService;
    }

    public void checkRequest(BindingResult result) throws BadRequestException {
        if(!result.hasErrors())
            return;

        StringBuilder builder = new StringBuilder();
        builder.append("Bad request (").append(request.getRemoteAddr()).append("):");
        for (FieldError error : result.getFieldErrors()) {
            builder.append(error.getDefaultMessage()).append("|");
        }

        String message = builder.toString();
        this.siemService.log(message);

        var token = this.getToken();
        if(token != null)
            this.revokeTokenService.addToList(token);

        throw new BadRequestException();
    }

    private String getToken(){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null || authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
