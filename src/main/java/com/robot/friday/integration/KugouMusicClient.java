package com.robot.friday.integration;

import com.google.common.collect.ImmutableMap;
import com.robot.friday.StringUtils;
import com.robot.friday.integration.response.KugouSearchResponse;
import feign.Feign;
import feign.Response;
import feign.Target;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
@Import(FeignClientsConfiguration.class)
public class KugouMusicClient {

    private KugouClient kugouClient;

    @Autowired
    public KugouMusicClient() {
        kugouClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(Target.EmptyTarget.create(KugouClient.class));
    }

    public InputStream play(String music) {
        try {
            String musicAddress = this.kugouSongSearch(music);
            if (musicAddress != null) {
                return kugouClient.play(new URI(musicAddress)).body().asInputStream();
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String kugouSongSearch(String songName) {
        try {
            ImmutableMap<String, String> param = new ImmutableMap.Builder<String, String>()
                    .put("keyword", songName)
                    .put("page", Integer.toString(1))
                    .put("pagesize", Integer.toString(5))
                    .build();
            URI uri = new URI(StringUtils.urlBuilder("https://songsearch.kugou.com/song_search_v2", param));
            KugouSearchResponse response = kugouClient.searchSong(uri);
            return kugouPlayUrl(response.getData()
                    .getLists()
                    .get(0)
                    .getFileHash());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String kugouPlayUrl(String hash) {
        try {
            ImmutableMap<String, String> param = new ImmutableMap.Builder<String, String>()
                    .put("r", "play/getdata")
                    .put("hash", hash)
                    // TODO: 2019/8/26 如何更新mid 
                    .put("mid", "f8dd31fd7c026bb43477478e0075c912")
                    .build();
            URI uri = new URI(StringUtils.urlBuilder("https://wwwapi.kugou.com/yy/index.php", param));
            return kugouClient.getPlayUrl(uri)
                    .getData()
                    .getPlayUrl();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

}
