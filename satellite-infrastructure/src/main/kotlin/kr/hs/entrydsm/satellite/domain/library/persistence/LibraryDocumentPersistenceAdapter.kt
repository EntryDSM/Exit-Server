package kr.hs.entrydsm.satellite.domain.library.persistence

import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.domain.LibraryDocument
import kr.hs.entrydsm.satellite.domain.library.persistence.repository.LibraryDocumentRepository
import kr.hs.entrydsm.satellite.domain.library.spi.LibraryDocumentPort
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@Adapter
class LibraryDocumentPersistenceAdapter(
    private val libraryDocumentRepository: LibraryDocumentRepository
) : LibraryDocumentPort {

    override fun save(libraryDocument: LibraryDocument): LibraryDocumentJpaEntity =
        libraryDocumentRepository.save(LibraryDocumentJpaEntity.of(libraryDocument))

    override fun queryById(libraryDocumentId: UUID) =
        libraryDocumentRepository.findByIdOrNull(libraryDocumentId)

    override fun queryAll(): List<LibraryDocument> =
        libraryDocumentRepository.findAll().toList()

    override fun queryByAccessRightNot(accessRight: AccessRight) =
        libraryDocumentRepository.findByAccessRightNot(accessRight)
}