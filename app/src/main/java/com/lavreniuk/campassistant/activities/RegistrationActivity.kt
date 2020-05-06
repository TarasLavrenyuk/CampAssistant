package com.lavreniuk.campassistant.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registration_activity_start_label.setOnClickListener {
            val fName = registration_activity_fname_input.text
            fName?.let {
                if (it.isBlank()) {
                    registration_activity_fname_input.error =
                        getString(R.string.ui_field_cannot_be_empty)
                    return@setOnClickListener
                }
            } ?: return@setOnClickListener

            userViewModel.createUser(
                fName = fName.toString().trim(),
                lName = registration_activity_lname_input.text?.toString()?.trim()?.let {
                    return@let if (it.isBlank()) null else it
                }
            )

            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
