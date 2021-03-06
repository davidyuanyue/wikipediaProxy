

import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class WikiProxy {


    public static void main(String[] args) {

        Server server = new Server(8081);

        ServletContextHandler ctx =
                new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

        ctx.setContextPath("/");
        server.setHandler(ctx);


        ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/rest/*");
        serHol.setInitOrder(1);
        serHol.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature","true");

        serHol.setInitParameter("com.sun.jersey.config.property.packages",
                "endpoint");

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(WikiProxy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            server.destroy();
        }
    }
}