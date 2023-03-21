package kr.hs.entrydsm.repo.domain.auth.persistence.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VerificationCodeRepository : CrudRepository<kr.hs.entrydsm.repo.domain.auth.persistence.VerificationCode, String>