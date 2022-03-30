package io.cloudflight.jsonwrapper.cleancode

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

@Serializable
class CleanCodeReport(
    val moduleName: String,
    val verificationResults: List<PluginVerificationResults>

) {
    fun getIssueCount(includingSuppressedIssues: Boolean): Int =
        verificationResults.sumOf { it.getIssueCount(includingSuppressedIssues) }

    fun prettyPrint(
        onlyRulesWithIssues: Boolean = true,
        printSuppressedIssues: Boolean = false,
        printDocumentationUrl: Boolean = false
    ): String {
        val buffer = StringBuffer()
        buffer.append(System.lineSeparator())
        val title = "Clean Code Verification Report for $moduleName"
        buffer.append(title).append(System.lineSeparator())
        buffer.append("-".repeat(title.length)).append(System.lineSeparator())
        verificationResults.filter { !onlyRulesWithIssues || it.getIssueCount(printSuppressedIssues) > 0 }
            .forEach { verificationResult ->
                verificationResult.verifications.filter { !onlyRulesWithIssues || it.getIssueCount(printSuppressedIssues) > 0 }
                    .forEach { verification ->
                        buffer.append("${verificationResult.pluginId}.${verification.ruleId}: ${verification.description}:")
                            .append(" ${verification.getIssueCount(false)} issue(s) (${verification.getSuppressedIssueCount()} suppressed)")

                        if (verification.getIssueCount(printSuppressedIssues) > 0 && verification.suppressionReason != null) {
                            buffer.append(", suppressed because of \"${verification.suppressionReason}\"")
                        }

                        if (verification.getIssueCount(printSuppressedIssues) > 0) {
                            buffer.append(":")
                        }

                        buffer.append(System.lineSeparator())

                        verification.issues.filter { it.suppressionReason == null }.forEach {
                            if (it.suppressionReason == null) {
                                buffer.append("  ${it.hash}: ${it.description}").append(System.lineSeparator())
                            } else if (printSuppressedIssues) {
                                buffer.append("  ${it.hash}: ${it.description} // suppressed because of \"${it.suppressionReason}\"")
                                    .append(System.lineSeparator())
                            }
                        }

                        if (printDocumentationUrl) {
                            verificationResult.pluginDocumentationUrl?.let {
                                buffer.append(
                                    "  see $it#" + verification.ruleId.replace(
                                        '.',
                                        '-'
                                    ) + " for more details and reasoning behind"
                                ).append(System.lineSeparator())
                            }
                        }

                        buffer.append(System.lineSeparator())
                    }
            }
        return buffer.toString()
    }


    companion object {
        private val json = Json {
            ignoreUnknownKeys = true
        }

        fun readFromFile(file: File): CleanCodeReport {
            return json.decodeFromStream(file.inputStream())
        }
    }
}

@Serializable
class PluginVerificationResults(
    val pluginId: String,
    val pluginVersion: String,
    val pluginDocumentationUrl: String?,
    val verifications: List<RuleVerification>
) {
    fun getIssueCount(includingSuppressedIssues: Boolean): Int =
        verifications.sumOf { it.getIssueCount(includingSuppressedIssues) }

    fun getSuppressedIssueCount(): Int =
        getIssueCount(true) - getIssueCount(false)
}

@Serializable
class RuleVerification(
    val ruleId: String,
    val description: String,
    override val suppressionReason: String?,
    val issues: List<RuleIssueVerification>
) : Suppressible {

    fun getIssueCount(includingSuppressedIssues: Boolean): Int =
        if (includingSuppressedIssues) issues.count() else if (suppressionReason != null) 0 else issues.count { it.suppressionReason == null }

    fun getSuppressedIssueCount(): Int =
        getIssueCount(true) - getIssueCount(false)

}

@Serializable
class RuleIssueVerification(
    val description: String,
    val hash: String,
    override val suppressionReason: String?
) : Suppressible

interface Suppressible {
    val suppressionReason: String?
}