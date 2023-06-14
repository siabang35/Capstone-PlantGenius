package com.kotlin.aplantgenius.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.databinding.ActivityLoginBinding
import com.kotlin.aplantgenius.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getThemeApp()

        setupEmailValidation()
        setupPasswordValidation()

        binding.darkMode.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveTheme(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveTheme(false)
            }
        }

        binding.signUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (isInputValid(email, password)) {
                login(email, password)
            }
        }
    }

    private fun login(email: String, password: String) {
        progressBar(true)
        viewModel.login(
            email,
            password,
            onSuccess = {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
                progressBar(false)
            },
            onFailure = { errorMessage ->
                Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                progressBar(false)
            }
        )
    }

    private fun getThemeApp() {
        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val theme = sharedPref.getBoolean("theme", false)

        if (theme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.darkMode.isChecked = true
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.darkMode.isChecked = false
        }
    }

    private fun saveTheme(theme: Boolean) {
        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("theme", theme)
            apply()
        }
    }

    private fun setupEmailValidation() {
        binding.loginEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                validateEmail(s.toString().trim())
            }
        })
    }

    private fun setupPasswordValidation() {
        binding.loginPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword(s.toString().trim())
            }
        })
    }

    private fun validateEmail(email: String) {
        if (email.isNotEmpty()) {
            if (!isValidEmail(email)) {
                binding.emailLayout.error = getString(R.string.invalid_email)
            } else {
                binding.emailLayout.error = null
            }
        } else {
            binding.emailLayout.error = null
        }
    }

    private fun validatePassword(password: String) {
        if (password.isNotEmpty()) {
            if (password.length < 6 || !isValidPassword(password)) {
                binding.passLayout.error = getString(R.string.invalid_password)
            } else {
                binding.passLayout.error = null
            }
        } else {
            binding.passLayout.error = null
        }
    }

    private fun isInputValid(email: String, password: String): Boolean {
        var isValid = true

        if (!isValidEmail(email)) {
            binding.emailLayout.error = getString(R.string.invalid_email)
            isValid = false
        }

        if (password.length < 6) {
            binding.passLayout.error = getString(R.string.invalid_password)
            isValid = false
        }

        return isValid
    }

    private fun progressBar(visible: Boolean) {
        binding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern = Regex("(?=.*\\d)(?=.*[a-zA-Z\\p{Punct}]).+")
        return pattern.matches(password)
    }
}
