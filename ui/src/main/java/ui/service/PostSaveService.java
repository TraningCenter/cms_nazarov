package ui.service;

import org.apache.commons.codec.binary.Base64;
import ui.dto.ContentDto;
import ui.dto.PostDto;
import ui.dto.PostSaveRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class PostSaveService {

    private ContentManager contentManager = new DefaultContentManager();
    private PostManager postManager = new DefaultPostManager();

    private final String htmlMatchesToSplitSrc = "src\\s*=\\s*\"([^\"]+)\"|[\\S]+";
    private final String htmlMatchesToSplitSrc2 = "src\\s*=\\s*\"([^\"]+)\"|[\\S]+";
    private final String srcMatch = "src\\s*=\\s*\"([^\"]+)\"";

    public PostDto savePostData(String postData) {
        try {
            List<ContentDto> savedContents = saveContents(postData);
            PostDto savedPost = postManager.savePost(makePostSaveRequest(savedContents, "TITLE", null, 1L));
            return savedPost;
        } catch (ContentSaveException e) {
            e.printStackTrace();
        }
        return null;
    }

    private PostSaveRequest makePostSaveRequest(List<ContentDto> contents, String title, Long postId, Long userId) {
        PostSaveRequest postSaveRequest = new PostSaveRequest();
        postSaveRequest.setPostId(postId);
        postSaveRequest.setTitle(title);
        postSaveRequest.setUserId(userId);
        postSaveRequest.setContents(contents);

        return postSaveRequest;
    }

    private List<ContentDto> saveContents(String postData) throws ContentSaveException {
        List<ContentDto> savedContents = new LinkedList<>();
        List<String> matches = parseMatches(postData);
        for (String match : matches)
            savedContents.add(parseAndSaveContent(match));

        return savedContents;
    }

    private ContentDto parseAndSaveContent(String match) throws ContentSaveException {
        try {
            if (match.startsWith("@") && match.endsWith("@")) {
                String srcValue = match.substring(1, match.length()-1);
                return new ContentDto(saveSrc(srcValue), true);
            } else
                return new ContentDto(saveText(match), false);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ContentDto();
    }

    private String saveText(String match) throws IOException, ContentSaveException {
        return contentManager.saveContent(match.getBytes());
    }

    private String saveSrc(String srcValue) throws IOException, ContentSaveException {
        String[] splitArr = srcValue.split(";");
        String mimeType = splitArr[0].replace("data:", "");

        byte[] bytes;

        if (splitArr[1].startsWith("base64,"))
            bytes = Base64.decodeBase64(splitArr[1].replace("base64,", ""));
        else
            bytes = splitArr[1].getBytes();

        return contentManager.saveContent(bytes, mimeType);
    }

    public List<String> parseMatches(String text) {
        List<String> list = new ArrayList<String>();

        Pattern pattern = Pattern.compile(srcMatch);
        StringBuffer sb = new StringBuffer();
        Matcher m = pattern.matcher(text);

        while (m.find()) {
            String group = m.group(1);
            m.appendReplacement(sb, "src=\"~~@"+group+"@~~\"");
        }
        m.appendTail(sb);

        String s = sb.toString();
        String[] split = s.split("~~");

        return Arrays.stream(split).collect(Collectors.toList());
    }
}
