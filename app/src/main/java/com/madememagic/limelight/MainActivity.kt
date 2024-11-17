package com.madememagic.limelight

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.madememagic.limelight.navigation.KeeperDestination
import com.madememagic.limelight.navigation.KeeperNavigation
import com.madememagic.limelight.presentation.auth.AuthViewModel
import com.madememagic.limelight.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var showNotificationPermissionDialog = mutableStateOf(false)
    private var showExactAlarmPermissionDialog = mutableStateOf(false)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        checkAndRequestPermissions()

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (showNotificationPermissionDialog.value) {
                        AlertDialog(
                            onDismissRequest = { showNotificationPermissionDialog.value = false },
                            title = { Text("Notification Permission") },
                            text = { Text("We need notification permission to send you important reminders about interesting moview") },
                            confirmButton = {
                                TextButton(onClick = {
                                    showNotificationPermissionDialog.value = false
                                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                }) {
                                    Text("Continue")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { 
                                    showNotificationPermissionDialog.value = false 
                                }) {
                                    Text("Not Now")
                                }
                            }
                        )
                    }


                    val viewModel: AuthViewModel = hiltViewModel()
                    val isUserAuthenticated by viewModel.isUserAuthenticated.collectAsStateWithLifecycle()
                    val navController = rememberNavController()

                    KeeperNavigation(
                        navController = navController,
                        logout = {
                            navController.navigate(KeeperDestination.Auth) {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        },
                        startDestination = if (isUserAuthenticated) {
                            KeeperDestination.Home
                        } else {
                            KeeperDestination.Auth
                        }
                    )
                }
            }
        }
    }

    private fun checkAndRequestPermissions() {
        // Check notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission already granted
                }
                else -> {
                    showNotificationPermissionDialog.value = true
                }
            }
        }

        // Check exact alarm permission for Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                showExactAlarmPermissionDialog.value = true
            }
        }
    }


    companion object {
        private const val TAG = "MainActivity"
    }
}
