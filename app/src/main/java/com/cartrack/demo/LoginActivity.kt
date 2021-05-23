@file:Suppress("DEPRECATION")

package com.cartrack.demo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cartrack.demo.repository.LoginRepository
import com.cartrack.demo.roomDatabase.LoginDatabase
import com.cartrack.demo.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private var isRemember: Boolean = false
    private var strUserName: String? = null
    private var strPassword: String? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        checkNextStep()
        val txtUserName = findViewById<TextView>(R.id.txtName)
        val txtPassword = findViewById<TextView>(R.id.txtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val cbIsRemembered = findViewById<CheckBox>(R.id.checkBox)

        cbIsRemembered.setOnClickListener {
            isRemember = cbIsRemembered.isChecked
            Log.d("isRemember Value", "--->" + isRemember )
        }


        txtregister.setOnClickListener {
            strUserName = txtUserName.text.toString().trim()
            strPassword = txtPassword.text.toString().trim()

            when {
                strPassword!!.isEmpty() -> {
                    txtUserName.hint = "Please enter the username"
                    lblReadResponse.text = "Please enter the username"
                }
                strPassword!!.isEmpty() -> {
                    txtPassword.hint = "Please enter the Password"
                    lblReadResponse.text = "Please enter the Password"
                }
                else -> {
                    viewModel.insertData(
                        applicationContext,
                        strUserName!!,
                        strPassword!!,
                        isRemember
                    )
                    Log.i("data", "Inserted Successfully")
                    lblReadResponse.text = "User Registered Successfully"
                    txtUserName.text = strUserName
                    txtPassword.text = strPassword
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("isRemember", isRemember)
                        putExtra("strUserName", strUserName)
                        putExtra("strPassword", strPassword)
                    }
                    startActivity(intent)
                    finish()
                }
            }

        }
        btnLogin.setOnClickListener {

            strUserName = txtUserName.text.toString().trim()
            strPassword = txtPassword.text.toString().trim()

            when {
                strUserName!!.isEmpty() -> {
                    txtUserName.hint = "Please enter the username"
                    lblReadResponse.text = "Please enter the username"
                }
                strPassword!!.isEmpty() -> {
                    txtPassword.hint = "Please enter the Password"
                    lblReadResponse.text = "Please enter the Password"
                }
                else -> {
                    viewModel.getLoginDetails(applicationContext, strUserName!!)!!
                        .observe(this, Observer {
                            if (it == null) {
                                lblReadResponse.text = "Data Not Found"
                            } else {
                                txtUserName.text = it.Username
                                txtPassword.text = it.Password
                                isRemember = it.isRemember
                                val intent = Intent(this, MainActivity::class.java).apply {
                                    putExtra("isRemember", isRemember)
                                    putExtra("strUserName", strUserName)
                                    putExtra("strPassword", strPassword)
                                }
                                startActivity(intent)
                                finish()
                                lblReadResponse.text = "Login Successfully"
                            }
                        })
                }
            }

        }
    }


    private fun checkNextStep() {
        LoginRepository.loginDatabase = LoginRepository.initializeDB(applicationContext)
        if(LoginRepository.loginDatabase!=null && isRemember) {
            viewModel.getLoginDetails(applicationContext, strUserName!!)!!
                .observe(this, Observer {
                    if (it != null) {
                        isRemember = it.isRemember
                        if (isRemember) {
                            nextView()
                        }
                    }
                })
        }
    }
    private fun nextView() {
        Log.d("cek", "nextView: MainActivity}")
        Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("isRemember", isRemember)
                    putExtra("strUserName", strUserName)
                    putExtra("strPassword", strPassword)
                }
                startActivity(intent)
                finish()

        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {
        private const val SPLASH_TIME_OUT = 1500
    }
}