package url;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class UrlSolverImpl implements UrlSolver {

    public UrlSolverImpl() {}

    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    private static final String RESOURCES_PATH = "lexer/src/main/java/url/resources";

    @Override
    public Stream<Character> downloadFile(String url) throws IOException {
       String fileName = RESOURCES_PATH + url; //mmmm
        FileUtils.copyURLToFile(
                //todo test file
                new URL(url),
                new File(fileName),
                CONNECTION_TIMEOUT,
                READ_TIMEOUT);

        return parseFileToString(fileName);
    }

    private Stream<Character> parseFileToString(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> content.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString().chars().mapToObj(i -> (char) i);
    }
}
