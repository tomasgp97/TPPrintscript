package url;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.stream.Stream;

public interface UrlSolver {

    Stream<Character> downloadFile(String url) throws IOException;
}
