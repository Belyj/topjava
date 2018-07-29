package ru.javawebinar.topjava.service;

import org.junit.*;
import org.junit.rules.*;
import org.junit.runner.*;
import org.slf4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
public class ServiceTest {

    static final Logger log = getLogger("result");
    static StringBuilder results = new StringBuilder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result);
            log.info(result + " ms\n");
        }
    };

    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

    @AfterClass
    public static void printResult() {
        log.info("\n---------------------------------" +
                         "\nTest                 Duration, ms" +
                         "\n---------------------------------" +
                         results +
                         "\n---------------------------------");
    }
}