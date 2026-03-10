package ru.teacherarmy.calendar.composables

import androidx.compose.runtime.Immutable
import java.io.Serializable

@Immutable
internal class VisibleItemState(
    val firstVisibleItemIndex: Int = 0,
    val firstVisibleItemScrollOffset: Int = 0,
) : Serializable
