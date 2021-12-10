package core.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Utils for work with dates
 * <p>
 *
 * @author Denis.Martynov
 * Created on 27.04.21
 */
public final class DateUtils {

    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

    private DateUtils() {
    }

    public static Instant parseInstant(final Instant instant, final String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime updatedAt = LocalDateTime.ofInstant(instant, ZONE_OFFSET);
        String updatedAtStr = updatedAt.format(dateTimeFormatter);
        LocalDateTime parsedUpdatedAt = LocalDateTime.parse(updatedAtStr, dateTimeFormatter);
        return parsedUpdatedAt.toInstant(ZONE_OFFSET);
    }

    public static Long getDifferenceBetweenTimezones(ZoneOffset firstZone, ZoneOffset secondZone) {
        LocalDateTime nowDate = LocalDateTime.now();
        ZonedDateTime fromZonedDateTime = nowDate.atZone(firstZone);
        ZonedDateTime toZonedDateTime = nowDate.atZone(secondZone);
        return Duration.between(fromZonedDateTime, toZonedDateTime)
                .toMillis();
    }

    public static Instant getNowWithDifferenceBetweenTimezones(final String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime now = LocalDateTime.now(ZONE_OFFSET);
        String formattedNow = now.format(dateTimeFormatter);
        LocalDateTime parsedFormattedNow = LocalDateTime.parse(formattedNow, dateTimeFormatter);
        Instant instantNow = parsedFormattedNow.toInstant(ZONE_OFFSET);

        Long differenceBetweenTimezones = getDifferenceBetweenTimezones(ZoneOffset.UTC, ZonedDateTime.now().getOffset());
        return instantNow.plusMillis(differenceBetweenTimezones);
    }
}
