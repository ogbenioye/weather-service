package com.ogbenioye.weatherservice.utility;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateExtension {

    public static long toUnixTimestamp(LocalDateTime date)
    {
        return date.toEpochSecond(ZoneOffset.UTC);
    }
}
