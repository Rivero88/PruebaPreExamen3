package com.example.pruebapreexamen3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pruebapreexamen3.data.Producto
import com.example.pruebapreexamen3.data.SupermercadoViewModel
import com.example.pruebapreexamen3.ui.theme.PruebaPreExamen3Theme
import com.example.pruebapreexamen3.data.DataSource
import com.example.pruebapreexamen3.data.SupermercadoUiState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaPreExamen3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPrincipal(viewModelSupermercado = viewModel(), productos = DataSource.productos)
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(modifier: Modifier = Modifier, viewModelSupermercado: SupermercadoViewModel,
                      productos: ArrayList<Producto>) {
    val uiState by viewModelSupermercado.uiState.collectAsState()
    uiState.productosNuevos = productos
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier.weight(1f)) {
            Text(
                text = "Hola, soy alumn@ Ester Rivero",
                modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(start = 20.dp, top = 50.dp),
                textAlign = TextAlign.Center
            )
            PantallaPrincipalProductos(
                viewModelSupermercado = viewModelSupermercado,
                productos = productos
            )
        }
        Text(
            text = "${uiState.textoUltAccion}",
            modifier.fillMaxWidth()
                .background(Color.LightGray)
                .padding(50.dp),
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipalProductos(modifier: Modifier = Modifier, viewModelSupermercado: SupermercadoViewModel,
                               productos: ArrayList<Producto>){
    Row (modifier){
        Column(modifier.weight(0.5f)) {
            TextField(value = viewModelSupermercado.nombreProducto,
                onValueChange = { viewModelSupermercado.nuevoProducto(it)},
                label = { Text(text = "Nombre")},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
            TextField(value = viewModelSupermercado.precioProducto,
                onValueChange = { viewModelSupermercado.nuevoPrecio(it)},
                label = { Text(text = "Precio")},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
            Button(onClick = { viewModelSupermercado.annadirActualizarProducto(productos,
                viewModelSupermercado.nombreProducto, viewModelSupermercado.precioProducto) },
                modifier
                    .fillMaxWidth()
                    .padding(20.dp)) {
                Text(text = "Añadir/Actualizar producto")
            }
        }
        LazyVerticalGrid(columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.weight(0.5f)){
            items(productos){producto->
                Card (modifier.padding(8.dp)) {
                    Text(text = "Nombre: ${producto.nombre}",
                        modifier
                            .fillMaxWidth()
                            .background(Color.Yellow)
                            .padding(20.dp))
                    Text(text = "Precio: ${producto.precio}",
                        modifier
                            .fillMaxWidth()
                            .background(Color.Cyan)
                            .padding(20.dp))
                }
            }
        }
    }
}

