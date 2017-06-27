package io.vertx.sample.hello;

import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class HelloVerticle extends LoggingVerticle {

  @Override
  public void start(Future<Void> fut) throws Exception {
    super.start();
    HttpServer server = vertx.createHttpServer();

    Router router = Router.router(vertx);

    router.route("/").handler(routingContext -> {

      // This handler will be called for every request
      HttpServerResponse response = routingContext.response();
      response.putHeader("content-type", "text/plain");

      // Write to the response and end it
      response.end("Hello World from Vert.x-Web!");
    });

      server.requestHandler(router::accept)
              .listen(
                      // Retrieve the port from the configuration,
                      // default to 8080.
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
