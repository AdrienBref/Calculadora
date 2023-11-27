package com.dam.calculadora

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dam.calculadora.databinding.ActivityMainBinding
import utils.evaluaSimbolo
import utils.resOperacion


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private var textoOperation: String = "";
    private val texto : Int = 0;
    var resultado = "" ;
    var operacion = ""
    var num = ""
    private lateinit var configuration: Configuration

    val listaElementos : ArrayList <String>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        configuration = resources.configuration
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        resultado = savedInstanceState?.getString("resultado") ?: ""
        operacion = savedInstanceState?.getString("operacion") ?: ""
        num = savedInstanceState?.getString("num") ?: ""

        binding.textodown.text = resultado.toString()
        binding.textosuper.text = operacion

        val buttonIds = mutableListOf<Button>()

        binding.javaClass.declaredFields.forEach { field ->
            if (field.type == Button::class.java) {
                field.isAccessible = true
                val button = field.get(binding) as? Button
                button?.let {
                    buttonIds.add(it)
                }
            }
        }

        buttonIds?.forEach {button ->
            button?.setOnClickListener(this)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("resultado",resultado)
        outState.putString("operacion", operacion)
        outState.putString("num", num)
    }

    override fun onClick(v: View?) {

        var textoLengthDown: Int? = binding.textodown.length()
        var textoLengthSuper: Int? = binding.textosuper.length()
        val myTextViewDown = findViewById<TextView>(com.dam.calculadora.R.id.textodown)
        val myTextViewSuper = findViewById<TextView>(com.dam.calculadora.R.id.textosuper)
        var ultimoValorPulsado = ""
        var pulsadoOperador : Boolean = false
        val textoPequeño : Float = 70f
        val textoNormal : Float =  100f

        if(v?.id == com.dam.calculadora.R.id.AC) {

            if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {

                myTextViewDown.setTextSize(textoNormal)
                myTextViewSuper.setTextSize(textoNormal)
            }

            binding.textodown.text = ""
            binding.textosuper.text = ""
            listaElementos?.clear()
            textoLengthDown = 0
            operacion = ""
            resultado = ""

        }

        if (textoLengthDown != null) {
            if(textoLengthDown < 10) {
                when (v?.id) {
                    com.dam.calculadora.R.id.uno -> {
                        binding.textodown.append("1")
                        ultimoValorPulsado = "1"
                    }
                    com.dam.calculadora.R.id.dos -> {
                        binding.textodown.append("2")
                        ultimoValorPulsado = "2"
                    }
                    com.dam.calculadora.R.id.tres -> {
                        binding.textodown.append("3")
                        ultimoValorPulsado = "3"
                    }
                    com.dam.calculadora.R.id.cuatro -> {
                        binding.textodown.append("4")
                        ultimoValorPulsado = "4"
                    }
                    com.dam.calculadora.R.id.cinco -> {
                        binding.textodown.append("5")
                        ultimoValorPulsado = "5"
                    }
                    com.dam.calculadora.R.id.seis -> {
                        binding.textodown.append("6")
                        ultimoValorPulsado = "6"
                    }
                    com.dam.calculadora.R.id.siete -> {
                        binding.textodown.append("7")
                        ultimoValorPulsado = "7"
                    }
                    com.dam.calculadora.R.id.ocho -> {
                        binding.textodown.append("8")
                        ultimoValorPulsado = "8"
                    }
                    com.dam.calculadora.R.id.nueve -> {
                        binding.textodown.append("9")
                        ultimoValorPulsado = "9"
                    }
                    com.dam.calculadora.R.id.coma -> {
                        binding.textodown.append(".")
                        ultimoValorPulsado = "."
                    }
                    com.dam.calculadora.R.id.resultado -> {
                        ultimoValorPulsado = "="
                    }
                    com.dam.calculadora.R.id.PI -> {
                        ultimoValorPulsado = "Pi"
                        binding.textodown.text = "3.14"
                    }
                }

               if(pulsadoOperador != true) {
                    when(v?.id) {
                        com.dam.calculadora.R.id.suma -> {
                            ultimoValorPulsado = "+"
                        }
                        com.dam.calculadora.R.id.resta -> {
                            ultimoValorPulsado = "-"
                        }
                        com.dam.calculadora.R.id.multiplicacion -> {
                            ultimoValorPulsado = "*"
                        }
                        com.dam.calculadora.R.id.division -> {
                            ultimoValorPulsado = "/"
                        }
                        com.dam.calculadora.R.id.seno -> {
                            ultimoValorPulsado = "sin"
                        }
                        com.dam.calculadora.R.id.porcentaje -> {
                            ultimoValorPulsado = "%"
                        }
                        com.dam.calculadora.R.id.coseno -> {
                            ultimoValorPulsado = "cos"
                        }
                        com.dam.calculadora.R.id.tangente -> {
                            ultimoValorPulsado = "tan"
                        }
                        com.dam.calculadora.R.id.raizCuadrada -> {
                            ultimoValorPulsado = "raz"
                        }

                    }
                }
                //TODO: arreglar el tema del 0
                if(textoLengthDown != 0 && v?.id == com.dam.calculadora.R.id.cero) {
                    binding.textodown.append("0")
                    ultimoValorPulsado = "0"
                }
            }

            num = binding.textodown.text.toString()
        }

        if(evaluaSimbolo(ultimoValorPulsado)) {

            if(listaElementos?.size== 0) {
                listaElementos?.add(binding.textodown.text.toString())
            }
            listaElementos?.add(ultimoValorPulsado)
            pulsadoOperador = evaluaSimbolo(ultimoValorPulsado)

            binding.textodown.text = ""

        }

        when(ultimoValorPulsado) {
            "=" -> {
                listaElementos?.add(binding.textodown.text.toString())

                if(listaElementos?.size == 3) {

                    resOperacion(listaElementos)

                    binding.textosuper.text = ""
                    listaElementos?.forEach{e->
                        binding.textosuper.append(e.toString())
                        operacion = binding.textosuper.text.toString()
                    }

                    binding.textosuper.append("=")
                    binding.textodown.text = ""
                    resultado = resOperacion(listaElementos)

                    binding.textodown.text = resultado

                    listaElementos.clear()
                    listaElementos.add(resultado)

                    pulsadoOperador = false
                }
            }
            "sin" ,"cos" ,"tan" ,"%" ,"raz"-> {
                listaElementos?.add(binding.textodown.text.toString())
                listaElementos?.add(ultimoValorPulsado)
                resultado = listaElementos?.let { resOperacion(it) }.toString()
                binding.textodown.text = resultado

            }
        }

        if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            if (textoLengthDown != null) {
                if(textoLengthDown > 6) {
                    myTextViewDown.setTextSize(textoPequeño)

                } else {
                    myTextViewDown.setTextSize(textoNormal)

                }
            }
        }
    }
}


