package qu4lizz.sni.forum.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qu4lizz.sni.forum.server.exceptions.UnauthorizedException;
import qu4lizz.sni.forum.server.models.dto.UrlDTO;
import qu4lizz.sni.forum.server.models.responses.JwtAuthResponse;
import qu4lizz.sni.forum.server.services.OAuthService;

@RestController
@RequestMapping("/api/oauth")
public class OAuthController {
    private final OAuthService oAuthService;

    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }


    @GetMapping("/url")
    public UrlDTO auth() {
        return oAuthService.authUrl();
    }

    @GetMapping("/callback")
    public JwtAuthResponse callback(@RequestParam("code") String code) throws UnauthorizedException {
        return oAuthService.callback(code);
    }
}
