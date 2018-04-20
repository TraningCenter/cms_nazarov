package ui.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import ui.dto.PostDto;
import ui.dto.PostSaveRequest;

public class DefaultPostManager implements PostManager {

    private String postManagerUrl = "http://localhost:9092/post";
    @Override
    public PostDto savePost(PostSaveRequest postSaveRequest) {

        ObjectMapper mapper = new ObjectMapper();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(postManagerUrl);
            StringEntity params = new StringEntity(mapper.writeValueAsString(postSaveRequest));

            request.addHeader("content-type", "application/json");
            request.setEntity(params);

            String result = httpClient.execute(request, Util::handleResponse);

            return mapper.readValue(result, PostDto.class);
        } catch (Exception ex) {
            // handle exception here
        }

        return null;
    }

    @Override
    public PostDto getPost(Integer id) {
        ObjectMapper mapper = new ObjectMapper();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(postManagerUrl+"/"+id);

            request.addHeader("content-type", "application/json");

            String result = httpClient.execute(request, Util::handleResponse);

            return mapper.readValue(result, PostDto.class);
        } catch (Exception ex) {
            // handle exception here
        }

        return null;
    }


}
