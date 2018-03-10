package contentmanager.model.service;

import contentmanager.model.dto.ContentResponse;
import contentmanager.model.dto.ContentEditResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ContentManager {
    ContentEditResponse save(MultipartFile file);
    ContentEditResponse delete(String name);
    ContentResponse getByName(String name);
}
