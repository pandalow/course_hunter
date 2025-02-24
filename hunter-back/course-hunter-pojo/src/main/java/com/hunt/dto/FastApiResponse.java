package com.hunt.dto;

import com.hunt.entity.ResultItem;
import lombok.Data;

import java.util.List;

@Data
public class FastApiResponse {
    private String query;
    private List<ResultItem> results;
    
    @Override
    public String toString() {
        return "FastApiResponse{" +
                "query='" + query + '\'' +
                ", results=" + results +
                '}';
    }
}
