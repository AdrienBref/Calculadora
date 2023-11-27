package utils

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan


fun evaluaSimbolo(simbolo:String):Boolean {

    return when (simbolo) {
        "+","-","*","/" -> true
        else -> false
    }
}

fun resOperacion(listaOperacion : ArrayList<String>) : String {

    val boundNum : Int = listaOperacion.size
    var resultadoString : String = ""
    var operando1: Double = listaOperacion[0].toDouble()
    var operando2: Double = 0.0
    var operador = listaOperacion[1]
    var resultado: Double = 0.0

    when(boundNum) {
        2 -> {
            when(operador) {
                "%" -> resultado = operando1 / 100
                "sin" -> resultado = sin(operando1)
                "cos" -> resultado = cos(operando1)
                "tan" -> resultado = tan(operando1)
                "raz" -> resultado = sqrt(operando1)
            }
        }
        3 -> {
            operando2 = listaOperacion[2].toDouble()

            when(operador) {
                "+" -> resultado = operando1 + operando2
                "-" -> resultado = operando1 - operando2
                "*" -> resultado = operando1 * operando2
                "/" -> {
                    if (operando2 != 0.0) {
                        resultado = operando1 / operando2
                    } else {
                        resultadoString = "∞"
                    }
                }
            }
        }
    }

    resultadoString = conversorDec(resultado)

    return resultadoString

}

fun conversorDec(numeroResultado : Double) : String {

    val formattedResult = if (numeroResultado % 1.0 == 0.0) {
        numeroResultado.toInt().toString()
    } else {
        numeroResultado.toString()
    }

    return formattedResult

}

