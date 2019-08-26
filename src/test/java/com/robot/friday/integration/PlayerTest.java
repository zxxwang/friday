package com.robot.friday.integration;

import com.robot.friday.FridayApplication;
import com.robot.friday.service.Mouth;
import javazoom.jl.decoder.JavaLayerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FridayApplication.class)
public class PlayerTest {

    @Autowired
    private KugouMusicClient kugouMusicClient;
    @Autowired
    private Mouth mouth;


    @Test
    public void kugouTest() throws IOException, JavaLayerException, URISyntaxException {
//        String ul = "https://webfs.yun.kugou.com/201908231527/faba3be2493f2a4d02d90dbfb1d04346/G152/M08/08/19/2A0DAF0J98-ACcE3ADW6ozh1e1o954.mp3";
//        Response o = kugouClient.play(new URI(ul));
//        new Player(new BufferedInputStream(o.body().asInputStream())).play();
//        kugouMusicClient.kugouSongSearch("模特");
        mouth.sing("模特");
    }
}
