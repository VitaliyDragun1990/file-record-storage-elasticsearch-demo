package com.vdrahun.filestorage.core.datetime.impl;

import com.vdrahun.filestorage.core.datetime.DateTimeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DateTimeServiceImpl implements DateTimeService {

    @Override
    public LocalDateTime currentDateTime() {
        return LocalDateTime.now();
    }

    @Override
    public LocalDate currentLocalDate() {
        return LocalDate.now();
    }
}
