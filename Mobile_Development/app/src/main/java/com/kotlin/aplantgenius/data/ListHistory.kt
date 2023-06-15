package com.kotlin.aplantgenius.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListHistory(
    val id: Int?,
    val result: String?,
    val image: String?,
    val desc: String?
) : Parcelable

