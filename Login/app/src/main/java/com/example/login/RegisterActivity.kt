package com.example.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login.data.RegisterRequest
import com.example.login.databinding.ActivityRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val registerButton = binding.registerButton
        val backToLoginButton = binding.backToLoginTextView
        val emailEditText = binding.emailEditText
        val passwordEditText = binding.passwordEditText
        // 设置注册按钮点击事件
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // 验证输入是否为空
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请输入邮箱和密码", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 调用注册方法
            register(email, password)
        }

        // 设置返回登录界面按钮点击事件
        backToLoginButton.setOnClickListener {
            // 返回到登录界面
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // 关闭当前注册界面
        }
    }

    private fun register(email: String, password: String) {
        // 使用协程执行网络请求
        GlobalScope.launch(Dispatchers.Main) {
            try {
                // 发起注册请求
                val response = ApiClient.apiService.save(RegisterRequest(email, password))
                if (response.isSuccessful) {
                    // 注册成功，提示用户并跳转到登录界面
                    Toast.makeText(this@RegisterActivity, "注册成功，请登录", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish() // 关闭当前注册界面
                } else {
                    // 注册失败，提示错误信息
                    Toast.makeText(this@RegisterActivity, "注册失败，请稍后重试", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // 请求失败，可能是网络问题或服务器故障
                Toast.makeText(this@RegisterActivity, "注册失败，${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}