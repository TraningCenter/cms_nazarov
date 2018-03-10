package contentmanager.model.service.repository;

import contentmanager.model.entities.ContentData;

public interface ContentDataRepository {
    ContentData save(ContentData contentData);
    ContentData read(String name);
    ContentData remove(String name);
    ContentData readAndRemove(String name);
}
