package ru.teacherarmy.homework1.data.model

import com.google.gson.annotations.SerializedName

data class NameInfo(
    @SerializedName("ru")
    val name: String?,
)
