package kr.hs.entrydsm.satellite.domain.document.usecase

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.common.getTestDocument
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.domain.element.AwardElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.CertificateElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.IntroduceElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.ProjectElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.feedback.domain.Feedback
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student

internal class QueryMyDocumentUseCaseTest : DescribeSpec({

    val securityPort: SecurityPort = mockk()
    val documentPort: DocumentPort = mockk()
    val feedbackPort: FeedbackPort = mockk()

    val queryMyDocumentInfoUseCase = QueryMyDocumentInfoUseCase(securityPort, documentPort, feedbackPort)

    describe("updateAward") {

        val student = anyValueObject<Student>()
        val document = getTestDocument(student)

        context("내 문서를 조회하면") {

            every { securityPort.getCurrentStudent() } returns student
            every { documentPort.queryByWriterStudentId(student.id) } returns document
            every { feedbackPort.queryByDocumentId(document.id) } returns listOf()

            it("문서 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryMyDocumentInfoUseCase.execute()
                }
            }
        }

        val introduce: IntroduceElement = anyValueObject()
        val project1: ProjectElement = anyValueObject()
        val project2: ProjectElement = anyValueObject()
        val award: AwardElement = anyValueObject()
        val certificate: CertificateElement = anyValueObject()

        val elementIds = listOf(introduce, project1, project2, award, certificate).map { it.elementId }

        val documentWithFeedbacks = Document(
            writer = WriterInfoElement(
                student = student,
                major = anyValueObject()
            ),
            introduce = introduce,
            status = DocumentStatus.SUBMITTED,
            projectList = mutableListOf(project1, project2),
            awardList = mutableListOf(award),
            certificateList = mutableListOf(certificate),
            year = 2023
        )

        val feedbacks = listOf(
            Feedback(document.id, elementIds[0], "0", false),
            Feedback(document.id, elementIds[1], "1", false),
            Feedback(document.id, elementIds[2], "2", false),
            Feedback(document.id, elementIds[3], "3", false),
            Feedback(document.id, elementIds[4], "4", false)
        )

        context("피드백이 있는 내 문서를 조회하면") {

            every { securityPort.getCurrentStudent() } returns student
            every { documentPort.queryByWriterStudentId(student.id) } returns documentWithFeedbacks
            every { feedbackPort.queryByDocumentId(documentWithFeedbacks.id) } returns feedbacks

            it("문서 상세, 피드백 정보를 반환한다.") {

                val response = queryMyDocumentInfoUseCase.execute()

                response.introduce.feedback shouldBe feedbacks[0].comment

                response.projectList[0].feedback shouldBe feedbacks[1].comment
                response.projectList[1].feedback shouldBe feedbacks[2].comment

                response.awardList[0].feedback shouldBe feedbacks[3].comment

                response.certificateList[0].feedback shouldBe feedbacks[4].comment
            }
        }

        context("내 문서가 존재하지 않으면") {

            every { securityPort.getCurrentStudent() } returns student
            every { documentPort.queryByWriterStudentId(student.id) } returns null

            it("DocumentNotFound 예외를 던진다.") {
                shouldThrow<DocumentNotFoundException> {
                    queryMyDocumentInfoUseCase.execute()
                }
            }
        }
    }
})