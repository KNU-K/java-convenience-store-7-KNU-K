package store.extension;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import store.common.utility.DateTimeGenerator;

import java.time.LocalDate;

public class FixedDateTimeExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        DateTimeGenerator.getInstance().enableFixedClockMode(LocalDate.of(2024, 11, 10));
    }
}