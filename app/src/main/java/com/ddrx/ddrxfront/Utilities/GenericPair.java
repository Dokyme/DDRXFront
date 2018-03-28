package com.ddrx.ddrxfront.Utilities;

/**
 * Created by vincentshaw on 2018/3/27.
 */

public class GenericPair<E extends Object, F extends Object> {
    private E first;
    private F second;

    public GenericPair(){

    }

    public E getFirst() {
        return first;
    }
    public void setFirst(E first) {
        this.first = first;
    }
    public F getSecond() {
        return second;
    }
    public void setSecond(F second) {
        this.second = second;
    }


}