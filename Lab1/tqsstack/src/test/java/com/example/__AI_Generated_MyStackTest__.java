package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

class __AI_Generated_MyStackTest__ {

    private MyStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new MyStack<>();
    }

    @Test
    void testNewStackIsEmpty() {
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void testPushIncreasesSize() {
        stack.push(10);
        stack.push(20);
        assertEquals(2, stack.size());
    }

    @Test
    void testPopReturnsLastPushedElement() {
        stack.push(5);
        stack.push(15);
        assertEquals(15, stack.pop());
        assertEquals(5, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPeekReturnsTopElementWithoutRemovingIt() {
        stack.push(42);
        assertEquals(42, stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    void testPopFromEmptyStackThrowsException() {
        assertThrows(NoSuchElementException.class, stack::pop);
    }

    @Test
    void testPeekFromEmptyStackThrowsException() {
        assertThrows(NoSuchElementException.class, stack::peek);
    }

    @Test
    void testBoundedStackPreventsOverflow() {
        MyStack<Integer> boundedStack = new MyStack<>(2);
        boundedStack.push(1);
        boundedStack.push(2);
        assertThrows(IllegalStateException.class, () -> boundedStack.push(3));
    }

    @Test
    void testMultiplePushAndPop() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }
}