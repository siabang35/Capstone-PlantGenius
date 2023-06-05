package com.kotlin.aplantgenius.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        supportActionBar?.hide()

        binding.loginPhoneEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString().trim()

                if (input.isNotEmpty()) {
                    if (isValidEmail(input)) {
                        binding.phoneEmailLayout.error = null
                    } else if (isValidPhone(input)) {
                        binding.phoneEmailLayout.error = null
                    } else {
                        binding.phoneEmailLayout.error = getString(R.string.invalid_phone_email)
                    }
                } else {
                    binding.phoneEmailLayout.error = null
                }
            }
        })

        binding.loginPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString().trim()

                if (password.length < 6 || !isValidPassword(password)) {
                    binding.passLayout.error = getString(R.string.invalid_password)
                    binding.passLayout.errorIconDrawable = null
                } else {
                    binding.passLayout.error = null
                }
            }
        })

        binding.loginButton.setOnClickListener {
            val phoneEmail = binding.loginPhoneEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (phoneEmail.isNotEmpty() && password.length >= 6) {
                login(phoneEmail, password)
            } else {
                if (phoneEmail.isEmpty()) {
                    binding.phoneEmailLayout.error = getString(R.string.invalid_phone_email)
                }
                if (password.length < 6) {
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
        val request = LoginRequest(email, password)
        val call = ApiConfig().getApi().loginUser(request)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.toString()
                    if (token != null) {
                        val sharedPreferences =
                            getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("token", token)
                        editor.apply()

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                } else {
                    val errorResponse = response.errorBody()?.string()
                    val error = Gson().fromJson(errorResponse, LoginErrorResponse::class.java)
                    val errorMessage = error?.message
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
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