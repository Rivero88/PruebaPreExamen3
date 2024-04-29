package com.example.pruebapreexamen3.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SupermercadoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SupermercadoUiState())
    val uiState: StateFlow<SupermercadoUiState> = _uiState.asStateFlow()

    var nombreProducto by mutableStateOf("")
    var precioProducto by mutableStateOf("")

    fun nuevoProducto(nuevoNombreProducto: String){
        nombreProducto = nuevoNombreProducto
    }
    fun nuevoPrecio (nuevoPrecioProducto: String){
        precioProducto = nuevoPrecioProducto
    }

    fun annadirActualizarProducto(productos: ArrayList<Producto>, nombreProductoTextEditor: String,
                                  precioProductoTextEditor: String){
        var precio = precioProductoTextEditor.toInt()
        var textoUltAccionAct = ""
        var existe = false
        var productoNuevoAct = Producto("",0)


        for(prod in productos){
            if(prod.nombre.equals(nombreProductoTextEditor) && prod.precio == precio){
                existe = true
                textoUltAccionAct = "No se ha modificado nada del producto ${prod.nombre}, el precio es el mismo."
            }else if (prod.nombre.equals(nombreProductoTextEditor) && prod.precio != precio){
                existe = true
                prod.precio = precio
                textoUltAccionAct = "Del producto ${prod.nombre} se ha modificado el precio: ${prod.precio}€ a $precio€."
            }
        }

        if(!existe){
            productoNuevoAct = Producto(nombreProductoTextEditor,precio)
            productos.add(productoNuevoAct)
            textoUltAccionAct = "Se ha añadido el producto ${nombreProductoTextEditor} con el precio $precio."}

        _uiState.update {
            actualizar -> actualizar.copy(
                textoUltAccion= textoUltAccionAct,
                productosNuevos= productos,
        )
        }

    }

}