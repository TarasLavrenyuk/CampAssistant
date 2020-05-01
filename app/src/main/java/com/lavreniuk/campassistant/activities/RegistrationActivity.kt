package com.lavreniuk.campassistant.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lavreniuk.campassistant.R
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registration_activity_start_label.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
