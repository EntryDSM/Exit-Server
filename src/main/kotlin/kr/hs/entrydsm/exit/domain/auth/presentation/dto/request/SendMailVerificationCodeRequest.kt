package kr.hs.entrydsm.exit.domain.auth.presentation.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class SendMailVerificationCodeRequest(
    @field:Email
    @field:NotBlank
    val email: String
)