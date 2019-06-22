package com.longhai.moviesservice.processor;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataLoaderTest {
    @Test
    public void testLoadData() throws Exception {
        DataLoader dataLoader = new DataLoader();
        String str = dataLoader.readAllExample();

    }
}