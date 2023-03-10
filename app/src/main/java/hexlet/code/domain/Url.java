package hexlet.code.domain;

import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
public class Url extends Model {

    @Id
    private long id;
    @Column(unique = true)
    private String name;
    @WhenCreated
    private Instant createdAt;
    @OneToMany
    private List<UrlCheck> checks;

    public Url(String urlName) {
        this.name = urlName;
    }
}
