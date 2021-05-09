import client_server.database.dao.UserDao;
import client_server.database.models.User;
import freemarker.template.*;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.nio.*;
import org.jgrapht.nio.dot.*;
import org.jgrapht.traverse.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class DatabaseRepresentation {
    private final static String TEMPLATE_FOLDER_PATH = "template/";
    private final static String TEMPLATE_IN = "connection_representation.ftl";
    private final static String TEMPLATE_OUT = "index.html";

    Map<String, Object> data;

    public DatabaseRepresentation() {
        data = new HashMap<>();
    }

    public void init() {
        Graph<String, DefaultEdge> graph = generateSocialNetworkRepresentation();
        String graphRepresentation = DatabaseRepresentation.renderHrefGraph(graph);

        data.put("data", graphRepresentation);
    }

    public void export() {
        Version version = Configuration.VERSION_2_3_28;
        File file = new File(TEMPLATE_FOLDER_PATH);

        Configuration configuration = new Configuration(version);

        try {
            configuration.setDirectoryForTemplateLoading(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Template template = null;

        try {
            template = configuration.getTemplate(TEMPLATE_IN);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Writer fileWriter;

        try {
            fileWriter = new FileWriter(TEMPLATE_OUT);
            template.process(data, fileWriter);
            fileWriter.close();
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
    }

    private Graph<String, DefaultEdge> generateSocialNetworkRepresentation() {
        Graph<String, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);

        UserDao userDao = new UserDao();

        List<User> users = userDao.getAll();

        for (User user : users) {
            graph.addVertex(user.getUsername());
        }

        for (User user : users) {
            for (User friend : userDao.getFriends(user)) {
               graph.addEdge(user.getUsername(), friend.getUsername());
            }
        }

        return graph;
    }

    private static String renderHrefGraph(Graph<String, DefaultEdge> hrefGraph) throws ExportException {

        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>();
        exporter.setVertexAttributeProvider((v) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("user", DefaultAttribute.createAttribute(v));
            return map;
        });

        Writer writer = new StringWriter();
        exporter.exportGraph(hrefGraph, writer);

        return writer.toString();
    }
}
