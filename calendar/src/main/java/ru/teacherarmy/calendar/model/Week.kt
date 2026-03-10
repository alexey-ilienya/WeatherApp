package ru.teacherarmy.calendar.model

import androidx.compose.runtime.Immutable
import java.io.Serializable

@Immutable
data class Week(
    val days: List<WeekDay>,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Week

        if (days.first() != other.days.first()) return false
        if (days.last() != other.days.last()) return false

        return true
    }

    override fun hashCode(): Int {
        var result = days.first().hashCode()
        result = 31 * result + days.last().hashCode()
        return result
    }

    override fun toString(): String =
        "Week { " +
            "first = ${days.first()}, " +
            "last = ${days.last()} " +
            "} "
}
