package ru.teacherarmy.check

import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity.WARNING
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.TextFormat
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

class DirectColorInSource :
    Detector(),
    SourceCodeScanner {
    override fun getApplicableMethodNames() = listOf("parseColor")

    override fun visitMethodCall(
        context: JavaContext,
        node: UCallExpression,
        method: PsiMethod,
    ) {
        if (context.evaluator.isMemberInClass(method, "android.graphics.Color")) {
            // if it hits here it's because we have a direct color, no more checks needed
            reportUsage(context, node)
        }
    }

    private fun reportUsage(
        context: JavaContext,
        node: UCallExpression,
    ) {
        context.report(
            issue = ISSUE_DIRECT_COLOR_IN_SOURCE,
            scope = node,
            location =
                context.getCallLocation(
                    call = node,
                    includeReceiver = true,
                    includeArguments = true,
                ),
            message = ISSUE_DIRECT_COLOR_IN_SOURCE.getExplanation(TextFormat.RAW),
        )
    }

    companion object {
        @JvmField
        val ISSUE_DIRECT_COLOR_IN_SOURCE =
            Issue.create(
                id = "DirectColorInSource",
                briefDescription = "Color method without theme reference",
                explanation =
                    """
Always favor methods for retrieving colors with an attribute and a theme as parameters. Relying only in color resources won't make the code abstracted from its theme colors.
                    """.trimIndent(),
                moreInfo = "https://material.io/develop/android/theming/color/",
                category = CORRECTNESS,
                priority = 7,
                severity = WARNING,
                androidSpecific = true,
                implementation =
                    Implementation(
                        DirectColorInSource::class.java,
                        Scope.JAVA_FILE_SCOPE,
                    ),
            )
    }
}
