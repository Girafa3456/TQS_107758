package com.example;

import java.util.LinkedList;

public class MyStack<T> {
    private LinkedList<T> collection;
    private int maxSize;

    public MyStack(){
        collection = new LinkedList<>();
        maxSize = 0;        // default
    }

    public void push(T item){
        collection.push(item);
    }

    public T pop(){
        return null;
    }

    public T peek(){
        return null;
    }

    public int size(){
        return 0;
    }

    public boolean isEmpty(){
        return true;
    }

}
