package contentmanager.model.service.identity;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

@Component
public class Sha256Hasher implements Hasher {

    public String hash(byte[] bytes){
        return Hashing.sha256().hashBytes(bytes).toString();
    }
}
