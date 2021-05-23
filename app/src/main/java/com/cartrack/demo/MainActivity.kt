@file:Suppress("DEPRECATION")

package com.cartrack.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cartrack.demo.Utils.getJsonDataFromAsset
import com.cartrack.demo.model.UserList
import com.cartrack.demo.viewmodel.LoginViewModel
import com.cartrack.demo.viewmodel.UserViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userListAdapter: UsersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_userlist)
        val txtLogout = findViewById<TextView>(R.id.txtLogout)
        var booleanIsRemember = intent.getBooleanExtra("is_Remember", true)
        Log.d("isRemember Value", "--->" + booleanIsRemember )
        val strUserName = intent.getStringExtra("strUserName")
        val strPassword = intent.getStringExtra("strPassword")

        val jsonFileString = getJsonDataFromAsset(applicationContext, "UserList.json")
        Log.i("data", "-->$jsonFileString")

        val gson = Gson()
        val listPersonType = object : TypeToken<List<UserList>>() {}.type

        val persons: MutableList<UserList> = gson.fromJson(jsonFileString, listPersonType)
        viewModel.getUserFromAPI(persons).observe(this, Observer {

            userListAdapter = UsersListAdapter(viewModel.arrayListUser) { itemClicked, _ ->
                val intent = Intent(this, MapsActivity::class.java).apply {
                    putExtra("lat", itemClicked.user.address?.geo?.lat)
                    putExtra("lng", itemClicked.user.address?.geo?.lng)
                }
                startActivity(intent)
            }
            recyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = userListAdapter
            userListAdapter.notifyDataSetChanged()
        })


        txtLogout.setOnClickListener {
            booleanIsRemember = false
            loginViewModel.insertData(
                applicationContext,
                strUserName!!,
                strPassword!!,
                booleanIsRemember
            )
            finish()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
    }
}