package demo.app.run;

import demo.app.init.WebAppInitializer;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.WebApplicationInitializer;

import java.util.concurrent.ConcurrentHashMap;

public class AppJettyRunner {

    public static void main(String[] args) throws Exception {
        new AppJettyRunner().startExploded();
    }

    private void startExploded() throws Exception {
        Server server = new Server(8080);
        server.setHandler(webAppContext());

        server.start();
        server.join();
    }

    private WebAppContext webAppContext() {
        WebAppContext context = new WebAppContext();
        context.setResourceBase("src/main/webapp");
        context.setContextPath("/");
        context.setParentLoaderPriority(true);
        context.setConfigurations(new Configuration[] { webAppAnnotationConfiguration() });
        return context;
    }

    private AnnotationConfiguration webAppAnnotationConfiguration() {
        return new AnnotationConfiguration() {
            @Override
            public void preConfigure(WebAppContext context) throws Exception {
                context.setAttribute(CLASS_INHERITANCE_MAP, webAppInitializerClassNameMap());
            }
        };
    }

    private ConcurrentHashMap<String, ConcurrentHashSet<String>> webAppInitializerClassNameMap() {
        ConcurrentHashSet<String> set = new ConcurrentHashSet<String>();
        set.add(WebAppInitializer.class.getName());

        ConcurrentHashMap<String, ConcurrentHashSet<String>> map = new ConcurrentHashMap<String, ConcurrentHashSet<String>>();
        map.put(WebApplicationInitializer.class.getName(), set);

        return map;
    }


}
