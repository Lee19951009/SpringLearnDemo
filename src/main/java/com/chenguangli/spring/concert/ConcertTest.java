package com.chenguangli.spring.concert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConcertConfig.class)
public class ConcertTest {

    @Autowired
    private Performance performance;


    @Test
    public void test1() {
        performance.perform();
        Encoreable encoreable = (Encoreable) performance;
        encoreable.performEncore();
    }
}
