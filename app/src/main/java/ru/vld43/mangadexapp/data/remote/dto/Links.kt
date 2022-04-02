package ru.vld43.mangadexapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("al") val al: String?,
    @SerializedName("ap") val ap: String?,
    @SerializedName("bw") val bw: String?,
    @SerializedName("kt") val kt: String?,
    @SerializedName("mu") val mu: String?,
    @SerializedName("amz") val amz: String?,
    @SerializedName("cdj") val cdj: String?,
    @SerializedName("ebj") val ebj: String?,
    @SerializedName("mal") val mal: String?,
    @SerializedName("raw") val raw: String?,
    @SerializedName("engtl") val engtl: String?,
)
