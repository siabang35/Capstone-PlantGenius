package com.kotlin.aplantgenius.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.data.ApiConfig
import com.kotlin.aplantgenius.data.LoginErrorResponse
import com.kotlin.aplantgenius.data.LoginRequest
import com.kotlin.aplantgenius.data.LoginResponse
import com.kotlin.aplantgenius.databinding.ActivityLoginBinding
import com.kotlin.aplantgenius.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    //private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getThemeApp()

        binding.apply {
            loginPhoneEmail.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val input = s.toString().trim()

                    if (input.isNotEmpty()) {
                        if (isValidEmail(input)) {
                            phoneEmailLayout.error = null
                        } else if (isValidPhone(input)) {
                            phoneEmailLayout.error = null
                        } else {
                            phoneEmailLayout.error = getString(R.string.invalid_phone_email)
                        }
                    } else {
                        phoneEmailLayout.error = null
                    }
                }
            })
        }

        binding.apply {
            loginPassword.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val password = s.toString().trim()

                    if (password.isNotEmpty()) {
                        if (password.length < 6 || !isValidPassword(password)) {
                            passLayout.error = getString(R.string.invalid_password)
                            passLayout.errorIconDrawable = null
                        } else {
                            passLayout.error = null
                        }
                    } else {
                        passLayout.error = null
                    }
                }
            })
        }

        binding.apply {
            loginButton.setOnClickListener {
                val phoneEmail = loginPhoneEmail.text.toString()
                val password = loginPassword.text.toString()

                if (isValidEmail(phoneEmail) && password.length >= 6) {
                    login(phoneEmail, password)
                } else {
                    if (!isValidEmail(phoneEmail)) {
                        phoneEmailLayout.error = getString(R.string.invalid_phone_email)
                    }
                    if (password.length < 6) {
                        passLayout.error = getString(R.string.invalid_password)
                    }
                }
            }
        }

        binding.signUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.darkMode.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveTheme(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveTheme(false)
            }
        }
    }

    private fun login(email: String, password: String) {
        progressBar(true)

        val request = LoginRequest(email, password)
        val call = ApiConfig().getApi().loginUser(request)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>, response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val token = response.body()?.toString()

                    if (token != null) {
                        Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT)
                            .show()
                        val shared = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        with(shared.edit()) {
                            putString("token", token)
                            apply()
                        }
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        progressBar(false)
                    }

                } else {
                    val errorResponse = response.errorBody()?.string()
                    val error = Gson().fromJson(errorResponse, LoginErrorResponse::class.java)
                    val errorMessage = error?.message
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    progressBar(false)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity, getString(R.string.failServer), Toast.LENGTH_SHORT
                ).show()
                progressBar(false)
            }
        })
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

    private fun progressBar(visible: Boolean) {
        if (visible) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPhone(phoneNumber: String): Boolean {
        return phoneNumber.matches(Regex("\\d{10,}"))
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern = Regex("(?=.*\\d)(?=.*[a-zA-Z\\p{Punct}]).+")
        return pattern.matches(password)
    }
}
