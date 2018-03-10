package contentmanager.model.service.repository;

import com.google.common.base.Optional;
import contentmanager.model.entities.ContentData;
import contentmanager.model.entities.ContentMeta;
import contentmanager.model.service.identity.ContentIdentityResolver;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Nullable;
import javax.swing.text.AbstractDocument;
import java.util.List;

public interface ContentMetaRepository extends CrudRepository<ContentMeta, Long>{
    Optional<ContentMeta> findOneById(Long id);
    Optional<ContentMeta> findByHashAndTemporaryIsFalse(String hash);
    Optional<ContentMeta> findByHashAndTemporaryIsTrue(String hash);

    Optional<ContentMeta> findByHash(String hash);

    Long countAllByHash(String hash);

    @Modifying
    @Query("delete from ContentMeta where hash=?1")
    Integer deleteAllByHash(String hash);

    List<ContentMeta> findAllByHash(String hash);
}
