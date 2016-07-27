package com.advantage.order.store.model;

import java.util.EmptyStackException;


/**
 * See feature 1779 - Order History (Web Only)
 * @author Binyamin Regev on on 27/07/2016.
 */
public class Feature1779OrdersHistory {

    private static final int KB = 1024; //  1KB

    private class Feature1779Stack {
        private Object[] elements;
        private int size = 0;

        /**
         * Default Constructor: Create the stack with space for 1024 elements.
         */
        public Feature1779Stack() {
            elements = new Object[KB];
        }

        /**
         * Create the stack with space for {@code initialCapacity} elements.
         * @param initialCapacity
         */
        public Feature1779Stack(int initialCapacity) {
            elements = new Object[initialCapacity];
        }

        /**
         * Add an an element into the <i>Stack</i>.
         * @param object
         */
        public void push(Object object) {
            ensureCapacity();
            elements[size++] = object;
        }

        /**
         * Remove an element from the <i>Stack</i>.
         * @return
         */
        public Object pop() {
            if (size == 0) throw new EmptyStackException();
            return elements[--size];
        }

        private void ensureCapacity() {
            if (elements.length == size) {
                Object[] oldElements = elements;
                elements = new Object[2 * elements.length + 1];
                System.arraycopy(oldElements, 0, elements, 0, size);
            }
        }

    }

    /**
     * This is the element
     */
    private class Feature1779Element {
        private char[] chars = new char[KB];
    }

    private static int finalizeCount = 0;
    //int[] val = new int[KB];  //  1K

    public Feature1779OrdersHistory() {
    }

    public Feature1779OrdersHistory(int initialCapacity) {
        Feature1779Stack stack = new Feature1779Stack(initialCapacity);
    }


    public void finalize()
    {
        try {
            super.finalize();
            finalizeCount++;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * An example to run {@link Feature1779OrdersHistory} which creates a <i>Memory Leak</i>.
     * @param args
     */
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        System.out.println("Total memory: " + runtime.totalMemory() + ", " +
                "free memory: " + runtime.freeMemory());

        for (int i = 0; i < 1000; i++) {
            new Feature1779OrdersHistory();
        }
        System.out.println("Number of times finalize executed: " + Feature1779OrdersHistory.finalizeCount);
        System.out.println("Total memory: " + runtime.totalMemory() + ", " +
                "free memory: " + runtime.freeMemory());

        //for (int i = 0; i < 1000; i++) { new Feature1779OrdersHistory(); }
        //System.out.println("Number of times finalize executed: " +
        //        Feature1779OrdersHistory.finalizeCount);
    }
}
