package com.pghaz.spotify.webapi.auth.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.pghaz.spotify.webapi.auth.SpotifyAuthorizationCallback
import com.pghaz.spotify.webapi.auth.SpotifyAuthorizationClient
import io.github.kaaes.spotify.webapi.core.models.UserPrivate
import net.openid.appauth.TokenResponse

abstract class BaseSpotifyActivity : AppCompatActivity(), SpotifyAuthorizationCallback.Authorize,
        SpotifyAuthorizationCallback.RefreshToken {

    companion object {
        const val EXTRA_USING_PENDING_INTENT = "EXTRA_USING_PENDING_INTENT"
    }

    lateinit var spotifyAuthClient: SpotifyAuthorizationClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        spotifyAuthClient = SpotifyAuthorizationClient.Builder(
                getString(R.string.spotify_client_id),
                getString(R.string.spotify_redirect_uri))
                /*.setScopes(arrayOf(
                        "user-top-read",
                        "user-read-recently-played"
                ))*/
                .setFetchUserAfterAuthorization(true)
                .setCustomTabColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .build(this)

        spotifyAuthClient.setDebugMode(true)
        spotifyAuthClient.addAuthorizationCallback(this)
        spotifyAuthClient.addRefreshTokenCallback(this)
    }

    override fun onStart() {
        super.onStart()
        spotifyAuthClient.onStart()

        val usingPendingIntent = intent?.getBooleanExtra(EXTRA_USING_PENDING_INTENT, false)

        // Note: Use this if you're using: authorize(context, completionPendingIntent, cancelPendingIntent)
        if (intent != null && usingPendingIntent == true) {
            // At this point it is authorized but we don't have access token yet.
            // We get it at when onAuthorizationSucceed() is called
            spotifyAuthClient.onCompletionActivity(intent)
        }
    }

    // Note: Use this if you're using: spotifyAuthClient.authorize(context, requestCode)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // At this point it is authorized but we don't have access token yet.
        // We get it at when onAuthorizationSucceed() is called
        spotifyAuthClient.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStop() {
        super.onStop()
        spotifyAuthClient.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        spotifyAuthClient.onDestroy()
    }

    override fun onAuthorizationStarted() {

    }

    override fun onAuthorizationCancelled() {

    }

    override fun onAuthorizationFailed(error: String?) {

    }

    override fun onAuthorizationRefused(error: String?) {

    }

    override fun onAuthorizationSucceed(tokenResponse: TokenResponse?, user: UserPrivate?) {

    }

    override fun onRefreshAccessTokenStarted() {

    }

    override fun onRefreshAccessTokenSucceed(tokenResponse: TokenResponse?, user: UserPrivate?) {

    }
}