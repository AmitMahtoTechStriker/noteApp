package com.mobile.noteapp

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.noteapp.models.UserRequest
import com.mobile.noteapp.models.UserResponse
import com.mobile.noteapp.repository.UserRepository
import com.mobile.noteapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel() {

    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
    get() = userRepository.userResponseLiveData

    fun registerUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
    }

    fun loginUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    fun validateCredentials(emailAddress: String, userName: String, password: String) : Pair<Boolean, String> {

        var result = Pair(true, "")
        if(TextUtils.isEmpty(emailAddress) || (TextUtils.isEmpty(userName)) || TextUtils.isEmpty(password)){
            result = Pair(false, "Please provide the credentials")
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            result = Pair(false, "Email is invalid")
        }
        else if(!TextUtils.isEmpty(password) && password.length <= 5){
            result = Pair(false, "Password length should be greater than 5")
        }
        return result
    }
}