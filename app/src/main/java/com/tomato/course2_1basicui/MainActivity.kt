package com.tomato.course2_1basicui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tomato.course2_1basicui.databinding.ActivityMainBinding
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView1.requestLayout()

        binding.buttonGetRandomImage.setOnClickListener {
            loadRandomImage()
        }
        binding.buttonGetRandomImage.setOnLongClickListener {
            showToast()
        }

        binding.editText1.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener loadRandomImage()
            }
            return@setOnEditorActionListener false
        }

        binding.checkBox1.setOnClickListener {
            updateUi()
        }
        updateUi()
    }

    private fun updateUi() {
        if (binding.checkBox1.isChecked) {
            binding.editText1.visibility = View.VISIBLE
        } else {
            binding.editText1.visibility = View.GONE
        }
    }

    private fun showToast(): Boolean {
        Toast.makeText(this, "Long click", Toast.LENGTH_LONG).show()
        return false
    }

    private fun loadRandomImage(): Boolean {
        val category = binding.editText1.text.toString()
        if (category.isBlank() && binding.checkBox1.isChecked) {
            binding.editText1.error = "Set category"
            return true
        }

        val encodedCategory = URLEncoder.encode(category, "UTF-8")
        Glide.with(this)
            .load("https://source.unsplash.com/random/800x600?$encodedCategory")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.imageView1)
        return false
    }
}