package com.robot.friday.integration;

import com.google.common.collect.ImmutableMap;
import com.robot.friday.integration.response.KugouSearchResponse;
import com.robot.friday.util.StringUtils;
import feign.Feign;
import feign.Target;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Slf4j
@Service
public class KugouMusicClient {

    private KugouClient kugouClient;

    @Autowired
    public KugouMusicClient() {
        kugouClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .contract( new SpringMvcContract())
                .target(Target.EmptyTarget.create(KugouClient.class));
    }

    public InputStream play(String music, String singer) {
        try {
            String playUrl = this.kugouSongSearch(music, singer);
            if (Strings.isNotBlank(playUrl)) {
                return kugouClient.play(new URI(playUrl)).body().asInputStream();
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String kugouSongSearch(String songName, String singer) {
        try {
            ImmutableMap<String, String> param = new ImmutableMap.Builder<String, String>()
                    .put("keyword", songName)
                    .put("page", Integer.toString(1))
                    .put("pagesize", Integer.toString(20))
                    .build();
            URI uri = new URI(StringUtils.urlBuilder("https://songsearch.kugou.com/song_search_v2", param));
            KugouSearchResponse response = kugouClient.searchSong(uri);
            return selectValidPlayUrl(response, singer);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String kugouRetrievePlayUrl(String hash) {
        try {
            ImmutableMap<String, String> param = new ImmutableMap.Builder<String, String>()
                    .put("r", "play/getdata")
                    .put("hash", hash)
                    .put("mid", UUID.randomUUID().toString()) // 加一个uuid就可以，为什么
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

    private String selectValidPlayUrl(KugouSearchResponse response, String singer) {
        for (KugouSearchResponse.SongDetail songDetail : response.getData().getLists()) {
            if (Strings.isNotBlank(singer) && !songDetail.getSingerName().equals(singer)) {
                continue;
            }
            if (songDetail.getSongName().contains("KTV") || songDetail.getSongName().contains("伴奏") ||
                    songDetail.getSongName().contains("Live") || songDetail.getSongName().contains("现场")) {
                continue;
            }
            String playUrl = this.kugouRetrievePlayUrl(songDetail.getFileHash());
            if (Strings.isNotBlank(playUrl)) {
                return playUrl;
            }
        }
        return null;
    }
}
