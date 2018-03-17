package contentmanager.controllers;

import contentmanager.model.dto.ContentResponse;
import contentmanager.model.dto.ContentModifyResponse;
import contentmanager.model.service.contentmanager.ContentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/content")
public class ContentController {

    @Autowired
    ContentManager contentManager;

    @PostMapping
    public ResponseEntity<ContentModifyResponse> addContent(@RequestParam("file") MultipartFile file){
        try {
             return ResponseEntity.ok(contentManager.save(file));
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path="/{name}")
    public ResponseEntity<ContentModifyResponse> deleteContent(@PathVariable String name){
        try {
             return ResponseEntity.ok(contentManager.delete(name));
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="/{name}")
    public ResponseEntity<byte[]> getContent(@PathVariable String name){
        ContentResponse contentResponse = contentManager.getByName(name);

        if (!contentResponse.getSuccess())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.parseMediaType(contentResponse.getContentType()));
        respHeaders.setContentLength(contentResponse.getContentLength());
        respHeaders.setContentDispositionFormData("attachment", contentResponse.getName());

        return new ResponseEntity<>(contentResponse.getBytes(), respHeaders, HttpStatus.OK);
    }
}
