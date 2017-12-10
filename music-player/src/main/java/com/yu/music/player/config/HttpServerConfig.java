package com.yu.music.player.config;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.ipc.netty.http.server.HttpServer;


/**
 * Created by yuliu on 2017/12/7 0007.
 */
@Configuration
public class HttpServerConfig {
    @Autowired
    private Environment environment;


    @Bean
    public Tomcat tomcatServer(RouterFunction<ServerResponse> route) throws LifecycleException {
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
        Tomcat tomcatServer = new Tomcat();
        tomcatServer.setHostname("server.ip");
        tomcatServer.setPort(Integer.valueOf(environment.getProperty("server.tomcat.port")));
        Context rootContext = tomcatServer.addContext("", System.getProperty("java.io.tmpdir"));
        ServletHttpHandlerAdapter servlet = new ServletHttpHandlerAdapter(httpHandler);
        Tomcat.addServlet(rootContext, "httpHandlerServlet", servlet);
        rootContext.addServletMappingDecoded("/", "httpHandlerServlet");
        tomcatServer.start();
        return tomcatServer;
    }

    @Bean
    public HttpServer httpServer(RouterFunction<ServerResponse> route) {
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create("localhost", Integer.valueOf(environment.getProperty("server.netty.port")));
        server.newHandler(adapter).block();
        return server;
    }
}
