package com.chenguangli.base.clone;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author chenguangli
 * @date 2020/4/10 20:51
 */
@Data
@AllArgsConstructor
public class Student implements Cloneable{

    private int a;
    private String b;

    @Override
    protected Student clone() throws CloneNotSupportedException {
        return (Student)super.clone();
    }
}
