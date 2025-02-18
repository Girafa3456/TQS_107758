package com.example;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MyStack<T> {
    private LinkedList<T> collection;
    private int maxSize;

    public MyStack(){
        this.collection = new LinkedList<>();
        this.maxSize = 0;
    }

    public MyStack(int maxSize){
        this.collection = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public void push(T item){
        if ((maxSize != 0) && (collection.size() >= maxSize)){
            throw new IllegalStateException("Stack is full");
        }
        collection.push(item);
    }

    public T pop(){
        if (collection.isEmpty()){
            throw new NoSuchElementException("Stack is empty");
        }
        return collection.pop();
    }

    public T peek(){
        if (collection.isEmpty()){
            throw new NoSuchElementException("Stack is empty");
        }
        return collection.peek();
    }

    public int size(){
        return collection.size();
    }

    public boolean isEmpty(){
        return collection.isEmpty();
    }
    
    public T popTopN(int n){
        // so it doesnt fail despite the high coverage level
        if (n > collection.size()){
            throw new NoSuchElementException("Not enought elements to remove");
        }

        T top = null;
        for (int i = 0; i < n; i++){
            top = collection.removeFirst();
        }
        return top;
    }

}
