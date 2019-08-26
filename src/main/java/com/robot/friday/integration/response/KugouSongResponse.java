package com.robot.friday.integration.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public class KugouSongResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("err_code")
    private String errCode;

    @JsonProperty("data")
    private SongDetail data;

    @Getter
    public static class SongDetail {

        @JsonProperty("hash")
        private String hash;

        @JsonProperty("timelength")
        private Integer timeLength;

        @JsonProperty("filesize")
        private Integer fileSize;

        @JsonProperty("album_name")
        private String albumName;

        @JsonProperty("img")
        private String img;

        @JsonProperty("song_name")
        private String songName;

        @JsonProperty("author_name")
        private String authorName;

        @JsonProperty("lyrics")
        private String lyrics;

        @JsonProperty("play_url")
        private String playUrl;
    }
}
