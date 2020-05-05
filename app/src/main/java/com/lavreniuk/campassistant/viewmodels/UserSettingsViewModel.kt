package com.lavreniuk.campassistant.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.lavreniuk.campassistant.dao.AppDatabase
import com.lavreniuk.campassistant.enums.ParameterType
import com.lavreniuk.campassistant.enums.SocialType
import com.lavreniuk.campassistant.models.Param
import com.lavreniuk.campassistant.models.User
import com.lavreniuk.campassistant.repositories.ParamRepo
import com.lavreniuk.campassistant.repositories.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserSettingsViewModel @JvmOverloads constructor(
    application: Application,
    personId: String = User.USER_ID
) : AndroidViewModel(application) {

    private val userRepo: UserRepo = UserRepo(AppDatabase.getAppDataBase(application).userDao())
    private val paramRepo: ParamRepo =
        ParamRepo(personId, AppDatabase.getAppDataBase(application).paramDao())

    val user: LiveData<User> = userRepo.user
    val params: LiveData<List<Param>> = paramRepo.personParams

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun addParam(
        paramName: String,
        paramValue: String,
        parameterType: ParameterType,
        socialType: SocialType? = null
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            val newParam = Param(
                ownerId = User.USER_ID,
                name = paramName,
                value = paramValue,
                type = parameterType,
                socialType = socialType
            )

            paramRepo.insertParam(newParam)
        }
}
