package store.common.utility;

import store.Application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class FileReader {

    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final String DELIMITER = ",";

    public static List<String[]> readMarkdownFile(String fileName) {
        try (InputStream inputStream = Application.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream), CHARSET))) {

            return reader.lines()
                    .skip(1)
                    .map(line -> line.split(DELIMITER))
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException("Failed to read file: " + fileName, e);
        }
    }
}
