package com.zmy.demo.util;

import java.util.*;

/**
 * 分页对象
 */
@lombok.Data public class Page {
    private int total;                      // 数据总条数
    private List<? extends Object> list;    // 当前页的数据

    public Page(int total, List<? extends Object> list) {
        this.total = total;
        this.list = list;
    }

    /**
     * 根据当前页currentPage与每页最大数据数pagesize计算当前页第一条数据的索引
     * (第一页第一条数据的索引为0)
     * @param currentPage 当前页(从1开始)
     * @param pagesize 每页的最大数据条数
     * @return
     */
    public static int getStartIndex(int currentPage, int pagesize) {
        return (currentPage - 1) * pagesize;
    }
}
