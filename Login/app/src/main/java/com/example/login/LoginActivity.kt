package com.example.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login.data.LoginRequest
import com.example.login.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginButton = binding.loginButton
        val emailEditText = binding.emailEditText
        val passwordEditText = binding.passwordEditText
        val registerButton =binding.registerTextView
        // 设置登录按钮点击事件
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // 验证输入是否为空
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请输入邮箱和密码", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 调用登录方法
            login(email, password)
        }

        // 设置注册按钮点击事件
        registerButton.setOnClickListener {
            // 跳转到注册界面
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    fun saveToken(token: String) {
        // 使用SharedPreferences存储token数据
        val sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.commit()
    }
    fun login(username: String, password: String) {
        // 使用协程执行网络请求
        GlobalScope.launch(Dispatchers.Main) {
            try {
                // 发起登录请求
                val response = ApiClient.apiService.login(LoginRequest(username, password))
                if (response.isSuccessful) {
                    Log.d("LoginActivity", "Response body: ${response.body()}")
                    val token = response.body()?.token
                    Log.d("LoginActivity", "Token: $token")
                    if (token != null) {
                        // 登录成功，保存token并跳转到用户界面
                        saveToken(token)
                        // 在主线程上更新UI
                        withContext(Dispatchers.Main) {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            // 把用户email传入用户界面
                            intent.putExtra("USER_Email", username)
                            startActivity(intent)
                            finish() // 关闭当前登录界面
                        }
                    } else {
                        // 如果token为空，表示登录失败
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@LoginActivity,
                                "登录失败，请检查邮箱和密码",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    // 如果请求不成功，提示网络错误
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@LoginActivity,
                            "网络错误，请稍后重试",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                // 请求失败
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "登录失败，${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }
}