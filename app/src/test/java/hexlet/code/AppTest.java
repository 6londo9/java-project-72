package hexlet.code;

import hexlet.code.domain.Url;
import hexlet.code.domain.query.QUrl;
import io.ebean.DB;
import io.ebean.Database;
import io.javalin.Javalin;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.Unirest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class AppTest {

    private static Javalin app;
    private static String baseUrl;

    @BeforeAll
    public static void beforeAll() {
        app = App.getApp();
        app.start(0);
        int port = app.port();
        baseUrl = "http://localhost:" + port;
    }

    @AfterAll
    public static void afterAll() {
        app.stop();
    }

    @BeforeEach
    void beforeEach() {
        Database db = DB.getDefault();
        db.truncate("urls");
        Url newUrl = new Url("https://hexlet.io");
        newUrl.save();
    }

    @Test
    void testHomePageWorking() {
        HttpResponse<String> response = Unirest
                .get(baseUrl + "/")
                .asString();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testAddNewUrl() {
        String urlName = "https://google.ru";

        HttpResponse<String> response = Unirest
                .post(baseUrl + "/urls")
                .field("url", urlName)
                .asString();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);

        Url newUrl = new QUrl()
                .name.equalTo(urlName)
                .findOne();

        assertThat(newUrl).isNotNull();
    }

    @Test
    void testAddIncorrectUrl() {
        String incorrectUrlName = "hexlet.io";

        HttpResponse<String> response = Unirest
                .post(baseUrl + "/urls")
                .field("url", incorrectUrlName)
                .asEmpty();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);

        Url testUrl = new QUrl()
                .name.equalTo(incorrectUrlName)
                .findOne();

        assertThat(testUrl).isNull();
    }

    @Test
    void testUrlsPage() {
        String existingUrl = "https://hexlet.io";

        HttpResponse<String> response = Unirest
                .get(baseUrl + "/urls")
                .asString();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(existingUrl);
    }

    @Test
    void testUrlPage() {
        String existingUrlName = "https://hexlet.io";

        Url existingUrl = new QUrl()
                .name.equalTo(existingUrlName)
                .findOne();

        HttpResponse<String> response = Unirest
                .get(baseUrl + "/urls/" + existingUrl.getId())
                .asString();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(existingUrlName);
    }
}
