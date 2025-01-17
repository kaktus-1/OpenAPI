package pl.nightdev701.http;

import pl.nightdev701.logger.AbstractLogger;
import pl.nightdev701.logger.standard.DefaultLogger;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;

public class HttpRequestHandler {

    private final String url;
    private final AbstractLogger logger;
    private String body;

    public HttpRequestHandler(String url) {
        this.logger = new DefaultLogger();
        this.url = url;
    }

    public HttpRequestHandler(String url, AbstractLogger logger) {
        this.url = url;
        this.logger = logger;
    }

    /**
     * fire request
     */
    public void request() {
        logger.log(Level.INFO, "Send request to " + url);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            this.body = response.body();

            logger.log(Level.INFO, "Request send");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Request failed");
        }
    }

    /**
     * Download file
     */
    public void download(String output) {

        String link = url;

        logger.log(Level.INFO, "Download file to path: " + output);

        URL url;
        try {
            url = new URL(link);
            try (
                    InputStream inputStream = url.openStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    FileOutputStream fileOutputStream = new FileOutputStream(output)
            ) {
                byte[] bucket = new byte[2048];
                int numBytesRead;

                while ((numBytesRead = bufferedInputStream.read(bucket, 0, bucket.length)) != -1) {
                    fileOutputStream.write(bucket, 0, numBytesRead);
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "Download failed: " + e.getMessage());
            }
            logger.log(Level.INFO, "Download complete!");
        } catch (MalformedURLException e) {
            logger.log(Level.WARNING, "Download failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * read body
     */
    public String getBody() {
        return body;
    }

}
