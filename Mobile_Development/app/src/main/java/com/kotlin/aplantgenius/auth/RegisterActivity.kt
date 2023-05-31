package com.kotlin.aplantgenius.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.isEmpty()) {
                    binding.nameLayout.error = getString(R.string.invalid_name)
                    binding.nameLayout.errorIconDrawable = null
                } else {
                    binding.nameLayout.error = null
                }
            }
        })

        binding.registerEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isValidEmail(s.toString())) {
                    binding.emailLayout.error = getString(R.string.invalid_email)
                    binding.emailLayout.errorIconDrawable = null
                } else {
                    binding.emailLayout.error = null
                }
            }
        })

        binding.registerPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    binding.passLayout.error = getString(R.string.invalid_password)
                    binding.passLayout.errorIconDrawable = null
                } else {
                    binding.passLayout.error = null
                }
            }
        })

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.registerButton.setOnClickListener {
            val name = binding.registerName.text.toString()
            val email = binding.registerEmail.text.toString()
            val password = binding.registerPassword.text.toString()

            if (isValidEmail(email) && password.length >= 8 && name.isNotEmpty()) {
                register(name, email, password)

            } else {
                if (name.isEmpty()) {
                    binding.nameLayout.error = getString(R.string.invalid_name)
                }
                if (!isValidEmail(email)) {
                    binding.emailLayout.error = getString(R.string.invalid_email)
                }
                if (password.length < 8) {
                    binding.passLayout.error = getString(R.string.invalid_password)
                }
            }
        }
    }

    private fun register(name: String, email: String, password: String) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}