package com.example.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.login.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH = 2000 // Splash界面显示时长，单位为毫秒

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 使用Handler在指定时长后跳转到登录界面
        Handler().postDelayed({
            // 创建一个Intent，跳转到登录界面LoginActivity
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish() // 结束当前的SplashActivity，避免用户返回到该界面
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}