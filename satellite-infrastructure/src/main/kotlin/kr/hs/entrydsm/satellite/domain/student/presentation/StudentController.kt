package kr.hs.entrydsm.satellite.domain.student.presentation

import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import javax.validation.Valid
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.QueryDocumentWebRequest
import kr.hs.entrydsm.satellite.domain.student.dto.StudentDocumentListResponse
import kr.hs.entrydsm.satellite.domain.student.presentation.dto.request.StudentSignUpWebRequest
import kr.hs.entrydsm.satellite.domain.student.usecase.QueryStudentDocumentListUseCase
import kr.hs.entrydsm.satellite.domain.student.usecase.StudentSignUpUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/student")
@RestController
class StudentController(
    private val studentSignUpUseCase: StudentSignUpUseCase,
    private val queryStudentDocumentListUseCase: QueryStudentDocumentListUseCase
) {

    @PostMapping
    fun studentSignUp(@RequestBody @Valid request: StudentSignUpWebRequest): TokenResponse {
        return studentSignUpUseCase.execute(request)
    }

    @GetMapping
    fun queryStudentDocumentList(
        @ModelAttribute @Valid request: QueryDocumentWebRequest
    ): StudentDocumentListResponse {
        return queryStudentDocumentListUseCase.execute(
            name = request.name,
            grade = request.grade,
            classNum = request.classNum,
            majorId = request.majorId
        )
    }
}