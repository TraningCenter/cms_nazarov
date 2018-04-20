package ui.view;

import ui.dto.PostDto;
import ui.dto.PostViewDto;
import ui.service.PostGetService;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class PostView {

    private Integer item;
    private PostViewDto postViewDto;

    private PostGetService postGetService = new PostGetService();

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public PostViewDto getPostViewDto() {
        return postViewDto;
    }

    public void setPostViewDto(PostViewDto postViewDto) {
        this.postViewDto = postViewDto;
    }

    public void checkItem(Integer item){
        postViewDto = postGetService.getPostById(item);
    }

    public Boolean isPostFound(){
        return postViewDto!=null;
    }

}
