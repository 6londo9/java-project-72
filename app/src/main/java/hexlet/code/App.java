package hexlet.code;

import io.javalin.Javalin;
import io.javalin.validation.ValidationException;

public class App {
    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "5000");
        return Integer.valueOf(port);
    }

    private static void addRoutes(Javalin app) {

        app.get("/", ctx -> ctx.result("Hello, World!"));

    }

    public static Javalin getApp() {

        Javalin app = Javalin.create(config -> config.plugins.enableDevLogging());

        app.exception(ValidationException.class, (e, ctx) -> {
            ctx.json(e.getErrors()).status(422);
        });

        addRoutes(app);

        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(getPort());
    }
}
