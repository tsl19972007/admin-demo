package com.example.demo.model.query;

import lombok.Data;

/**
 * @author ：tsl
 * @date ：Created in 2020/6/10 13:35
 * @description：
 */

@Data
public class PageQuery {
    private int current;
    private int pageSize;
}
