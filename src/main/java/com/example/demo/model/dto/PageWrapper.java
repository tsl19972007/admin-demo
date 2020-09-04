package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/6/10 13:04
 * @description：
 */

@Data
@AllArgsConstructor
public class PageWrapper<T> {

    private List<T> data;

    private long total;

    private int current;

    private int pageSize;
}
