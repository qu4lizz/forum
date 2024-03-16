package qu4lizz.sni.forum.server.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import qu4lizz.sni.forum.server.exceptions.UnauthorizedException;
import qu4lizz.sni.forum.server.models.dto.JwtUser;
import qu4lizz.sni.forum.server.models.dto.UrlDTO;
import qu4lizz.sni.forum.server.models.dto.UserInfo;
import qu4lizz.sni.forum.server.models.entities.PermissionEntity;
import qu4lizz.sni.forum.server.models.entities.UserEntity;
import qu4lizz.sni.forum.server.models.enums.Role;
import qu4lizz.sni.forum.server.models.enums.Status;
import qu4lizz.sni.forum.server.models.responses.JwtAuthResponse;
import qu4lizz.sni.forum.server.repositories.PermissionRepository;
import qu4lizz.sni.forum.server.repositories.TopicRepository;
import qu4lizz.sni.forum.server.repositories.UserRepository;

import java.io.IOException;
import java.util.Arrays;

@Service
public class OAuthService {
    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}")
    private String clientSecret;

    @Value("${security.client}")
    private String clientUrl;
    private final WebClient userInfoClient;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final PermissionRepository permissionRepository;
    private final JwtService jwtService;
    private final JwtUserDetailsService jwtUserDetailsService;


    public OAuthService(WebClient userInfoClient, UserRepository userRepository, TopicRepository topicRepository, PermissionRepository permissionRepository, JwtService jwtService, JwtUserDetailsService jwtUserDetailsService) {
        this.userInfoClient = userInfoClient;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.permissionRepository = permissionRepository;
        this.jwtService = jwtService;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }


    public UrlDTO authUrl() {
        String url = new GoogleAuthorizationCodeRequestUrl(
                clientId,
                clientUrl,
                Arrays.asList(
                        "email",
                        "profile",
                        "openid")).build();

        return new UrlDTO(url);
    }

    public JwtAuthResponse callback(String code) throws UnauthorizedException {
        try {
            String token = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(), new GsonFactory(),
                    clientId,
                    clientSecret,
                    code,
                    clientUrl
            ).execute().getAccessToken();

            UserInfo userInfo = userInfoClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/oauth2/v3/userinfo")
                            .queryParam("access_token", token)
                            .build())
                    .retrieve()
                    .bodyToMono(UserInfo.class)
                    .block();

            var userEntity = userRepository.findByEmail(userInfo.email());

            if (userEntity.isPresent()) {
                if (userEntity.get().getPassword() != null)
                    throw new UnauthorizedException();

                JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(userEntity.get().getUsername());
                return new JwtAuthResponse(jwtService.generateToken(jwtUser), userEntity.get().getUsername(), userEntity.get().getRole());
            }
            else {
                UserEntity newUser = new UserEntity();
                newUser.setUsername(userInfo.name());
                newUser.setEmail(userInfo.email());
                newUser.setPassword(null);
                newUser.setRole(Role.USER);
                newUser.setStatus(Status.APPROVED);
                newUser.setOAuth2User(true);

                UserEntity saved = userRepository.save(newUser);

                var topics = topicRepository.findAll();
                for (var topic : topics) {
                    permissionRepository.save(new PermissionEntity(saved.getId(), topic.getId()));
                }

                JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(saved.getUsername());
                return new JwtAuthResponse(jwtService.generateToken(jwtUser), newUser.getUsername(), newUser.getRole());
            }

        } catch (IOException e) {
            throw new UnauthorizedException();
        }
    }
}
