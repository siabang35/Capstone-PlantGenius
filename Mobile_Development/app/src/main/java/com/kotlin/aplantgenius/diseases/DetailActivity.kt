package com.kotlin.aplantgenius.diseases

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kotlin.aplantgenius.databinding.ActivityDetailBinding
import com.kotlin.aplantgenius.main.MainActivity
import java.io.File

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backDetail.setOnClickListener {
            finish()
        }

        val image = intent.getByteArrayExtra("image")
        val penyakit = intent.getStringExtra("penyakit")
        val penanganan = intent.getStringExtra("penanganan")

        binding.diseaseName.text = penyakit
        binding.diseaseDesc.text = penanganan

        Glide.with(this)
            .load(image)
            .into(binding.imageDetail)

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        binding.btnCheck.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}