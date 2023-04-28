# Splash Screen Example
Ejemplo de cómo crear una Splash Screen utilizando la nueva API de Splash Screen de Android

Pasos a seguir para hacer una Splash Screen básica

1. Añadir la dependencia
```groovy
dependencies {
    // Dependencia del Splash Screen API
    implementation "androidx.core:core-splashscreen:1.0.1"
}
```

2. Crear un nuevo recurso en la carpeta "values" llamado ``splash.xml``

![image](https://user-images.githubusercontent.com/36543483/235236989-8f903775-b66f-44bd-889f-5d7249e0f7f3.png)

3. Inicializar los atributos en el XML.

**Nota:** el ícono del Splash Screen debe tenerse de antemano en la carpeta drawable como un _vector asset_

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="Theme.App.Starting" parent="Theme.SplashScreen">
        <!--        El color de fondo-->
        <item name="windowSplashScreenBackground">#FFFFFF</item>
        <!--        El ícono a mostrar. Puede ser o no animado-->
        <item name="windowSplashScreenAnimatedIcon">@drawable/ic_baseline_bedroom_baby_24</item>
        <!--        La duración de la animación si fuera animado. Para íconos animados, coincide con la duración del splash-->
        <item name="windowSplashScreenAnimationDuration">300</item>
        <!--        Volver a setear el tema del proyecto luego de mostrar el splash screen-->
        <item name="postSplashScreenTheme">@style/Theme.SplashScreenExample</item>
        <!--        El color del fondo del círculo que rodea el ícono. Sólo para Android 12 en adelante-->
        <!--        <item name="windowSplashScreenIconBackgroundColor"/>-->
    </style>
</resources>
```

4. Instalar la Splash Screen luego del ``onCreate()`` y antes del ``setContent{}`` de la ``MainActivity.kt``.

```kotlin
installSplashScreen()
```

5. Si se quieren realizar operaciones en _background_ mientras se carga el Splash, se crea un view model para la Splash Screen y luego se aplica la configuración en la declaración de la Splash Screen

**SplashViewModel.kt**
```kotlin
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(300)
            _isLoading.value = false
        }
    }
}
```

**MainActivity.kt**
```kotlin
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
            //this.setOnExitAnimationListener{}
        }

        setContent {...}
    }
}
```
