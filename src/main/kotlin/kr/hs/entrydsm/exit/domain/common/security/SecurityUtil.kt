package kr.hs.entrydsm.exit.domain.common.security

import org.springframework.security.core.context.SecurityContextHolder
import java.util.UUID


object SecurityUtil {
    fun getCurrentUserId(): UUID {
        return UUID.fromString(SecurityContextHolder.getContext().authentication.name)
    }
}