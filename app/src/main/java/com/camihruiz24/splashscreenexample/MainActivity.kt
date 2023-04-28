package com.camihruiz24.splashscreenexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.camihruiz24.splashscreenexample.ui.theme.SplashScreenExampleTheme

class MainActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Se puede instanciar la splash screen si se necesita para después.
         * Con apply se aplican las modificaciones necesarias inmediatamente por lo que no es necesario instanciarla.
          */
        val splashScreen: SplashScreen = installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
            // Cuando queremos hacer algo justo luego de salir de la Splash Screen
            this.setOnExitAnimationListener{}
        }

        setContent {
            SplashScreenExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SplashScreenExampleTheme {
        Greeting("Android")
    }
}