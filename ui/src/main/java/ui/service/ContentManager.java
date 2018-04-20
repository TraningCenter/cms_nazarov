package ui.service;

import java.io.IOException;

public interface ContentManager {
    String saveContent(byte[] bytes) throws IOException, ContentSaveException;
    String saveContent(byte[] bytes, String contentType) throws IOException, ContentSaveException;
    byte[] getContent(String hash) throws IOException;
}
