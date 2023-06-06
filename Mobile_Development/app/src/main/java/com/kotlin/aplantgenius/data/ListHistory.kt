package com.kotlin.aplantgenius.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListHistory (
    val name: String?,
    val photo: Int?,
    val desc: String?,
    val id: String?
) : Parcelable
