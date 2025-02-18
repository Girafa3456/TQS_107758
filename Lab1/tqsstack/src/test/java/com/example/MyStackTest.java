package com.example;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

/**
 * Unit test for simple App.
 */
public class MyStackTest
{
    private MyStack<Integer> stack;

    @BeforeEach
    void setUp(){
        stack = new MyStack<>();
    }

    // A stack is empty on construction
    @Test
    void emptyOnContruction(){
        assert(stack.isEmpty());
    }

    // A stack has size 0 on construction
    @Test
    void nullSizeOnConstruction(){
        assertEquals(0, stack.size());
    }

    // After n > 0 pushes to an empty stack, the stack is not empty 
    // and its size is n
    @Test
    void notEmptyNorNullSizeAfterPush(){
        stack.push(100);
        stack.push(200);
        stack.push(500);
        assertFalse(stack.isEmpty());
        assertEquals(3, stack.size());
    }

    // If one pushes x then pops, the value popped is x.
    @Test
    void xPopAfterPush(){
        stack.push(7);
        assertEquals(7, stack.pop());
        assertTrue(stack.isEmpty());
    }

    // If one pushes x then peeks, the value returned is x, but the  
    // size stays the same
    @Test
    void xStaysInStackAfterPeek(){
        stack.push(500);
        int initial_size = stack.size();
        assertEquals(500, stack.peek());
        assertEquals(initial_size, stack.size());
    }


    // If the size is n, then after n pops, the stack is empty and has a 
    // size 0
    @Test
    void stackEmptyAfterNPushesAndNPops(){
        stack.push(1000);
        stack.push(1001);
        stack.push(1002);
        stack.push(1003);
        stack.push(1004);
        stack.push(1005);
        for (int i = 0; i < 6; i++){
            stack.pop();
        }
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    // Popping from an empty stack throws a NoSuchElementException
    @Test
    void NoSuchElementExceptionOnEmptyStackAfterPop(){
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    // Peeking into an empty stack throws a NoSuchElementException
    @Test
    void NoSuchElementExceptionOnEmptyStackAfterPeek(){
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }

    // For bounded stacks only, pushing onto a full stack throws 
    // an IllegalStateException
    @Test
    void IllegalStateExceptionOnFullBoundedStackAfterPush(){
        // redefine the stack as bounded
        stack = new MyStack<>(2);
        stack.push(100);
        stack.push(200);
        assertThrows(IllegalStateException.class, () -> stack.push(300));
    }

    @Test
    void popTopNTest1(){
        stack.push(1);
        stack.push(3);
        stack.push(5);
        stack.push(7);
        stack.push(9);
        Integer initial_size = stack.size();
        Integer lastPopped = stack.popTopN(3);

        assertEquals(initial_size - 3, stack.size());

        assertEquals(5, lastPopped);
    
        assertEquals(3, stack.peek());
    } 

    @Test
    void popTopNTest2(){
        stack.push(50);
        stack.push(100);

        Integer lastPop = stack.popTopN(2);

        assertTrue(stack.isEmpty());
        assertEquals(50, lastPop);
    }

    @Test
    void popTopNTestMoreThanSizeException(){
        stack.push(100);
        stack.push(200);

        assertThrows(NoSuchElementException.class, () -> stack.popTopN(3));
    }
}
