package com.kotlin.aplantgenius.diseases

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.data.ListHistory
import com.kotlin.aplantgenius.databinding.ActivityDetailBinding
import com.kotlin.aplantgenius.main.MainActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent?.extras
        val ids = extras?.getString(EXTRA_ID)
        val name = extras?.getString(EXTRA_NAME)
        val desc = extras?.getString(EXTRA_DESC)
        val image = extras?.getString(EXTRA_IMG)

        if (image != null || name != null || desc != null) {
            progressBar(true)
            binding.diseaseName.text = name
            binding.diseaseDesc.text = desc

            Glide.with(this)
                .load(image)
                .error(R.drawable.image_detail)
                .into(binding.imageDetail)
            progressBar(false)
        }

        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        if (ids != null) {
            val id = ids.toInt()
            viewModel.isLoading.observe(this) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            viewModel.detailHistory.observe(this) {
                setDetail(it)
            }

            viewModel.getDetail(token, id)
        }

        binding.backDetail.setOnClickListener {
            finish()
        }

        binding.btnCheck.setOnClickListener {
            val intent = Intent(this@DetailActivity, ScanActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.btnHome.setOnClickListener {
            val intent = Intent(this@DetailActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun setDetail(detail: ListHistory?) {
        if (detail != null) {
            progressBar(true)
            Glide.with(this@DetailActivity)
                .load(detail.image)
                .into(binding.imageDetail)

            binding.diseaseName.text = detail.name
            binding.diseaseDesc.text = detail.desc
            progressBar(false)
        }
    }

    private fun progressBar(visible: Boolean) {
        binding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_IMG = "extra_img"
    }
}
