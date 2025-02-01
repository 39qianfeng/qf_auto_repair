package com.qianfeng.test;

import com.qianfeng.task.B;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Auther: qianfeng
 * @Date: 2024/11/20/23:50
 * @Description:
 */
@SpringBootTest
public class test {
    @Autowired
    private B b;
    @Test
    public void test_b_top100(){
        b.getTop100();
    }
}
