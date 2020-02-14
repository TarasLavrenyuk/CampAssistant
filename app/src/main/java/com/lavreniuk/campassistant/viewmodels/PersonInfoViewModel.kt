package com.lavreniuk.campassistant.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lavreniuk.campassistant.models.PersonInfo
import com.lavreniuk.campassistant.repositories.PersonInfoRepo

class PersonInfoViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    lateinit var personId: String
    val user: LiveData<PersonInfo> = PersonInfoRepo.getById(personId)
}
