package com.elsharif.mindcrafted.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.elsharif.mindcrafted.domain.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository
):ViewModel() {

}