### Project tests and linter status:
[![Actions Status](https://github.com/6londo9/java-project-72/workflows/hexlet-check/badge.svg)](https://github.com/6londo9/java-project-72/actions)
<a href="https://codeclimate.com/github/6londo9/java-project-72/maintainability"><img src="https://api.codeclimate.com/v1/badges/a23396132a7fbd5c721c/maintainability" /></a>
<a href="https://codeclimate.com/github/6londo9/java-project-72/test_coverage"><img src="https://api.codeclimate.com/v1/badges/a23396132a7fbd5c721c/test_coverage" /></a>
---
### [Page analizator](https://java-project-72-production-348f.up.railway.app/)
#### This is a Web app, that you can use to check different sites for SEO suitability, like:

- Status code
- Title
- h1
- Description

It's contains bootstrap classes for a nice interface.

---
#### In case if you want to test it locally:

to make your local h2 DB run from root or /app directory
```
make generate-migrations
make start
```
Here you are. Now it's working locally and you can do whatever you want.

Default host: http://localhost:5000 . If your computer do not support that port, you should change it manually in App
```Java
private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "5000");
        return Integer.valueOf(port);
    }
```
