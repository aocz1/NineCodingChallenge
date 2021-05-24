package com.subzoron.ninecodingchallenge.datamodels
import com.google.gson.annotations.SerializedName


data class ShowsResponse(
    @SerializedName("payload")
    val payload: MutableList<Payload>,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("take")
    val take: Int,
    @SerializedName("totalRecords")
    val totalRecords: Int
)

data class Payload(
    @SerializedName("country")
    val country: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("drm")
    val drm: Boolean,
    @SerializedName("episodeCount")
    val episodeCount: Int,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("image")
    val image: Image,
    @SerializedName("language")
    val language: String,
    @SerializedName("nextEpisode")
    val nextEpisode: Any,
    @SerializedName("primaryColour")
    val primaryColour: String,
    @SerializedName("seasons")
    val seasons: List<Season>,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("tvChannel")
    val tvChannel: String
)

data class Image(
    @SerializedName("showImage")
    val showImage: String
)

data class Season(
    @SerializedName("slug")
    val slug: String
)