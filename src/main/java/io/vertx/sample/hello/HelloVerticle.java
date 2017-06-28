package io.vertx.sample.hello;

import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.templ.HandlebarsTemplateEngine;

public class HelloVerticle extends LoggingVerticle {

    // In order to use a template we first need to create an engine

    @Override
  public void start(Future<Void> fut) throws Exception {
    super.start();


    final HandlebarsTemplateEngine engine = HandlebarsTemplateEngine.create();



        HttpServer server = vertx.createHttpServer();

    Router router = Router.router(vertx);

    router.route("/").handler(routingContext -> {

      // This handler will be called for every request
      HttpServerResponse response = routingContext.response();
      response.putHeader("content-type", "text/plain");

      // Write to the response and end it
      response.putHeader("content-type", "text/html").end("Hello World from Vert.x-Web!");
    });

      router.get().handler(ctx -> {
          ctx.put("title", "Welcome");
          ctx.put("welcome", "Good morning!");
          engine.render(ctx, "/templates/hello.hbs", res -> {
              if (res.succeeded()) {
                  ctx.response().end(res.result());
              } else {
                  ctx.fail(res.cause());
              }
          });
      });

      server.requestHandler(router::accept)
              .listen(
                      config().getInteger("http.port", 8080),
                      result -> {
                          if (result.succeeded()) {
                              fut.complete();
                          } else {
                              fut.fail(result.cause());
                          }
                      }
              );
  }
}
