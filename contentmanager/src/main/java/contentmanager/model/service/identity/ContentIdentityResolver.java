package contentmanager.model.service.identity;

public interface ContentIdentityResolver {
    void addToJobQueue(IdentityResolveJob job);
}
