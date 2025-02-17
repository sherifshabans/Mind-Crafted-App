package com.elsharif.mindcrafted.presentation.session

import com.elsharif.mindcrafted.domain.model.Session
import com.elsharif.mindcrafted.domain.model.Subject

data class SessionState (

    val subjects :List<Subject> = emptyList(),
    val sessions :List<Session> = emptyList(),
    val relatedToSubject :String?=null,
    val subjectId :Int? =null,
    val session: Session?=null
)