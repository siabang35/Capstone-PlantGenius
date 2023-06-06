package com.kotlin.aplantgenius.diseases.check

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.aplantgenius.databinding.ActivityCheckBinding
import com.kotlin.aplantgenius.diseases.detail.DetailActivity

class CheckActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backCheck.setOnClickListener {
            finish()
        }

        binding.detailButton.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}
