package com.xoptimal.interaction.entity;

/**
 * Created by Freddie on 2018/2/24 0024 .
 * Description:
 */
public interface IResult<T> {

    int getCode();

    String getMessage();

    T getResult();
}
