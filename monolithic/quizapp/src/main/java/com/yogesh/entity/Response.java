package com.yogesh.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response {
    private Integer id; // to store question id
    private String response; // to store user selected answer for the question
}
