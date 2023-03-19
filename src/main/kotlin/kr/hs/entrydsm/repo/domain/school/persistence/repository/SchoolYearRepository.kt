package kr.hs.entrydsm.repo.domain.school.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.repo.domain.school.persistence.SchoolYear
import org.springframework.data.repository.CrudRepository

interface SchoolYearRepository : CrudRepository<SchoolYear, UUID>