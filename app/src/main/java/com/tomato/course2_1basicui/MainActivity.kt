package com.tomato.course2_1basicui

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.RadioButton
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            loadRandomImage()
        }
    }

    private fun showToast(): Boolean {
        Toast.makeText(this, "Long click", Toast.LENGTH_LONG).show()
        return false
    }

    private fun loadRandomImage(): Boolean {
        val checkedId = binding.radioGroup.checkedRadioButtonId
        val keyword = binding.radioGroup.findViewById<RadioButton>(checkedId).text.toString()


        val encodedCategory = URLEncoder.encode(keyword, "UTF-8")

        binding.progressBar.visibility = View.VISIBLE

        Glide.with(this)
            .load("https://source.unsplash.com/random/800x600?$encodedCategory")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady( resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }

            })
            .into(binding.imageView1)
        return false
    }
}