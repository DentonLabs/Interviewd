package io.github.alexdenton.interviewd.template.create.events

/**
 * Created by ryan on 10/14/17.
 */
sealed class TemplateFormRouter

object ShowTemplateFormFragment : TemplateFormRouter()
object ShowQuestionBankFragment : TemplateFormRouter()