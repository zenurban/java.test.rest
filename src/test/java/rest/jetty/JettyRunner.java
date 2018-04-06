package rest.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
//import org.springframework.web.context.ContextLoaderListener;

import java.io.IOException;
import java.net.ServerSocket;

import static zen.common.Constant.REST_CONTEXT_ROOT;

public class JettyRunner implements Runnable, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(JettyRunner.class);

    public static final String REST_PACKAGE_2SCAN = "zen.rest";
    private static final int JETTY_DEFAULT_PORT = 11080;
    //private static final String CONTEXT = "cz.csas.inet.service.tpprep.dao.mapper";
    private static Server server;
    public int jettyPort = JETTY_DEFAULT_PORT;
    public String context = REST_CONTEXT_ROOT;

    public JettyRunner() throws Exception {
        this(findFreePort(), REST_CONTEXT_ROOT);
    }

    public JettyRunner(int jettyPort, String context) throws Exception {
        this.jettyPort = jettyPort;
        this.context = context;
    }

    /**
     * Find a free server port.
     *
     * @return port number.
     */
    public static int findFreePort() throws IOException {
        ServerSocket server = new ServerSocket(0);
        int port = server.getLocalPort();
        server.close();
        return port;
    }

    public JettyRunner(int jettyPort) throws Exception {
        this(jettyPort, REST_CONTEXT_ROOT);
    }

    public static void main(String[] args) throws Exception {
        new JettyRunner(JETTY_DEFAULT_PORT).runStandAloneJettyServer();
    }

    public void runStandAloneJettyServer() throws Exception {

/*
        Resource resource = new ClassPathResource("Configuration.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        // Setting two active spring profiles, notRunningInGrid and onsite/cloud
        String profile = props.getProperty("spring.profile");
        System.out.println("-->Spring profile: " + profile);
*/
        //SpringProfile.setActiveProfiles(profile, SpringProfile.PROFILE_RUNNING_STANDALONE);
        //MapperApplicationContext.Instance.initialize();

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setContextPath("/" + REST_CONTEXT_ROOT);

        ServletHolder jerseyServlet = servletContextHandler.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        // jerseyServlet.setInitParameter("com.sun.jersey.config.property.resourceConfigClass",
        // "com.sun.jersey.service.core.PackagesResourceConfig");
        // jerseyServlet.setInitParameter("com.sun.jersey.config.property.packages",
        // "com.infor.ion.desk.newoneview.rest.handler");
        jerseyServlet.setInitParameter("useFileMappedBuffer", "false");
        // jerseyServlet.setInitParameter("com.sun.jersey.service.json.POJOMappingFeature", "true");

        server = new Server(jettyPort);

        // add Spring DI
        //servletContextHandler.addEventListener(new ContextLoaderListener());
        //servletContextHandler.setInitParameter("contextConfigLocation", "classpath:applicationContext.xml");
        // contextHandler.setInitParameter("contextConfigLocation", "classpath*:**/WEB-INF/applicationContextRest.xml");

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", REST_PACKAGE_2SCAN);
        //jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", RestServiceDuplicate.class.getCanonicalName());

        // Add the handlers to the server.
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{
                servletContextHandler, getFileResourceHandler()
        });

        server.setHandler(handlers);

        String sep = "\n_______________________________________________________________\n" ;
        log("{}Starting Jetty server at port: {}\n" +
                        "-->To get data use: localhost:{}/{}/calc/abs(-2)^(2*2) + abs(-4*2) + sizeof'This sentence has 26 chars' {}",
                sep, jettyPort, jettyPort, context,  sep);
        try {
            server.start();
            server.join();
        } finally {
            close();
        }
    }


    /**
     * Create the ResourceHandler.
     * load default root resource (index.html)
     * It is the object that will actually handle the request for a given file.
     * It is a Jetty Handler object so it is suitable for chaining with other handlers.
     * <p/>
     * Configure the ResourceHandler. Setting the resource base indicates where the files should be served out of.
     *
     * @return Handler
     */
    private Handler getFileResourceHandler() {
        ResourceHandler fileResourceHandler = new ResourceHandler();

        fileResourceHandler.setDirectoriesListed(true);
        fileResourceHandler.setResourceBase(".");
        fileResourceHandler.setWelcomeFiles(new String[]{"index.html", "demoSearchAngularJS.html"});

        return fileResourceHandler;
    }

    public static void log(String msg, Object... objs) {
        System.out.println(MessageFormatter.arrayFormat(msg, objs).getMessage());
    }

    @Override
    public void close() throws Exception {
        destroy();
    }

    public void destroy() throws InterruptedException {
        if (server != null) {
            stop();
            while (server.isStopping()) {
                Thread.sleep(100);
            }
            server.destroy();
            server = null;
            LOG.info("\n------------Jetty server(port:" + jettyPort + " ) stopped and destroyed--------------");
        }
    }

    public void stop() {
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }
    }

    public void waitUntilStarted() {
        while (server == null || !server.isStarted()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        try {
            runStandAloneJettyServer();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

}
