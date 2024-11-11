package store.common.utility;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;

public class DateTimeGenerator {
    private static DateTimeGenerator instance;
    private boolean isFixedClockEnabled = false;
    private LocalDate fixedDate;

    private DateTimeGenerator() {
    }

    // 3. 인스턴스를 반환하는 public 메서드 (Lazy initialization)
    public static synchronized DateTimeGenerator getInstance() {
        if (instance == null) {
            instance = new DateTimeGenerator();
        }
        return instance;
    }

    // 예시로 날짜를 반환하는 메서드
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

    // 고정 클럭 모드를 비활성화하는 메서드
    public void disableFixedClockMode() {
        this.isFixedClockEnabled = false;
    }
}
