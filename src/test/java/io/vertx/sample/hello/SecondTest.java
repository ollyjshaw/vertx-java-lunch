
package io.vertx.sample.hello;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.Repeat;
import io.vertx.ext.unit.junit.RepeatRule;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class SecondTest {

    private Vertx vertx;

    @Rule
    public RepeatRule rule = new RepeatRule();

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

    @Repeat(100)
    @Test
    public void testMyApplication(TestContext context) {

        final Async async = context.async(1);
        vertx.createHttpClient().getNow(8080, "localhost", "/",
                response -> {
                    response.handler(body -> {
                        context.assertTrue(body.toString().contains("Hello"));
                        context.assertEquals(200, response.statusCode());
                        async.complete();
                    });
                });
        async.awaitSuccess();
    }

//    @Test
//    public void testMyApplication404(TestContext context) {
//        final Async async = context.async();
//
//        vertx.createHttpClient().getNow(8080, "localhost", "/foobar",
//                response -> {
//                    response.handler(body -> {
//                        context.assertEquals(404, response.statusCode());
//                        async.complete();
//                    });
//                });
//    }
//
//
//    @Test
//    public void doTest(TestContext context) {
//
//
//        System.out.print("!!!!!!!!!!!!!!!!!!!!!!");
//
//        context.assertEquals(1, 1);
//    }
}
