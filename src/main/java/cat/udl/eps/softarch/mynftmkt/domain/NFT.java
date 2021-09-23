package cat.udl.eps.softarch.mynftmkt.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class NFT extends UriEntity<String>{

    @Id
    private String title;

    private String description;

    @ElementCollection
    private List<String> keywords;

    public String getCategory() {
        return category;
    }

    private String category;

    private String mediaType;

    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getId() {
        return getTitle();
    }
}
