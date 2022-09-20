package com.example.remoteconfigtest

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.example.remoteconfigtest.ui.theme.RemoteConfigTestTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

private const val TAG = "MainActivity"


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RemoteConfigTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Firebase.messaging.getToken().addOnCompleteListener { task ->
//                        if (!task.isSuccessful) {
//                            Log.w(TAG, "Fetching FCM registration token failed", task.exception)
//                            return@addOnCompleteListener
//                        }
//
//                        // Get new FCM registration token
//                        val token = task.result
//
//                        // Log and toast
//                        val msg = "FCM Registration token: $token"
//                        Log.d(TAG, msg)
//                        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                    }
//                    Text("hola")

                    UiScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val ctx = LocalContext.current
    var remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    var configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600
    }

    
    remoteConfig.setConfigSettingsAsync(configSettings)
    var text by remember {
        mutableStateOf(" world")
    }

    remoteConfig.fetchAndActivate().addOnCompleteListener{ task ->
        if (task.isSuccessful) {
            val updated = task.result
            Log.d(TAG, "Config params updated: $updated")
            Log.d(TAG, "Greeting: ${remoteConfig.get("cfg").asString()}")
            Log.d(TAG, "Greeting: ${remoteConfig.get("cfg1").asString()}")
            Log.d(TAG, "Greeting: ${remoteConfig.get("test").asString()}")
            Log.d(TAG, "Greeting: ${remoteConfig.get("test1").asString()}")
            text = remoteConfig.get("cfg").asString()
            Toast.makeText(
                ctx, "Fetch and activate succeeded",
                Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(ctx, "Fetch failed",
                Toast.LENGTH_SHORT).show()
        }
    }

    Text(text = "Hello ${text} !")


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RemoteConfigTestTheme {
        UiScreen()
    }
}