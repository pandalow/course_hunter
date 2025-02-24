package com.hunt.entity;

import lombok.Data;

@Data
public class ResultItem {
    private Long id;
    private String title;
    @Override
    public String toString() {
        return "ResultItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
