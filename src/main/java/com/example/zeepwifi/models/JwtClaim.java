package com.example.zeepwifi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties
public class JwtClaim {
    public String accountUsername;

}