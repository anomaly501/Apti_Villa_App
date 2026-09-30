package app.apti.villa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.apti.villa.core.designsystem.theme.AptiVillaTheme
import app.apti.villa.core.navigation.AptiVillaNavHost
import app.apti.villa.domain.model.AppThemeMode
import app.apti.villa.domain.model.UserPreferences
import app.apti.villa.domain.repository.PreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val userPrefs by preferencesRepository.userPreferences.collectAsStateWithLifecycle(
                initialValue = UserPreferences()
            )
            val isDark = when (userPrefs.themeMode) {
                AppThemeMode.SYSTEM -> isSystemInDarkTheme()
                AppThemeMode.LIGHT -> false
                AppThemeMode.DARK -> true
            }

            AptiVillaTheme(
                darkTheme = isDark,
                dynamicColor = userPrefs.dynamicColor,
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AptiVillaNavHost()
                }
            }
        }
    }
}
