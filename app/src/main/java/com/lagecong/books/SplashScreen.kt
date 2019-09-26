package com.lagecong.books

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.lagecong.books.books.BookActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        openMainActivity()
    }

    private fun openMainActivity() {
        Handler().postDelayed({
            startActivity(Intent(this, BookActivity::class.java))
            overridePendingTransition(R.anim.enter, R.anim.exit)
            finish()
        },400)

    }
}
