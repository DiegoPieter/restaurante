package com.example.prueba.modelo

class CuentaMesa(val mesa: Int) {

    private val _items: MutableList<ItemMesa> = mutableListOf()

    var aceptaPropina: Boolean = true

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        agregarItem(ItemMesa(itemMenu, cantidad))   // reutiliza la otra función
    }

    fun agregarItem(itemMesa: ItemMesa) {
        _items.add(itemMesa)
    }

    fun calcularTotalSinPropina(): Int {
        return _items.sumOf { it.calcularSubtotal() }
    }

    fun calcularPropina(): Int {
        return if (aceptaPropina) calcularTotalSinPropina() / 10 else 0
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}