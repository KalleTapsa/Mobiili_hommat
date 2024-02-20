package com.example.androidiapplikaatio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidiapplikaatio.ui.theme.AndroidiApplikaatioTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AccountDatabase::class.java,
            "account-db"
        ).build()
    }
private val viewModel by viewModels<AccountViewModel>(
    factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AccountViewModel(db.dao) as T
            }
        }
    }
)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidiApplikaatioTheme {
                val navController = rememberNavController()
                val state by viewModel.state.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = Route.conversation
                ) {
                    composable(route = Route.conversation) {
                        Conversation(
                            SampleData.conversationSample,
                            navigateToScreenTwo = { navController.navigate(Route.Profile) },
                            navigateToNotifications = { navController.navigate(Route.Notifications) }
                        )
                    }
                    composable(route = Route.Profile) {
                        Profile(navigateBack = { navController.popBackStack(Route.conversation, false)}, state = state, onEvent = viewModel::onEvent, navigateToScreenThree = { navController.navigate(Route.FriendsList) })
                        /*Profile(
                            navigateBack = {
                                //navController.popBackStack() // Aiheutti bugeja

                                 navController.popBackStack(Route.conversation, false)

                                //navController.navigate(Route.conversation) { Toimi myös
                                    //popUpTo(Route.conversation) {
                                    //    inclusive = true
                                  //  }
                                //}

                            }
                        )*/
                    }
                    composable(route = Route.FriendsList) {
                        FriendsList(navigateBack = { navController.popBackStack(Route.Profile, false)}, state = state, onEvent = viewModel::onEvent)
                    }
                    composable(route = Route.Notifications) {
                        NotificationsScreen(navigateBack = { navController.popBackStack(Route.Profile, false)})
                    }
                }
            }
        }
    }
}
object Route {
    const val conversation = "conversation"
    const val Profile = "Profile"
    const val FriendsList = "FriendsList"
    const val Notifications = "Notifications"
}
