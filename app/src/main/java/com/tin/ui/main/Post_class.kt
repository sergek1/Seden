package com.tin.ui.main

import com.google.gson.annotations.SerializedName

data class Post_class (
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("votes")
    val votes: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("gifURL")
    val gifURL: String,
    @SerializedName("previewURL")
    val previewURL: String,
    @SerializedName("videoURL")
    val videoURL: String,
    @SerializedName("videoPath")
    val videoPath: String,
    @SerializedName("commentsCount")
    val commentsCount: String,
    @SerializedName("canVote")
    val canVote: String,

        )
