package com.pennywisenepal.financetracker.Entity;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class SortableWrapper {
    private LocalDate date;
    private Object item;

    public SortableWrapper(LocalDate date, Object item) {
        this.date = date;
        this.item = item;
    }

    public LocalDate getDate() {
        return date;
    }

    public Object getItem() {
        return item;
    }
}