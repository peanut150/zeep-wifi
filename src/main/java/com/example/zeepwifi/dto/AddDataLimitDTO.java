package com.example.zeepwifi.dto;

import lombok.Data;

@Data
public class AddDataLimitDto {
    public Integer client_id;
    public Integer package_id;
    public Integer value;
}