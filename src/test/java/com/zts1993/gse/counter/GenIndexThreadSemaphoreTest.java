package com.zts1993.gse.counter;

import org.junit.Test;

public class GenIndexThreadSemaphoreTest {



    @Test
    public void testSum() throws Exception {
        GenIndexThreadSemaphore.sum();
    }

    @Test
    public void testIncr() throws Exception {
        GenIndexThreadSemaphore.incr();
    }

    @Test
    public void testDecr() throws Exception {
        GenIndexThreadSemaphore.decr();
    }
}