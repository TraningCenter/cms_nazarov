package contentmanager.model.service.identity;

public interface Hasher {
    String hash(byte[] bytes);
}
