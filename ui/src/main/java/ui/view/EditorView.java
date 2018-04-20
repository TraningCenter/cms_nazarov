package ui.view;

import ui.dto.PostDto;
import ui.service.PostSaveService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
public class EditorView {

    private PostSaveService postSaveService = new PostSaveService();

    private String text = "123";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void buttonAction(ActionEvent actionEvent) {
        PostDto savedPost = postSaveService.savePostData(text);
        addMessage(savedPost.getTitle() + " saved!");
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}