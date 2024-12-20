package com.madememagic.limelight.data.model

import com.google.gson.annotations.SerializedName

data class BaseModelTvSeries(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<TvSeriesItem>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
