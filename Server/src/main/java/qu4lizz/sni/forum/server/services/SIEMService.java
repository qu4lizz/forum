package qu4lizz.sni.forum.server.services;

import org.springframework.stereotype.Service;
import qu4lizz.sni.forum.server.models.entities.LogEntity;
import qu4lizz.sni.forum.server.repositories.LogRepository;

@Service
// Security information and event management
public class SIEMService {
    private final LogRepository logRepository;

    public SIEMService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void log(String content) {
        LogEntity logEntity = new LogEntity(content);

        logRepository.save(logEntity);
    }
}
