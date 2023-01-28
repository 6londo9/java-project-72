package hexlet.code.domain;

import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@ToString
public class Url extends Model {

    @Id
    private long id;
    @Column(unique = true)
    private String name;
    @WhenCreated
    private Instant createdAt;
    @OneToMany
    @OrderBy("id desc")
    private List<UrlCheck> checks;

    public Url(String urlName) {
        this.name = urlName;
    }
}
