package kr.hs.entrydsm.satellite.domain.document.domain.element

import kr.hs.entrydsm.satellite.domain.major.domain.Major
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import java.util.*

class WriterInfoElement(

    elementId: UUID? = null,

    val studentId: UUID,
    val name: String,
    val email: String,
    val profileImagePath: String,

    val grade: String,
    val classNum: String,
    val number: String,

    val majorId: UUID,
    val majorName: String

) : AbstractElement(elementId) {

    override val elementName: String
        get() = "내 정보"

    val studentNumber: Int
        get() = toStudentNumber(grade, classNum, number)

    constructor(
        student: Student,
        major: Major
    ) : this(
        studentId = student.id,
        name = student.name,
        profileImagePath = student.profileImagePath,
        grade = student.grade,
        classNum = student.classNum,
        number = student.number,
        email = student.email,
        majorId = major.id,
        majorName = major.name
    )

    fun updateVariableInfo(
        profileImagePath: String,
        grade: String,
        classNum: String,
        number: String,
        email: String,
        major: Major
    ): WriterInfoElement {
        return copy(
            profileImagePath = profileImagePath,
            grade = grade,
            classNum = classNum,
            number = number,
            email = email,
            majorId = major.id,
            majorName = major.name
        )
    }

    private fun copy(
        studentId: UUID = this.studentId,
        name: String = this.name,
        profileImagePath: String = this.profileImagePath,
        grade: String = this.grade,
        classNum: String = this.classNum,
        number: String = this.number,
        email: String = this.email,
        majorId: UUID = this.majorId,
        majorName: String = this.majorName
    ): WriterInfoElement {
        return WriterInfoElement(
            studentId = studentId,
            name = name,
            profileImagePath = profileImagePath,
            grade = grade,
            classNum = classNum,
            number = number,
            email = email,
            majorId = majorId,
            majorName = majorName
        )
    }

    companion object {
        fun toStudentNumber(
            grade: String,
            classNum: String,
            number: String
        ) = Integer.valueOf(
            grade + classNum + String.format("%02d", Integer.parseInt(number))
        )
    }
}