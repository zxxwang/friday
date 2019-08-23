package com.robot.friday.integration;

import com.robot.friday.FridayApplication;
import com.robot.friday.service.SpeakService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FridayApplication.class)
public class Text2SoundTest {

    @Autowired
    private SpeakService speakService;

    @Test
    public void saySomething() {
        speakService.speak("Now that we know the basic volume-related Docker commands, let’s see how we use them with containers and services.\n");
    }
}
