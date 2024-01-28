package com.example.androidiapplikaatio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidiapplikaatio.ui.theme.AndroidiApplikaatioTheme
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
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
