package com.kotlin.aplantgenius.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListHistory (
    val name: String?,
    val photo: String?,
    val description: String?,
    val id: String?
) : Parcelable
