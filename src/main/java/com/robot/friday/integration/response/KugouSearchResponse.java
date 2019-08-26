package com.robot.friday.integration.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KugouSearchResponse {

    @JsonProperty("status")
    private String status;

    private SongListInfo data;

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_msg")
    private String errorMsg;

    @Getter
    public static class SongListInfo {
        @JsonProperty("lists")
        private List<SongDetail> lists;

        @JsonProperty("chinesecount")
        private Integer chineseCount;

        @JsonProperty("searchfull")
        private Integer searchfull;

        @JsonProperty("correctiontype")
        private String correctionType;

        @JsonProperty("subjecttype")
        private String subjectType;

        @JsonProperty("allowerr")
        private String allowerr;

        @JsonProperty("correctionsubject")
        private String correctionSubject;

        @JsonProperty("correctionforce")
        private String correctionForce;

        @JsonProperty("total")
        private Integer total;

        @JsonProperty("istagresult")
        private Boolean isTagresult;

        @JsonProperty("istag")
        private Boolean isTag;

        @JsonProperty("correctiontip")
        private String correctionTip;

        @JsonProperty("pagesize")
        private Integer pageSize;

    }

    @Getter
    public static class SongDetail {
        @JsonProperty("SongName")
        private String songName;

        @JsonProperty("FileHash")
        private String fileHash;

        @JsonProperty("AlbumID")
        private String albumId;

        @JsonProperty("AlbumName")
        private String albumName;

        @JsonProperty("SingerName")
        private String singerName;
    }
}
