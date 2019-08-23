package com.iflytek.view;

import com.iflytek.cloud.speech.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ListenService {

    //讯飞开放平台语音听写 appid
    private static String APPID = "5d5b6c2f";
    //语音听写对象
    private SpeechRecognizer mIat = SpeechRecognizer.createRecognizer();
    //标志是否已经开始录音
    private boolean startRecordFlag = false;

    public ListenService() {
        setting();
    }

    public static void main(String[] args) {
        new ListenService().startRecord();
    }

    /**
     * 开始录音
     */
    public void startRecord() {
        startRecordFlag = true;
        if (!mIat.isListening()) {
            mIat.startListening(recognizerListener);
        }
    }

    /**
     * 停止录音
     */
    public void stopRecord() {
        startRecordFlag = false;
        mIat.stopListening();
    }

    /**
     * 听写监听器
     */
    private RecognizerListener recognizerListener = new RecognizerListener() {
        private StringBuffer sb = new StringBuffer();
        /**
         * 获取听写结果. 获取RecognizerResult类型的识别结果
         */
        @Override
        public void onResult(RecognizerResult results, boolean islast) {
            sb.append(results.getResultString());
            if (islast) {
                System.out.println("result: " + sb.toString());
                sb.setLength(0);
                //开始新一轮语音识别
                if (startRecordFlag) {
                    mIat.stopListening();
                    mIat.startListening(recognizerListener);
                }
            }
        }

        @Override
        public void onError(SpeechError error) {
            System.out.println("onError enter");
            if (null != error) {
                System.out.print(" Code：" + error.getErrorDescription(true));
            }
            //出错直接重试，暂时不做什么处理，可能会有死循环的事情发生
//            if (startRecordFlag) {
//                mIat.stopListening();
//                mIat.startListening(recognizerListener);
//            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int agr2, String msg) {}

        @Override
        public void onVolumeChanged(int volume) {}

        @Override
        public void onBeginOfSpeech() {}

        @Override
        public void onEndOfSpeech() {}
    };

    /**
     * 初始化基本配置
     */
    private void setting() {
        //设置开发者信息 appid
        SpeechUtility.createUtility("appid=" + APPID);

        //配置参数
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "plain");//返回结果类型  {json， xml， plain}， 默认值：json

//        mIat.setParameter(SpeechConstant.NET_TIMEOUT, "20000");//网络连接超时时间，[0, 30000]，默认值：20000
//        mIat.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, "10000");//设置录取音频的最长时间，[0, 60000]，默认值：60000
//        mIat.setParameter(SpeechConstant.DOMAIN, "iat");//应用领域用于听写和语音语义服务，{ "iat"（短信和日常用语）, "video", "poi"（地图）, "music" }，默认值："iat"
        mIat.setParameter(SpeechConstant.VAD_BOS, "1000");//开始录入音频后，音频前面部分最长静音时长，[1000, 10000]， 默认值：5000
//        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");//语言，{ "zh_cn", "en_us" }， 默认值："zh_cn"
//        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");//语言区域 默认 mandarin：普通话 | cantonese：粤语 | lmz：四川话 | henanese：河南话
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");//后端点超时，开始录入音频后，音频后面部分最长静音时长，[0, 10000]， 默认值：1800
//        mIat.setParameter(SpeechConstant.SAMPLE_RATE, "16000");//识别采样率，{8000，16000}， 默认值：16000
//        mIat.setParameter(SpeechConstant.ASR_NBEST, "1");//句子级多候选，有歧义的句子有多少个候选结果 ，[1, 5]，默认值：1
//        mIat.setParameter(SpeechConstant.ASR_WBEST, "1");//词级多候选，有歧义的词有多少个候选结果 ，[1, 5]，默认值：1
//        mIat.setParameter(SpeechConstant.ASR_PTT, "1");//设置是否有标点符号， { null， 0， 1}， 默认值：1
//        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, null );//识别录音保存路径 当前识别支持未压缩的16位，单声道，采样率为16000或8000，字节顺序为 Little-Endian的Windows PCM音频。
    }

}
