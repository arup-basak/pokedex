package com.arup.pokedex

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arup.pokedex.ui.theme.PokedexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ktorApiClient = KtorApiClient(this@MainActivity)

        setContent {
            PokedexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var pokemonList by remember { mutableStateOf(getEmptyPokemonList()) }

                    LaunchedEffect(pokemonList) {
                        try {
                            pokemonList = ktorApiClient
                                .getPokemonList(0,25)
                        }
                        catch (e: Exception) {
                            Log.d("fuhsdifsdf", e.message.toString())
                        }
                    }

                    Column {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            items(pokemonList.results) {
                                PokemonCard(name = it.name)
                            }
                        }

                        Row (
                            modifier = Modifier
                        ) {
                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .weight(1f),
                                enabled = !pokemonList.previous.isNullOrEmpty()
                            ) {
                                Icon (
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Arrow Back Icon"
                                )
                                Text(text = "Next")
                            }

                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .weight(1f),
                                enabled = !pokemonList.next.isNullOrEmpty()
                            ) {
                                Text(text = "Previous")
                                Icon (
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "Arrow Forward Icon"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonCard(
    name: String
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = name,
            modifier = Modifier
                .padding(12.dp)
        )
    }
}

@Preview
@Composable
fun PokemonCardPreview() {
    PokemonCard(
        "charmeleon"
    )
}