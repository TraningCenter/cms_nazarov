package ui.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.primefaces.json.JSONObject;
import ui.content.Content;

import java.io.Console;
import java.io.File;
import java.io.IOException;

public class DefaultContentManager implements ContentManager {

    private String contentManagerUrl = "http://localhost:9091/content";

    @Override
    public String saveContent(byte[] bytes) throws IOException, ContentSaveException {
        return saveContent(bytes, null);
    }

    @Override
    public String saveContent(byte[] bytes, String contentType) throws IOException, ContentSaveException {


        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addBinaryBody("file", bytes,
                            contentType!=null? ContentType.create(contentType):
                            ContentType.DEFAULT_BINARY, "myfile");

            HttpEntity data = entityBuilder.build();

            HttpPost request = new HttpPost(contentManagerUrl);
            request.setEntity(data);

            String result = httpclient.execute(request, Util::handleResponse);
            System.out.println(result);

            return parserResult(result);
        }
    }

    private String parserResult(String result) throws ContentSaveException {
        final JSONObject obj = new JSONObject(result);
        if (obj.getBoolean("success"))
            return obj.getString("hash");
        throw new ContentSaveException();
    }
}
