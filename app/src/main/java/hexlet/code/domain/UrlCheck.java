package hexlet.code.domain;

import io.ebean.Model;
import io.ebean.annotation.NotNull;
import io.ebean.annotation.WhenCreated;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Lob;
import java.time.Instant;

@Entity
@Table(name = "url_checks")
@Getter
public class UrlCheck extends Model {

    @Id
    private long id;
    @WhenCreated
    private Instant createdAt;
    private int statusCode;
    private String title;
    private String h1;
    @Lob
    private String description;
    @ManyToOne
    @NotNull
    private Url url;

    public UrlCheck(int reqStatusCode, String reqTitle, String reqH1, String reqDescription, Url reqUrl) {
        this.statusCode = reqStatusCode;
        this.title = reqTitle;
        this.h1 = reqH1;
        this.description = reqDescription;
        this.url = reqUrl;
    }

}
