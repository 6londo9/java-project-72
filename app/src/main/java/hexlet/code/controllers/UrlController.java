package hexlet.code.controllers;

import hexlet.code.domain.UrlCheck;
import hexlet.code.domain.query.QUrlCheck;
import io.javalin.http.Handler;

import java.util.List;
import java.net.URL;
import java.util.Map;

import hexlet.code.domain.Url;
import hexlet.code.domain.query.QUrl;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UrlController {

    public static Handler listUrls = ctx -> {

        int page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        int rowsPerPage = 10;
        int offset = (page - 1) * rowsPerPage;
        int idCount = new QUrl()
                .findList().size();
        int maxPage = idCount / rowsPerPage + ((idCount % rowsPerPage == 0) ? 0 : 1);

        List<Url> urls = new QUrl()
                .setFirstRow(offset)
                .setMaxRows(rowsPerPage)
                .orderBy()
                    .id.asc()
                .findPagedList()
                .getList();

        Map<Long, UrlCheck> urlChecks = new QUrlCheck()
                .url.id.asMapKey()
                .orderBy()
                    .createdAt.desc()
                .findMap();

        ctx.attribute("urls", urls);
        ctx.attribute("urlChecks", urlChecks);
        ctx.attribute("page", page);
        ctx.attribute("maxPage", maxPage);
        ctx.render("urls/index.html");
    };

    public static Handler showUrl = ctx -> {
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);

        Url url = new QUrl()
                .where()
                .id.equalTo(id)
                .findOne();
        List<UrlCheck> checks = new QUrlCheck()
                .where()
                .url.equalTo(url)
                .orderBy()
                .id.desc()
                .findList();

        if (url == null) {
            throw new NotFoundResponse();
        }

        ctx.attribute("url", url);
        ctx.attribute("urlChecks", checks);
        ctx.render("urls/show.html");
    };

    public static Handler createUrl = ctx -> {

        String inputUrl = ctx.formParam("url");
        URL parsedUrl;

        try {
            parsedUrl = new URL(inputUrl);

        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "danger");
            ctx.redirect("/");
            return;
        }

        String normalizedUrl = new StringBuilder()
                .append(parsedUrl.getProtocol())
                .append("://")
                .append(parsedUrl.getAuthority())
                .toString();

        Url url = new QUrl()
                .name.equalTo(normalizedUrl)
                .findOne();

        if (url != null) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flashType", "info");

        } else {
            Url newUrl = new Url(normalizedUrl);
            newUrl.save();
            ctx.sessionAttribute("flash", "Страница успешно добавлена!");
            ctx.sessionAttribute("flashType", "success");
        }
        ctx.redirect("/urls");
    };

    public static Handler checkUrl = ctx -> {

        long id = ctx.pathParamAsClass("id", long.class).getOrDefault(null);

        Url url = new QUrl()
                .id.equalTo(id)
                .findOne();

        if (url == null) {
            throw new NotFoundResponse();
        }

        HttpResponse<String> response;
        try {
            response = Unirest
                    .get(url.getName())
                    .asString();
            String html = response.getBody();
            Document doc = Jsoup.parse(html);

            int statusCode = response.getStatus();
            String title = doc.title();
            String h1 = doc.select("h1").text();
            String description = doc.select("meta[name=description]").attr("content");

            UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description, url);
            urlCheck.save();

            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashType", "success");

        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
            ctx.sessionAttribute("flashType", "danger");

        } finally {
            ctx.redirect("/urls/" + id);
        }
    };
}
