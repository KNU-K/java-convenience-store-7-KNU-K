package store.common.utility;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;

public class DateTimeGenerator {
    private static DateTimeGenerator instance;
    private boolean isFixedClockEnabled = false;
    private LocalDate fixedDate;

    private DateTimeGenerator() {
    }

    public static synchronized DateTimeGenerator getInstance() {
        if (instance == null) {
            instance = new DateTimeGenerator();
        }
        return instance;
    }

    public LocalDate now() {
        if (isFixedClockEnabled) {
            return fixedDate;
        }
        return DateTimes.now().toLocalDate();
    }

    public void enableFixedClockMode(LocalDate fixedDate) {
        this.fixedDate = fixedDate;
        this.isFixedClockEnabled = true;
    }

    public void disableFixedClockMode() {
        this.isFixedClockEnabled = false;
    }
}
