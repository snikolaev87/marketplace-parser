package pro.snikolaev.marketplace.parser.server;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;

import pro.snikolaev.marketplace.parser.server.filters.ExceptionsFilter;
import pro.snikolaev.marketplace.parser.server.servlets.ParsingServlet;

public class ApplicationServer {

    public static void main(String[] args) throws ServletException {
        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(ApplicationServer.class.getClassLoader())
                .setContextPath("/action")
                .setDeploymentName("servlets.war")
                .addServlets(
                        Servlets.servlet("ParsingServlet", ParsingServlet.class).addMapping("/parse")
                )
                .addFilters(
                        Servlets.filter("ErrorHandler", ExceptionsFilter.class)
                )
                .addFilterUrlMapping("ErrorHandler", "/*", DispatcherType.REQUEST);

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();

        Undertow server = Undertow.builder()
                .addHttpListener(8090, "localhost")
                .setHandler(Handlers.path()
                        .addPrefixPath("/",
                                Handlers.resource(new ClassPathResourceManager(ApplicationServer.class.getClassLoader(), "html")).addWelcomeFiles("index.html"))
                        .addPrefixPath("/js",
                                Handlers.resource(new ClassPathResourceManager(ApplicationServer.class.getClassLoader(), "js")))
                        .addPrefixPath("/action", manager.start())
                )
                .build();

        server.start();
    }
}
