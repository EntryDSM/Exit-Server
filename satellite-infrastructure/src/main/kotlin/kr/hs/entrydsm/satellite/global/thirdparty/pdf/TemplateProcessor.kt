package kr.hs.entrydsm.satellite.global.thirdparty.pdf

import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Component
class TemplateProcessor(
    private val templateEngine: TemplateEngine,
) {
    fun process(
        template: String,
        data: Any?,
    ): String {
        val context = Context().apply { setVariable("data", data) }
        println(template)
        return templateEngine.process(template, context)
    }
}