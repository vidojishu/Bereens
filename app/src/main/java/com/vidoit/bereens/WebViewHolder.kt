package com.vidoit.bereens

import android.content.Context
import android.graphics.Color
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

object WebViewHolder {
    var webView: WebView? = null

    fun initWebView(context: Context) {
        if (webView == null) {
            webView = WebView(context).apply {
                setBackgroundColor(Color.WHITE) // Important pour éviter l'écran transparent

                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    cacheMode = WebSettings.LOAD_DEFAULT
                }

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        println(" WebView chargée : $url")
                    }
                }

                loadUrl("https://etudebiblique.eeu-eglise.org/")
            }
        }
    }
}
