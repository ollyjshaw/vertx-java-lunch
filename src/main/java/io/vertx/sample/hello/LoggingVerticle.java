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
        logger.info("!!!!!!!!!!!!!!!!! INIT");
        super.init(vertx, context);
    }

    @Override
    public void start() throws Exception{
        logger.info("!!!!!!!!!!!!! STARTING");
        super.start();
    }
}
