package com.example.demo.service;

/**
 * @program: demo-unit-one
 * @description:
 * @author: Wyy
 * @create_time: 2021-03-15 16:32
 * @modifier：Wyy
 * @modification_time：2021-03-15 16:32
 **/
public interface Formula {
    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }}
