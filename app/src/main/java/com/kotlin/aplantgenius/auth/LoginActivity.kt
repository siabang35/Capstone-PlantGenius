package com.kotlin.aplantgenius.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.databinding.ActivityLoginBinding
import com.kotlin.aplantgenius.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.loginEmail.addTextChangedListener(object : TextWatcher {
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

        binding.loginPassword.addTextChangedListener(object : TextWatcher {
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

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (isValidEmail(email) && password.length >= 8) {
                login(email, password)
            } else {
                if (!isValidEmail(email)) {
                    binding.emailLayout.error = getString(R.string.invalid_email)
                }
                if (password.length < 8) {
                    binding.passLayout.error = getString(R.string.invalid_password)
                }
            }
        }

        binding.signUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(email: String, password: String) {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}