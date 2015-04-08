package com.zts1993.gse.counter;

import com.zts1993.gse.index.InvertedIndexThreadSemaphore;
import org.junit.Test;

public class InvertedIndexThreadSemaphoreTest {



    @Test
    public void testSum() throws Exception {
        InvertedIndexThreadSemaphore.sum();
    }

    @Test
    public void testIncr() throws Exception {
        InvertedIndexThreadSemaphore.incr();
    }

    @Test
    public void testDecr() throws Exception {
        InvertedIndexThreadSemaphore.decr();
    }
}