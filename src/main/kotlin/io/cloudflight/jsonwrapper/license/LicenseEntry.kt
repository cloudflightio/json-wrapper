package io.cloudflight.jsonwrapper.license

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class LicenseEntry @JsonCreator constructor(
    @JsonProperty("license") @field:JsonProperty("license") val license: String,
    @JsonProperty("license_id") @field:JsonProperty("license_id") val licenseId: String?,
    @JsonProperty("license_url") @field:JsonProperty("license_url") val licenseUrl: String
)
