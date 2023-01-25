package hexlet.code.controllers;

import hexlet.code.domain.UrlCheck;
import io.javalin.core.validation.JavalinValidation;
import io.javalin.core.validation.ValidationError;
import io.javalin.core.validation.Validator;
import io.javalin.http.Handler;

import java.util.List;
import java.util.Map;
import java.net.URL;

import hexlet.code.domain.Url;
import hexlet.code.domain.query.QUrl;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.Unirest;

public class UrlController {

    public static Handler listUrls = ctx -> {

        List<Url> urls = new QUrl()
                .orderBy()
                    .id.desc()
                .findList();

        ctx.attribute("urls", urls);
        ctx.render("urls/index.html");
    };

    public static Handler showUrl = ctx -> {
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);

        Url url = new QUrl()
                .id.equalTo(id)
                .findOne();

        ctx.attribute("url", url);
        ctx.render("urls/show.html");
    };

    public static Handler createUrl = ctx -> {

        String url = "";

        try {
            URL incomeUrl = new URL(ctx.formParam("url"));
            url = new StringBuilder()
                    .append(incomeUrl.getProtocol())
                    .append("://")
                    .append(incomeUrl.getAuthority())
                    .append("/")
                    .toString();

        } catch (Exception e) {

        }

        Validator<String> urlValidator = new Validator<>(url, String.class, "url")
                .check(value -> !value.equals(""), "Некорректный URL");

        Map<String, List<ValidationError<?>>> errors = JavalinValidation.collectErrors(urlValidator);

        if (!errors.isEmpty()) {
            ctx.status(HttpStatus.UNPROCESSABLE_ENTITY);
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.redirect("/");
            return;
        }

        Url isExist = new QUrl()
                .name.equalTo(url)
                .findOne();

        if (isExist != null) {
            ctx.sessionAttribute("flash", "Страница уже существует");

        } else {
            Url newUrl = new Url(url);
            newUrl.save();
            ctx.sessionAttribute("flash", "Страница успешно добавлена!");
        }
        ctx.redirect("/urls");
    };

    public static Handler checkUrl = ctx -> {

        long id = ctx.pathParamAsClass("id", long.class).getOrDefault(null);

        Url url = new QUrl()
                .id.equalTo(id)
                .findOne();

        HttpResponse<String> response = Unirest
                .get(url.getName())
                .asString();

        int statusCode = response.getStatus();
        String title = response.getHeaders().getFirst("title");
        String h1 = response.getHeaders().getFirst("h1");
        String description = response.getBody().;

        UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description, url);
        urlCheck.save();

        ctx.redirect("/urls/" + id);
    };
}
