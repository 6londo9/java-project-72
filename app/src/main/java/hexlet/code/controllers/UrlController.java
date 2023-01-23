package hexlet.code.controllers;

import io.javalin.core.validation.JavalinValidation;
import io.javalin.core.validation.ValidationError;
import io.javalin.core.validation.Validator;
import io.javalin.http.Handler;

import java.util.List;
import java.util.Map;
import java.net.URL;

import hexlet.code.domain.Url;
import hexlet.code.domain.query.QUrl;
import org.eclipse.jetty.http.HttpStatus;

public class UrlController {

    public static Handler listUrls = ctx -> {

        List<Url> urls = new QUrl()
                .orderBy()
                    .id.asc()
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
                    .toString();

        } catch (Exception e) {

        }

        Validator<String> urlValidator = new Validator<>(url, String.class, "url")
                .check(value -> !value.equals(""), "Некорректный URL");

        Map<String, List<ValidationError<?>>> errors = JavalinValidation.collectErrors(urlValidator);

        if (!errors.isEmpty()) {
            ctx.status(HttpStatus.UNPROCESSABLE_ENTITY_422);
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
}
