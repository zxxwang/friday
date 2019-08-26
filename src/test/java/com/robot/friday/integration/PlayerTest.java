package com.robot.friday.integration;

import com.robot.friday.FridayApplication;
import com.robot.friday.service.Mouth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FridayApplication.class)
public class PlayerTest {

    @Autowired
    private KugouMusicClient kugouMusicClient;
    @Autowired
    private Mouth mouth;


    @Test
    public void kugouTest() {
        mouth.sing("模特");
    }
}
