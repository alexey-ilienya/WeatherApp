package ru.teacherarmy.check

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import ru.teacherarmy.check.DirectColorInSource.Companion.ISSUE_DIRECT_COLOR_IN_SOURCE
import ru.teacherarmy.check.NetworkLayerClassSerializedNameDetector.Companion.ISSUE_NETWORK_LAYER_CLASS_SERIALIZED_NAME_RULE

internal class IssueRegistry : IssueRegistry() {
    override val issues: List<Issue> =
        listOf(
            ISSUE_NETWORK_LAYER_CLASS_SERIALIZED_NAME_RULE,
            ISSUE_DIRECT_COLOR_IN_SOURCE,
        )

    override val api: Int = CURRENT_API

    override val minApi: Int = 1
}
