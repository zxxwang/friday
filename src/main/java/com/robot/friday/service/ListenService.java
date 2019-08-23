package com.robot.friday.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sound.sampled.*;

@Slf4j
@Service
public class ListenService {

    static volatile boolean stop=false;
    public static void main(String[] args) {
        Play();
    }
    //播放音频文件
//    @PostConstruct
    public static void Play() {

        try {
            AudioFormat audioFormat =
                    new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100F,
                    8, 1, 1, 44100F, false);
//                    new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,44100F, 16, 2, 4,
//                            44100F, true);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
            TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
            targetDataLine.open(audioFormat);
            final SourceDataLine sourceDataLine;
            info = new DataLine.Info(SourceDataLine.class, audioFormat);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceDataLine.open(audioFormat);
            targetDataLine.start();
            sourceDataLine.start();
            FloatControl fc=(FloatControl)sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
            double value=2;
            float dB = (float)
                    (Math.log(value)/Math.log(10.0)*20.0);
            fc.setValue(dB);
            int nByte = 0;
            final int bufSize=40960 ;
            byte[] buffer = new byte[bufSize];
            int i = 0;
            while (nByte != -1) {
                nByte = targetDataLine.read(buffer, 0, bufSize);
                sourceDataLine.write(buffer, 0, nByte);
                System.out.println(++i);
            }
            sourceDataLine.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
