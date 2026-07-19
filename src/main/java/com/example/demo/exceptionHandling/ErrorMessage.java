package com.example.demo.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private Date timestamp;
    private String message;}
