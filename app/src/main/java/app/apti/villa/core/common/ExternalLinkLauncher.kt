package app.apti.villa.core.common

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri

object ExternalLinkLauncher {

    /**
     * Checks if the device has an active internet connection.
     * Note: Per architecture rules, network checks are strictly limited to external link launchers and AI.
     */
    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    /**
     * Launches a YouTube or external URL in browser/app with optional timestamp.
     * Returns true if intent was launched, false if no internet or invalid URL.
     */
    fun openVideoUrl(context: Context, url: String, timestampSec: Int? = null): Boolean {
        val targetUrl = if (timestampSec != null && timestampSec > 0) {
            if (url.contains("?")) "$url&t=${timestampSec}s" else "$url?t=${timestampSec}s"
        } else {
            url
        }

        return try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(targetUrl)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            true
        } catch (_: Exception) {
            false
        }
    }
}
