package hexlet.code;

import hexlet.code.domain.Url;
import hexlet.code.domain.UrlCheck;
import hexlet.code.domain.query.QUrl;
import hexlet.code.domain.query.QUrlCheck;
import io.ebean.DB;
import io.ebean.Database;
import io.javalin.Javalin;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.Unirest;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public final class AppTest {

    private static Javalin app;
    private static String baseUrl;
    private static Database database;

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
        database = DB.getDefault();
        Url newUrl = new Url("https://hexlet.io");
        newUrl.save();
    }

    @AfterEach
    void afterEach() {
        database.script().run("/truncate.sql");
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

    @Test
    void testAddCheck() throws IOException {
        MockWebServer server = new MockWebServer();
        server.start();
        server.enqueue(new MockResponse().setResponseCode(200)
                .setBody(Files.readString(Path.of("src/test/resources/assets/body1.html")
                        .toAbsolutePath().normalize(), StandardCharsets.UTF_8)));


        String httpUrl = server.url("/").toString();
        Url url = new Url(httpUrl);
        url.save();

        Url expectedUrl = new QUrl()
                .name.equalTo(httpUrl)
                .findOne();

        assertThat(expectedUrl).isNotNull();

        HttpResponse<String> response = Unirest
                .post(baseUrl + "/urls/" + expectedUrl.getId() + "/checks")
                .asString();

        UrlCheck check = new QUrlCheck()
                .url.name.equalTo(url.getName())
                .findOne();
        assertThat(check).isNotNull();
        assertThat(check.getStatusCode()).isEqualTo(200);
        assertThat(check.getTitle()).isEqualTo("Testing checks");
        assertThat(check.getH1()).isEqualTo("Testing h1");

        server.shutdown();
    }
}
