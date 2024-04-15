package com.example.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.login.databinding.ActivityMainBinding

public class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emailTextView: TextView = binding.emailTextView
        // 接受LoginActivity传输过来的数据并显示在界面上
        val i = intent
        val email = i.getStringExtra("USER_Email")
        emailTextView.setText("Email："+email)

        // 设置退出登录按钮的点击事件
        val logoutButton: Button = binding.logoutButton
        logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        // 清除保存的 token
        val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("token").apply()

        // 跳转回登录界面
        startActivity(Intent(this, LoginActivity::class.java))
        finish() // 关闭当前用户界面
    }

}
