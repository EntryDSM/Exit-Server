package kr.hs.entrydsm.exit.domain.document.persistence

import com.fasterxml.uuid.Generators
import kr.hs.entrydsm.exit.domain.document.persistence.element.CertificateElement
import kr.hs.entrydsm.exit.domain.document.persistence.element.IntroduceElement
import kr.hs.entrydsm.exit.domain.document.persistence.element.ProjectElement
import kr.hs.entrydsm.exit.domain.document.persistence.element.WriterInfoElement
import kr.hs.entrydsm.exit.domain.document.persistence.enums.Status
import kr.hs.entrydsm.exit.domain.student.persistence.Student
import kr.hs.entrydsm.exit.global.entity.BaseMongoUUIDEntity
import org.springframework.data.annotation.Id
import java.util.*
import javax.persistence.Column

@org.springframework.data.mongodb.core.mapping.Document(collection="documents")
class Document(

    val writer: WriterInfoElement,

    val status: Status,

    val introduce: IntroduceElement = IntroduceElement(),

    val skillSet: MutableList<String> = mutableListOf(),

    val projectList: MutableList<ProjectElement> = mutableListOf(),

    val certificateList: MutableList<CertificateElement> = mutableListOf()

): BaseMongoUUIDEntity() {

    fun isWriter(student: Student): Boolean {
        return writer.studentId == student.id
    }

    fun updateWriterInfo(writer: WriterInfoElement): Document =
        copy(writer = writer)

    fun updateStatus(status: Status): Document =
        copy(status = status)

    fun updateIntroduce(introduce: IntroduceElement): Document =
        copy(introduce = introduce)

    fun updateSkillSet(skillSet: MutableList<String>): Document  =
        copy(skillSet = skillSet)

    fun updateProject(projectList: MutableList<ProjectElement>): Document  =
        copy(projectList = projectList)

    fun updateCertificateList(certificateList: MutableList<CertificateElement>): Document  =
        copy(certificateList = certificateList)

    private fun copy(
        writer: WriterInfoElement = this.writer,
        status: Status = this.status,
        introduce: IntroduceElement = this.introduce,
        skillSet: MutableList<String> = this.skillSet,
        projectList: MutableList<ProjectElement> = this.projectList,
        certificateList: MutableList<CertificateElement> = this.certificateList
    ): Document {
        return Document(
            writer = writer,
            status = status,
            introduce = introduce,
            skillSet = skillSet,
            projectList = projectList,
            certificateList = certificateList
        )
    }

}