package com.example.prueba

import android.os.Bundle
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.example.prueba.modelo.CuentaMesa
import com.example.prueba.modelo.ItemMenu
import com.example.prueba.modelo.ItemMesa
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val pastelDeChoclo = ItemMenu("Pastel de Choclo", 12000)
    private val cazuela = ItemMenu("Cazuela", 10000)

    private val itemPastel = ItemMesa(pastelDeChoclo, 0)
    private val itemCazuela = ItemMesa(cazuela, 0)

    private val cuentaMesa = CuentaMesa(1)

    private val formatoPesos: NumberFormat =
        NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CL")).apply {
            maximumFractionDigits = 0
        }

    private lateinit var etCantPastel: EditText
    private lateinit var etCantCazuela: EditText
    private lateinit var tvSubtotalPastel: TextView
    private lateinit var tvSubtotalCazuela: TextView
    private lateinit var tvComida: TextView
    private lateinit var tvPropina: TextView
    private lateinit var tvTotal: TextView
    private lateinit var swPropina: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        conectarVistas()

        cuentaMesa.agregarItem(itemPastel)
        cuentaMesa.agregarItem(itemCazuela)

        cuentaMesa.aceptaPropina = swPropina.isChecked

        configurarEventos()
        actualizarMontos()
    }

    private fun conectarVistas() {
        etCantPastel = findViewById(R.id.etCantPastel)
        etCantCazuela = findViewById(R.id.etCantCazuela)
        tvSubtotalPastel = findViewById(R.id.tvSubtotalPastel)
        tvSubtotalCazuela = findViewById(R.id.tvSubtotalCazuela)
        tvComida = findViewById(R.id.tvComida)
        tvPropina = findViewById(R.id.tvPropina)
        tvTotal = findViewById(R.id.tvTotal)
        swPropina = findViewById(R.id.swPropina)
    }

    private fun configurarEventos() {
        etCantPastel.doAfterTextChanged { texto ->
            itemPastel.cantidad = texto.toString().toIntOrNull() ?: 0
            actualizarMontos()
        }

        etCantCazuela.doAfterTextChanged { texto ->
            itemCazuela.cantidad = texto.toString().toIntOrNull() ?: 0
            actualizarMontos()
        }

        swPropina.setOnCheckedChangeListener { _, estaActivado ->
            cuentaMesa.aceptaPropina = estaActivado
            actualizarMontos()
        }
    }

    private fun actualizarMontos() {
        tvSubtotalPastel.text = formatoPesos.format(itemPastel.calcularSubtotal())
        tvSubtotalCazuela.text = formatoPesos.format(itemCazuela.calcularSubtotal())
        tvComida.text = formatoPesos.format(cuentaMesa.calcularTotalSinPropina())
        tvPropina.text = formatoPesos.format(cuentaMesa.calcularPropina())
        tvTotal.text = formatoPesos.format(cuentaMesa.calcularTotalConPropina())
    }
}