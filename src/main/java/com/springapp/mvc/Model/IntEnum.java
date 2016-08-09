package com.springapp.mvc.Model;

/**
 * Created by Administrator on 2015/11/10.
 */
public interface IntEnum<E extends Enum<E>> {
    int getIntValue();
}
