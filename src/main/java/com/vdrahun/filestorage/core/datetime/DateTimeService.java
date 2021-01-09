package com.vdrahun.filestorage.core.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DateTimeService {

    LocalDateTime currentDateTime();

    LocalDate currentLocalDate();
}
