package qu4lizz.sni.forum.server.services;

import org.springframework.stereotype.Service;
import qu4lizz.sni.forum.server.models.entities.RevokedTokenEntity;
import qu4lizz.sni.forum.server.repositories.RevokeTokenRepository;

@Service
public class RevokeTokenService {
    private final RevokeTokenRepository revokeTokenRepository;

    public RevokeTokenService(RevokeTokenRepository revokeTokenRepository) {
        this.revokeTokenRepository = revokeTokenRepository;
    }

    public void addToList(String token) {
        RevokedTokenEntity revokedToken = new RevokedTokenEntity(token);

        revokeTokenRepository.save(revokedToken);
    }

    public boolean isRevoked(String token) {
        return revokeTokenRepository.existsById(token);
    }
}
