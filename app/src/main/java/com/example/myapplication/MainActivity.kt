package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {

    private val tag = "CICLO_VIDA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate")

        setContent {
            val viewModel: ReviewViewModel = viewModel(
                factory = ReviewViewModelFactory(applicationContext, this)
            )

            ReviewScreen(viewModel)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")
    }
}

@Composable
fun ReviewScreen(viewModel: ReviewViewModel) {
    val comment by viewModel.comment.collectAsState()

    // Bloquea el botón atrás si hay texto
    BackHandler(enabled = comment.isNotEmpty()) {
        Log.d("BACK_HANDLER", "No se puede salir, hay comentario")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Sistema de Reseñas",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(text = "Puntaje: ${viewModel.rating.floatValue.toInt()}")

        Slider(
            value = viewModel.rating.floatValue,
            onValueChange = { viewModel.updateRating(it) },
            valueRange = 1f..5f,
            steps = 3
        )

        OutlinedTextField(
            value = comment,
            onValueChange = { viewModel.updateComment(it) },
            label = { Text("Comentario") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4
        )

        Button(
            onClick = { viewModel.saveDraft() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Borrador")
        }
    }
}