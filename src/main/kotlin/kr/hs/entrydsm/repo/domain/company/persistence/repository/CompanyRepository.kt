package kr.hs.entrydsm.repo.domain.company.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.repo.domain.company.persistence.Company
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepository : CrudRepository<Company, UUID> {
    fun findByEmail(email: String): Company?
    fun findByNameContaining(name: String): List<Company>
}