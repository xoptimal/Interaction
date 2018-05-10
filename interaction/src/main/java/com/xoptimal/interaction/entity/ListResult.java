package com.xoptimal.interaction.entity;

import java.util.List;

/**
 * Created by Freddie on 2018/2/24 0024 .
 * Description:
 */
public class ListResult {

    private boolean hasLoadMore;

    private List<Object> lists;

    public ListResult(List<Object> lists, boolean hasLoadMore) {
        this.hasLoadMore = hasLoadMore;
        this.lists = lists;
    }

    public List<Object> getLists() {
        return lists;
    }

    public boolean hasLoadMore() {
        return hasLoadMore;
    }
}

