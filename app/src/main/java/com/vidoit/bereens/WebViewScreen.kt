package com.vidoit.bereens

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch

import android.webkit.WebResourceError
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.composable


import android.content.Intent
import android.os.Handler
import android.os.Looper

private const val BASE_URL = "https://etudebiblique.eeu-eglise.org/"

@Composable
fun WebViewScreen() {
    val context = LocalContext.current
    val isOnline = isNetworkAvailable(context)
    val targetUrl = BASE_URL  // URL fixe pour votre application

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        when {
            !isOnline -> {
                ErrorMessage("Vérifiez votre connexion internet")
            }
            else -> {
                WebViewContent(targetUrl)
            }
        }
    }
}

@Composable
private fun WebViewContent(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    // Empêche la navigation vers d'autres domaines
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        url: String
                    ): Boolean {
                        return if (!url.startsWith(BASE_URL)) {
                            true  // Bloque les URLs externes
                        } else {
                            false  // Autorise les URLs du domaine
                        }
                    }
                }
                settings.javaScriptEnabled = true
                loadUrl(url)

            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun ErrorMessage(text: String) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}

private fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        @Suppress("DEPRECATION")
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}
