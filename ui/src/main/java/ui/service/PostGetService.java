package ui.service;

import ui.dto.ContentDto;
import ui.dto.PostDto;
import ui.dto.PostViewDto;

import java.io.IOException;

public class PostGetService {

    private String contentManagerUrl = "http://localhost:9091/content";

    private ContentManager contentManager = new DefaultContentManager();
    private PostManager postManager = new DefaultPostManager();

    public PostViewDto getPostById(Integer id) {
        PostDto post = postManager.getPost(id);

        StringBuilder stringBuilder = new StringBuilder();

        try {
            for (ContentDto contentDto : post.getContentRespons()) {
                if (contentDto.getThroughLink())
                    stringBuilder.append(contentManagerUrl).append("/").append(contentDto.getHash());
                else
                    stringBuilder.append(String.valueOf(
                                    contentManager.getContent(contentDto.getHash())));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return new PostViewDto(post.getPostId(), post.getTitle(), post.getUserDto(), stringBuilder.toString());
    }
}
