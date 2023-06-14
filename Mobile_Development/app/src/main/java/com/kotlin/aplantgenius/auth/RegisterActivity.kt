package com.kotlin.aplantgenius.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNameValidation()
        setupEmailValidation()
        setupPasswordValidation()

        binding.backButton.setOnClickListener {
            finish()
        }
        binding.backText.setOnClickListener {
            finish()
        }

        binding.registerButton.setOnClickListener {
            val name = binding.registerName.text.toString()
            val email = binding.registerEmail.text.toString()
            val password = binding.registerPassword.text.toString()

            if (isInputValid(name, email, password)) {
                register(name, email, password)
            }
        }
    }

    private fun register(name: String, email: String, password: String) {
        progressBar(true)
        viewModel.register(
            name,
            email,
            password,
            onSuccess = {
                Toast.makeText(this, getString(R.string.accCreated), Toast.LENGTH_SHORT).show()
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
                progressBar(false)
            },
            onFailure = { errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                progressBar(false)
            }
        )
    }

    private fun setupNameValidation() {
        binding.registerName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateName(s.toString().trim())
            }
        })
    }

    private fun setupEmailValidation() {
        binding.registerEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                validateEmail(s.toString().trim())
            }
        })
    }

    private fun setupPasswordValidation() {
        binding.registerPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword(s.toString().trim())
            }
        })
    }

    private fun validateName(name: String) {
        if (name.isEmpty()) {
            binding.nameLayout.error = getString(R.string.invalid_name)
        } else if (!isValidName(name)) {
            binding.nameLayout.error = getString(R.string.invalid_nameNumber)
        } else {
            binding.nameLayout.error = null
        }
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

    private fun isInputValid(name: String, email: String, password: String): Boolean {
        var isValid = true

        if (!isValidName(name) || name.isEmpty()) {
            binding.nameLayout.error = getString(R.string.invalid_name)
            isValid = false
        }

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

    private fun isValidName(name: String): Boolean {
        val pattern = Regex("^[a-zA-Z ]+\$")
        return name.matches(pattern)
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern = Regex("(?=.*\\d)(?=.*[a-zA-Z\\p{Punct}]).+")
        return pattern.matches(password)
    }
}
