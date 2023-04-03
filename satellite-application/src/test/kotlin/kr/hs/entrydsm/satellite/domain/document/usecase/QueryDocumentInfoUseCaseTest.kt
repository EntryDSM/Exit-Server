package kr.hs.entrydsm.satellite.domain.document.usecase

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.common.getTestDocument
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentAccessRightException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort
import kr.hs.entrydsm.satellite.domain.teacher.domain.Teacher

internal class QueryDocumentInfoUseCaseTest : DescribeSpec({

    val securityPort: SecurityPort = mockk()
    val documentPort: DocumentPort = mockk()
    val studentPort: StudentPort = mockk()

    val queryDocumentInfoUseCase = QueryDocumentInfoUseCase(securityPort, documentPort, studentPort)

    describe("queryDocumentInfo") {

        val student = anyValueObject<Student>(
            "grade" to "1",
            "classNum" to "1",
            "number" to "1"
        )
        val otherStudent = anyValueObject<Student>()
        val teacher = anyValueObject<Teacher>()

        val createdDocument = getTestDocument(
            student = student,
            status = DocumentStatus.CREATED
        )

        context("작성자가 CREATED 상태의 문서를 조회하면") {

            every { securityPort.getCurrentUserAuthority() } returns Authority.STUDENT
            every { securityPort.getCurrentStudent() } returns student
            every { documentPort.queryById(createdDocument.id) } returns createdDocument

            it("문서의 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryDocumentInfoUseCase.execute(createdDocument.id)
                }
            }
        }

        context("작성자가 아닌 학생이 CREATED 상태의 문서를 조회하면") {

            every { securityPort.getCurrentUserAuthority() } returns Authority.STUDENT
            every { securityPort.getCurrentStudent() } returns otherStudent
            every { documentPort.queryById(createdDocument.id) } returns createdDocument

            it("DocumentAccessRight 예외를 던진다.") {
                shouldThrow<DocumentAccessRightException> {
                    queryDocumentInfoUseCase.execute(createdDocument.id)
                }
            }
        }

        context("선생님이 CREATED 상태의 문서를 조회하면") {

            every { securityPort.getCurrentUserAuthority() } returns Authority.TEACHER
            every { documentPort.queryById(createdDocument.id) } returns createdDocument

            it("DocumentAccessRight 예외를 던진다.") {
                shouldThrow<DocumentAccessRightException> {
                    queryDocumentInfoUseCase.execute(createdDocument.id)
                }
            }
        }

        val submittedDocument = getTestDocument(
            student = student,
            status = DocumentStatus.SUBMITTED
        )

        context("작성자가 아닌 학생이 SUBMITTED 상태의 문서를 조회하면") {

            every { securityPort.getCurrentUserAuthority() } returns Authority.STUDENT
            every { securityPort.getCurrentStudent() } returns otherStudent
            every { documentPort.queryById(submittedDocument.id) } returns submittedDocument

            it("DocumentAccessRight 예외를 던진다.") {
                shouldThrow<DocumentAccessRightException> {
                    queryDocumentInfoUseCase.execute(submittedDocument.id)
                }
            }
        }

        context("선생님이 SUBMITTED 상태의 문서를 조회하면") {

            every { securityPort.getCurrentUserAuthority() } returns Authority.TEACHER
            every { documentPort.queryById(submittedDocument.id) } returns submittedDocument

            it("문서의 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryDocumentInfoUseCase.execute(submittedDocument.id)
                }
            }
        }

        val sharedDocument = getTestDocument(
            student = student,
            status = DocumentStatus.SHARED
        )

        context("작성자가 아닌 학생이 SHARED 상태의 문서를 조회하면") {

            every { securityPort.getCurrentUserAuthority() } returns Authority.STUDENT
            every { securityPort.getCurrentStudent() } returns otherStudent
            every { documentPort.queryById(sharedDocument.id) } returns sharedDocument

            it("문서의 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryDocumentInfoUseCase.execute(sharedDocument.id)
                }
            }
        }

        context("선생님이 SHARED 상태의 문서를 조회하면") {

            every { securityPort.getCurrentUserAuthority() } returns Authority.TEACHER
            every { documentPort.queryById(sharedDocument.id) } returns sharedDocument
            
            it("문서의 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryDocumentInfoUseCase.execute(sharedDocument.id)
                }
            }
        }
    }
})