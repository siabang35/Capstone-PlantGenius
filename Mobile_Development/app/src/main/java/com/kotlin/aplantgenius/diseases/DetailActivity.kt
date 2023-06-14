package com.kotlin.aplantgenius.diseases

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kotlin.aplantgenius.databinding.ActivityDetailBinding
import com.kotlin.aplantgenius.main.MainActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = intent.getByteArrayExtra("image")
        val penyakit = intent.getStringExtra("penyakit")
        val penanganan = intent.getStringExtra("penanganan")

        binding.diseaseName.text = penyakit
        binding.diseaseDesc.text = penanganan

        Glide.with(this)
            .load(image)
            //.placeholder(R.drawable.placeholder_image)
            //.error(R.drawable.error_image)
            .into(binding.imageDetail)

        binding.backDetail.setOnClickListener {
            finish()
        }

        binding.btnCheck.setOnClickListener {
            finish()
        }

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}