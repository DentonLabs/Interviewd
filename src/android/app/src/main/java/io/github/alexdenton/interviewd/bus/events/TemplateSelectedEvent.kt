package io.github.alexdenton.interviewd.bus.events

import io.github.alexdenton.interviewd.interview.Template

/**
 * Created by ryan on 9/16/17.
 */
data class TemplateSelectedEvent(val template: Template)