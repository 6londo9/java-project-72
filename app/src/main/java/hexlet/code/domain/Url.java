package hexlet.code.domain;

import io.ebean.annotation.WhenCreated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name="urls")
public class Url {

    @Id
    long id;

    String name;

    @WhenCreated
    Instant createdAt;
}
