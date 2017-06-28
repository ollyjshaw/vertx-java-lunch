
package io.vertx.sample.hello;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class FirstTest {

    private Vertx vertx;

    @Before
    public void setUp(TestContext context) {
        vertx = Vertx.vertx();
        vertx.deployVerticle(HelloVerticle.class.getName(),
                context.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testMyApplication(TestContext context) {


        for (int ii = 0; ii<100; ii++) {
            final Async async = context.async();

            vertx.createHttpClient().getNow(8080, "localhost", "/",
                    response -> {
                        response.handler(body -> {
                            context.assertTrue(body.toString().contains("Hello"));
                            context.assertEquals(200, response.statusCode());
                            context.assertEquals(response.headers().get("content-type"), "text/html");
                            async.complete();
                        });
                    });
        }
    }

    @Test
    public void testTemplating(TestContext context) {


            final Async async = context.async();

            vertx.createHttpClient().getNow(8080, "localhost", "/welcome",
                    response -> {
                        response.handler(body -> {
                            context.assertTrue(body.toString().contains("<h1>Good morning!</h1>"));
                            context.assertTrue(body.toString().contains("<title>Welcome</title>"));
                            context.assertEquals(200, response.statusCode());
                            context.assertEquals(response.headers().get("content-type"), "text/html");
                            async.complete();
                        });
                    });
    }

    @Test
    public void testMyApplication404(TestContext context) {
        final Async async = context.async();

        vertx.createHttpClient().getNow(8080, "localhost", "/foobar",
                response -> {
                    response.handler(body -> {
                        context.assertEquals(404, response.statusCode());
                        async.complete();
                    });
                });
    }


    @Test
    public void doTest(TestContext context) {
        context.assertEquals(1, 1);
    }
}
