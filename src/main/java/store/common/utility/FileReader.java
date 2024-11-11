package store.common.utility;

import store.Application;
import store.common.constants.ErrorMessages;
import store.common.exception.InvalidFileException;

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
    private static final int HEADER_SKIP = 1;

    public static List<String[]> readMarkdownFile(String fileName) {
        try (InputStream inputStream = Application.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream), CHARSET))) {

            return reader.lines()
                    .skip(HEADER_SKIP)
                    .map(line -> line.split(DELIMITER))
                    .toList();

        } catch (Exception e) {
            throw new InvalidFileException(ErrorMessages.INVALID_FILE);
        }
    }
}
