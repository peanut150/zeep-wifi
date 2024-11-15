package com.example.zeepwifi.dto;

public class DataLimitDTO {
    public Long counter;
    public String csrf_token;
    public Integer limit_count;
    public String limit_type;

    public DataLimitDTO(Long counter, String csrf_token, Integer limit_count, String limit_type) {
        this.counter = counter;
        this.csrf_token = csrf_token;
        this.limit_count = limit_count;
        this.limit_type = limit_type;
    }
}
