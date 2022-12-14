package com.example.exit.domain.student.persistence.repository

import com.example.exit.domain.student.persistence.Student
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StudentRepository: CrudRepository<Student, UUID> {

    fun findByEmail(email: String): Student?

}