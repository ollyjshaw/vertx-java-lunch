package io.vertx.sample.hello;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class LoggingVerticle extends AbstractVerticle {
    Logger logger = LoggerFactory.getLogger(LoggingVerticle.class);

    @Override
    public void init(Vertx vertx, Context context) {
        //System.out.println(context.config().getString("http.port"));
        String logmessage = "!!!!!!!!!!!!!!!!! INIT" + context.config().getInteger("http.port");
        logger.info(logmessage);

        super.init(vertx, context);
    }
}
