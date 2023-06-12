package com.abdulaziz.gallaryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import com.abdulaziz.gallaryapp.ui.GalleryViewModel
import com.abdulaziz.gallaryapp.ui.destinations.NavGraphs
import com.abdulaziz.gallaryapp.ui.theme.SomeWhatWhite
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(backgroundColor = SomeWhatWhite) { _ ->
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
