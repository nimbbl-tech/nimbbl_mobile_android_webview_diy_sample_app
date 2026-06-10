package com.example.webviewsample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.webviewsample.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoadUrl.setOnClickListener {
            val url = binding.etUrl1.text.toString().trim()
            if (isValidUrl(url)) {
                val intent = Intent(this, WebViewActivity::class.java).apply {
                    putExtra(WebViewActivity.EXTRA_URL, url)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.invalid_url), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidUrl(url: String): Boolean {
        if (url.isEmpty()) return false
        return try {
            val formatted = if (!url.startsWith("http://") && !url.startsWith("https://")) "https://$url" else url
            val uri = Uri.parse(formatted)
            uri.scheme != null && !uri.host.isNullOrEmpty()
        } catch (e: Exception) {
            false
        }
    }
}
