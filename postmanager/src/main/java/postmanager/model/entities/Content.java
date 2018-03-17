package postmanager.model.entities;

import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.domains.LongId;


@Entity
public class Content extends LongId{

    private String hash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", hash='" + hash +'\'' +
                '}';
    }
}
