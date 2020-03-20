package com.robot.friday.service;

import com.robot.friday.integration.IflytekTtsClient;
import com.robot.friday.integration.KugouMusicClient;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Objects;

@Slf4j
@Service
public class Mouth {
    private KugouMusicClient kugouMusicClient;
    private IflytekTtsClient iflytekTtsClient;

    @Autowired
    public Mouth(KugouMusicClient kugouMusicClient, IflytekTtsClient iflytekTtsClient) {
        this.kugouMusicClient = kugouMusicClient;
        this.iflytekTtsClient = iflytekTtsClient;
    }

    public void speak(String words) {
        SourceDataLine sourceDataLine = null;
        try {
            // 获取音频输入流
            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(iflytekTtsClient.read(words));
            // 获取音频编码对象
            AudioFormat audioFormat = audioInputStream.getFormat();
            // 设置数据输入
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
                    audioFormat, AudioSystem.NOT_SPECIFIED);

            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();
            // 从输入流中读取数据发送到混音器
            int count;
            byte tempBuffer[] = new byte[1024];
            while ((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
                if (count > 0) {
                    sourceDataLine.write(tempBuffer, 0, count);
                }
            }
        } catch (Exception e) {
            log.error("音频流朗读失败, {}", e);
        } finally {
            Objects.requireNonNull(sourceDataLine).drain();
            sourceDataLine.close();
        }
    }

    public void sing(String music, String singer) {
        try {
            InputStream song = kugouMusicClient.play(music, singer);
            if (Objects.nonNull(song)) {
                new Player(song).play();
            } else {
                speak("由于版权原因，该歌曲无法播放");
            }
        } catch (JavaLayerException e) {
            log.error("音乐播放失败, {}", e);
        }
    }
}
