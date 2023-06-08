package com.kotlin.aplantgenius.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.data.*
import com.kotlin.aplantgenius.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    //private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            registerName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val name = s.toString().trim()

                    if (name.isEmpty()) {
                        nameLayout.error = getString(R.string.invalid_name)
                    } else if (!isValidName(name)) {
                        nameLayout.error = getString(R.string.invalid_nameNumber)
                    } else {
                        nameLayout.error = null
                    }
                }
            })
        }
        binding.apply {
            registerPhone.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    val phone = s.toString().trim()

                    if (phone.isNotEmpty()) {
                        if (!isValidPhone(phone)) {
                            phoneLayout.error = getString(R.string.invalid_phone)
                            phoneLayout.errorIconDrawable = null
                        } else {
                            phoneLayout.error = null
                        }
                    } else {
                        phoneLayout.error = null
                    }
                }
            })
        }
        binding.apply {
            registerEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    val email = s.toString().trim()

                    if (email.isNotEmpty()) {
                        if (!isValidEmail(email)) {
                            emailLayout.error = getString(R.string.invalid_email)
                            emailLayout.errorIconDrawable = null
                        } else {
                            emailLayout.error = null
                        }
                    } else {
                        emailLayout.error = null
                    }
                }
            })
        }
        binding.apply {
            registerPassword.addTextChangedListener(object : TextWatcher {
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

        binding.backButton.setOnClickListener {
            finish()
        }
        binding.backText.setOnClickListener {
            finish()
        }

        binding.apply {
            registerButton.setOnClickListener {
                val name = registerName.text.toString()
                val phone = registerPhone.text.toString()
                val email = registerEmail.text.toString()
                val password = registerPassword.text.toString()

                if (name.isNotEmpty() && isValidPhone(phone) && isValidEmail(email) && password.length >= 6) {
                    register(name, email, password, phone)
                } else {
                    if (!isValidName(name) || name.isEmpty()) {
                        nameLayout.error = getString(R.string.invalid_name)
                    }
                    if (!isValidPhone(phone)) {
                        phoneLayout.error = getString(R.string.invalid_phone)
                    }
                    if (!isValidEmail(email)) {
                        emailLayout.error = getString(R.string.invalid_email)
                    }
                    if (password.length < 6) {
                        passLayout.error = getString(R.string.invalid_password)
                    }
                }
            }
        }
    }

    private fun register(name: String, email: String, password: String, phone: String) {
        progressBar(true)

        val request = RegisterRequest(name, email, password, phone)
        val call = ApiConfig().getApi().registerUser(request)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>, response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@RegisterActivity, getString(R.string.accCreated), Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                    progressBar(false)

                } else {
                    val errorResponse = response.errorBody()?.string()
                    val error = Gson().fromJson(errorResponse, ErrorRegister::class.java)
                    val errorMessage = error.details.getErrorMessage()
                    Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    progressBar(false)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(
                    this@RegisterActivity, getString(R.string.failServer), Toast.LENGTH_SHORT
                ).show()
                progressBar(false)
            }
        })
    }

    private fun ErrorDetails.getErrorMessage(): String {
        val errorMessages = mutableListOf<String>()

        email?.message?.let { errorMessages.add(it) }
        phone?.getOrNull(0)?.message?.let { errorMessages.add(it) }
        name?.message?.let { errorMessages.add(it) }
        password?.message?.let { errorMessages.add(it) }

        val errorMessage = errorMessages.joinToString(" and ")
        errorMessages.clear()

        return errorMessage
    }

    private fun progressBar(visible: Boolean) {
        if (visible) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun isValidName(name: String): Boolean {
        val pattern = Regex("^[a-zA-Z ]+\$")
        return name.matches(pattern)
    }

    private fun isValidPhone(phoneNumber: String): Boolean {
        return phoneNumber.matches(Regex("\\d{10,}"))
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern = Regex("(?=.*\\d)(?=.*[a-zA-Z\\p{Punct}]).+")
        return pattern.matches(password)
    }
}


