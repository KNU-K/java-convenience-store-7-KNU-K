package store.extension;

import java.time.*;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import store.common.utility.DateTimeGenerator;

public class FixedDateTimeExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        DateTimeGenerator.getInstance().enableFixedClockMode(LocalDate.of(2024,11,6));
    }
}