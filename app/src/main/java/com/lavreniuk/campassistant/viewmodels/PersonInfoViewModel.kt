package com.lavreniuk.campassistant.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lavreniuk.campassistant.models.Person
import com.lavreniuk.campassistant.repositories.PersonRepo
import javax.inject.Inject

class PersonInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    personRepository: PersonRepo
) : ViewModel() {
    lateinit var personId: String
    val user: LiveData<Person> = personRepository.getById(personId)
}
