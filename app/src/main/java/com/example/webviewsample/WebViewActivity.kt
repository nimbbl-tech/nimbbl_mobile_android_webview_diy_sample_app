package com.example.webviewsample

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class WebViewActivity : AppCompatActivity() {

    private val TAG = "WebViewActivity"

    private lateinit var toolbar: Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var webView: WebView

    companion object {
        const val EXTRA_URL = "extra_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        toolbar = findViewById(R.id.toolbar)
        progressBar = findViewById(R.id.progressBar)
        webView = findViewById(R.id.webView)

        val url = intent.getStringExtra(EXTRA_URL)
        if (url.isNullOrEmpty()) {
            finish()
            return
        }

        setupToolbar()
        setupBackHandler()
        setupWebView()
        loadUrl(url)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun setupBackHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            // Required for payment popups (3DS, net banking redirects, etc.)
            setSupportMultipleWindows(true)
            javaScriptCanOpenWindowsAutomatically = true
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return handleUrl(request?.url?.toString())
            }

            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return handleUrl(url)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d(TAG, "Loading: $url")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d(TAG, "Finished: $url")
            }

            // Only fires for main frame errors — not sub-resources
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                if (request?.isForMainFrame == true) {
                    progressBar.visibility = View.GONE
                    Log.e(TAG, "Main frame error ${error?.errorCode}: ${error?.description} for ${request.url}")
                }
            }
        }

        // Single source of truth for progress bar visibility
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.visibility = if (newProgress < 100) View.VISIBLE else View.GONE
            }

            // Handle window.open() calls from the checkout (3DS auth, payment gateway pages)
            override fun onCreateWindow(
                view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?
            ): Boolean {
                val popupWebView = WebView(this@WebViewActivity)
                popupWebView.settings.javaScriptEnabled = true
                popupWebView.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(popupView: WebView?, request: WebResourceRequest?): Boolean {
                        val url = request?.url?.toString() ?: return false
                        Log.d(TAG, "Popup navigating to: $url")
                        if (handleUrl(url)) return true
                        // Load payment redirect URLs in the main WebView
                        webView.loadUrl(url)
                        return true
                    }
                }
                val transport = resultMsg?.obj as? WebView.WebViewTransport
                transport?.webView = popupWebView
                resultMsg?.sendToTarget()
                return true
            }
        }
    }

    private fun handleUrl(url: String?): Boolean {
        if (url.isNullOrEmpty()) return false
        if (!isUpiUrl(url)) return false

        Log.d(TAG, "Intercepting UPI URL: $url")
        return try {
            val baseIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            val resolveInfo = packageManager.resolveActivity(
                baseIntent, android.content.pm.PackageManager.MATCH_DEFAULT_ONLY
            )

            val launchIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                resolveInfo?.activityInfo?.packageName?.let { setPackage(it) }
                // No FLAG_ACTIVITY_NEW_TASK — we are already in an Activity context.
                // Adding it causes cold-start launches to go to background on some devices.
            }

            startActivity(launchIntent)
            Toast.makeText(this, "Opening UPI app...", Toast.LENGTH_SHORT).show()
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to launch UPI app for: $url", e)
            Toast.makeText(this, "No UPI app found to handle this link", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun isUpiUrl(url: String): Boolean {
        // Never intercept standard web URLs
        if (url.startsWith("http://", ignoreCase = true) ||
            url.startsWith("https://", ignoreCase = true)) return false

        if (url.startsWith("upi://", ignoreCase = true)) return true

        val upiSchemes = listOf(
            "gpay://", "phonepe://", "paytm://", "bhim://",
            "amazonpay://", "mobikwik://", "freecharge://",
            "jupiter://", "slice://", "cred://"
        )
        return upiSchemes.any { url.startsWith(it, ignoreCase = true) }
    }

    private fun loadUrl(url: String) {
        val formatted = if (!url.startsWith("http://") && !url.startsWith("https://")) "https://$url" else url
        webView.loadUrl(formatted)
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.clearHistory()
        webView.destroy()
    }
}
