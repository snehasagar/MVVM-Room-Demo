package com.cartrack.demo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.cartrack.demo.roomDatabase.LoginDatabase
import com.cartrack.demo.roomDatabase.LoginTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginRepository {

    companion object {

        var loginDatabase: LoginDatabase? = null

        private var loginTableModel: LiveData<LoginTable>? = null

         fun initializeDB(context: Context): LoginDatabase {
            return LoginDatabase.getDataseClient(context)
        }

        fun insertData(context: Context, username: String, password: String , isRemember: Boolean) {

            loginDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                val loginDetails = LoginTable(username, password, isRemember)
                loginDatabase!!.loginDao().insertData(loginDetails)
            }

        }

        fun getLoginDetails(context: Context, username: String): LiveData<LoginTable>? {

            loginDatabase = initializeDB(context)

            loginTableModel = loginDatabase!!.loginDao().getLoginDetails(username)

            return loginTableModel
        }

        fun deleteUsers(context : Context){
            loginDatabase = initializeDB(context)

            loginDatabase!!.loginDao().deleteAllData()
        }

    }
}
