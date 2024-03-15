package qu4lizz.sni.forum.server.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qu4lizz.sni.forum.server.models.dto.UrlDTO;
import qu4lizz.sni.forum.server.models.responses.JwtAuthResponse;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/api/oauth")
public class OAuthController {
    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}")
    private String clientSecret;

    @Value("${security.client}")
    private String clientUrl;

    @GetMapping("/url")
    public UrlDTO auth() {
        String url = new GoogleAuthorizationCodeRequestUrl(
                clientId,
                clientUrl,
                Arrays.asList(
                        "email",
                        "profile",
                        "openid")).build();

        return new UrlDTO(url);
    }

    @GetMapping("/callback")
    public ResponseEntity<JwtAuthResponse> callback(@RequestParam("code") String code) {
        String token;
        try {
            token = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(), new GsonFactory(),
                    clientId,
                    clientSecret,
                    code,
                    clientUrl
            ).execute().getAccessToken();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(new JwtAuthResponse(token));
    }
}
