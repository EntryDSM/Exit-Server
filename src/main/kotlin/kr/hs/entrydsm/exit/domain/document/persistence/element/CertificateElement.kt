package kr.hs.entrydsm.exit.domain.document.persistence.element

import java.util.*

class CertificateElement(

    val elementId: UUID = UUID.randomUUID(),
    val name: String,
    val issuingInstitution: String,
    val date: Date

)