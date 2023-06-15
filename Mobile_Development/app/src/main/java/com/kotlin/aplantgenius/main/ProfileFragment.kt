package com.kotlin.aplantgenius.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.auth.LoginActivity
import com.kotlin.aplantgenius.data.ApiConfig
import com.kotlin.aplantgenius.data.LogoutResponse
import com.kotlin.aplantgenius.databinding.FragmentProfileBinding
import retrofit2.Call

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", null)
        val email = sharedPref.getString("email", null)
        val name = sharedPref.getString("name", null)

        binding.userName.text = name
        binding.userEmail.text = email

        if (token != null) {
            binding.logoutButton.setOnClickListener {
                logout(token)
                with(sharedPref.edit()) {
                    remove("token")
                    remove("email")
                    remove("name")
                    apply()
                }

                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
    }

    private fun logout(token: String) {
        val call = ApiConfig().getApi().logout(token)

        call.enqueue(object : retrofit2.Callback<LogoutResponse> {
            override fun onResponse(
                call: Call<LogoutResponse>,
                response: retrofit2.Response<LogoutResponse>
            ) {
                if (isAdded) {
                    if (response.isSuccessful) {
                        val logoutResponse = response.body()
                        logoutResponse?.let {
                            val message = it.message
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), getString(R.string.anError), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                if (isAdded) {
                    Toast.makeText(
                        requireContext(), getString(R.string.failServer), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}

