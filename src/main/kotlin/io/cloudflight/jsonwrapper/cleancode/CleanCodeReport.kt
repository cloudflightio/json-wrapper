package io.cloudflight.jsonwrapper.cleancode

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.cloudflight.jsonwrapper.Parser
import java.io.File

class CleanCodeReport @JsonCreator constructor(
    @JsonProperty("moduleName") val moduleName: String,
    @JsonProperty("verificationResults") val verificationResults: List<PluginVerificationResults>

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
        fun readFromFile(file: File): CleanCodeReport {
            return Parser.parseFile(file, CleanCodeReport::class.java, withKotlinSupport = false)
        }
    }
}

class PluginVerificationResults @JsonCreator constructor(
    @JsonProperty("pluginId") val pluginId: String,
    @JsonProperty("pluginVersion") val pluginVersion: String,
    @JsonProperty("pluginDocumentationUrl") val pluginDocumentationUrl: String?,
    @JsonProperty("verifications") val verifications: List<RuleVerification>
) {
    @JsonIgnore
    fun getIssueCount(includingSuppressedIssues: Boolean): Int =
        verifications.sumOf { it.getIssueCount(includingSuppressedIssues) }

    @JsonIgnore
    fun getSuppressedIssueCount(): Int =
        getIssueCount(true) - getIssueCount(false)
}

class RuleVerification @JsonCreator constructor(
    @JsonProperty("ruleId") val ruleId: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("suppressionReason") suppressionReason: String?,
    @JsonProperty("issues") val issues: List<RuleIssueVerification>
) : Suppressible(suppressionReason) {

    @JsonIgnore
    fun getIssueCount(includingSuppressedIssues: Boolean): Int =
        if (includingSuppressedIssues) issues.count() else if (suppressionReason != null) 0 else issues.count { it.suppressionReason == null }

    @JsonIgnore
    fun getSuppressedIssueCount(): Int =
        getIssueCount(true) - getIssueCount(false)

}

class RuleIssueVerification @JsonCreator constructor(
    @JsonProperty("description") val description: String,
    @JsonProperty("hash") val hash: String,
    @JsonProperty("suppressionReason") suppressionReason: String?
) : Suppressible(suppressionReason)

open class Suppressible(
    val suppressionReason: String?
)