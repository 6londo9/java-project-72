package hexlet.code.domain;

import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name="urls")
@Getter
public class Url extends Model {

    @Id
    long id;

    String name;

    @WhenCreated
    Instant createdAt;

    public Url (String name) {
        this.name = name;
    }
}
