package com.cartrack.demo.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cartrack.demo.repository.LoginRepository
import com.cartrack.demo.model.UserList

class UserViewModel : ViewModel{
 constructor() : super()

    lateinit var user: UserList

    constructor(userData: UserList) : super() {
        this.user = userData
    }


    fun deleteUsers(context: Context) {
        LoginRepository.deleteUsers(context)
    }


    var arrayListUser = ArrayList<UserViewModel>()
    private var userLiveData = MutableLiveData<ArrayList<UserViewModel>>()

    fun getUserFromAPI(data: MutableList<UserList>?): MutableLiveData<ArrayList<UserViewModel>> {
        arrayListUser.clear()
        for (i in 0 until data?.size!!) {
            arrayListUser.add(UserViewModel(UserList(
                data[i].id,
                data[i].username,
                data[i].name,
                data[i].email,
                data[i].address)))

        }
        userLiveData.value = arrayListUser
        return userLiveData
    }

}