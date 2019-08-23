package com.robot.friday.service;

import com.robot.friday.integration.IflytekTtsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.util.Objects;

@Slf4j
@Service
public class SpeakService {
    private IflytekTtsClient iflytekTtsClient;

    @Autowired
    public SpeakService(IflytekTtsClient iflytekTtsClient) {
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
}
