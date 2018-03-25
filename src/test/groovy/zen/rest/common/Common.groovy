package zen.rest.common

import com.jayway.restassured.RestAssured
import rest.jetty.JettyRunner
import spock.lang.Shared

class Common {
    def static LOG_ENABLED = true


    @Shared
    static JettyRunner jettyRunner = null;

    //TODO to set as false before commit on SVN!
    static final boolean enablePrintln = false

    public static void log(String format, Object... args) {
        if (enablePrintln) println(String.format(format, args))
    }

    public static void startRestServer() throws Exception {
        if (jettyRunner == null) {
            jettyRunner = new JettyRunner();
            System.out.println("-----------------------------new JettyRunner------");
            Thread t = new Thread(jettyRunner);
            t.start();
            jettyRunner.waitUntilStarted();
            /*
             When rest server started by JettyRunner e.g. http://localhost:11080/zen
             */
            RestAssured.baseURI = "http://localhost:" + jettyRunner.jettyPort + "/" + jettyRunner.context;

            println("------------running----------------JettyRunner...on port:${jettyRunner.jettyPort}.....");
            println("------------RestAssured.baseURI ----------------${RestAssured.baseURI}.....");
        }
    }

    public static void stopRestServer() throws Exception {

        if (jettyRunner != null) {
            int port = jettyRunner.jettyPort;
            jettyRunner.stop();
            jettyRunner = null;
            println("------------stopped----------------JettyRunner: $port ........");
        }
    }

    public static void printRestJson(String path) {
        def requestGet = RestAssured.given()
                .contentType("application/json")
                .header("Accept", "application/json")
        def response = RestAssured.given().spec(requestGet).get(path)
        def body = response.body.jsonPath()
        log "$body.prettify()"
//        body.prettyPrint()
    }

    static def logHeader(str = "!!!") {
        if (LOG_ENABLED)
            println "-" * 40 + "-->" + str + "-" * 40
    }

    static def log(str = "!!!") {
        if (LOG_ENABLED)
            println "-" * 80 + "\n-->" + str + "\n" + "-" * 80
    }


}
