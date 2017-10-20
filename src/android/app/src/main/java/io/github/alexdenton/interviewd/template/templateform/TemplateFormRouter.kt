package io.github.alexdenton.interviewd.template.templateform

/**
 * Created by ryan on 10/14/17.
 */
sealed class TemplateFormRouter

object ShowTemplateFormFragment : TemplateFormRouter()
object ShowQuestionBankFragment : TemplateFormRouter()
object Leave : TemplateFormRouter()