package contentmanager.model.service.contentmanager;

import contentmanager.model.dto.ContentResponse;
import contentmanager.model.dto.ContentModifyResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ContentManager {
    ContentModifyResponse save(MultipartFile file);
    ContentModifyResponse delete(String name);
    ContentResponse getByName(String name);
}
