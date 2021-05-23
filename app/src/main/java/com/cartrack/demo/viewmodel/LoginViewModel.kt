package com.cartrack.demo.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cartrack.demo.repository.LoginRepository
import com.cartrack.demo.roomDatabase.LoginTable

class LoginViewModel : ViewModel() {

    private var liveDataLogin: LiveData<LoginTable>? = null

    fun insertData(context: Context, username: String, password: String, isRemember: Boolean) {
        LoginRepository.insertData(context, username, password, isRemember)
    }

    fun getLoginDetails(context: Context, username: String): LiveData<LoginTable>? {
        liveDataLogin = LoginRepository.getLoginDetails(context, username)
        return liveDataLogin
    }
     fun UserCheck(context: Context) {

     }

}
