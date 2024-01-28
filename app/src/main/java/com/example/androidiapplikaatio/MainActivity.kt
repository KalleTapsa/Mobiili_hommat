package com.example.androidiapplikaatio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidiapplikaatio.ui.theme.AndroidiApplikaatioTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidiApplikaatioTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Route.conversation
                ) {
                    composable(route = Route.conversation) {
                        Conversation(
                            SampleData.conversationSample,
                            navigateToScreenTwo = { navController.navigate(Route.Profile) }
                        )
                    }
                    composable(route = Route.Profile) {
                        Profile(
                            navigateBack = {
                                navController.popBackStack() // Vähiten koodia ja toimii

                                // navController.popBackStack(Route.conversation, false) Toimii myös

                                //navController.navigate(Route.conversation) { Toimi myös
                                    //popUpTo(Route.conversation) {
                                    //    inclusive = true
                                  //  }
                                //}

                            }
                        )
                    }
                }
            }
        }
    }
}
object Route {
    const val conversation = "conversation"
    const val Profile = "Profile"
}
