package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.mariuszgromada.math.mxparser.Expression
import kotlin.math.sqrt

class EquationActivity : AppCompatActivity() {

    private lateinit var equationTypeSpinner: Spinner
    private lateinit var inputEquation: EditText
    private lateinit var btnSolve: Button
    private lateinit var txtResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equation)

        equationTypeSpinner = findViewById(R.id.equationTypeSpinner)
        inputEquation = findViewById(R.id.inputEquation)
        btnSolve = findViewById(R.id.btnSolve)
        txtResult = findViewById(R.id.txtResult)

        // Equation types
        val equationTypes = arrayOf("Select Equation Type", "Linear (ax + b = c)", "Quadratic (ax² + bx + c = 0)", "Cubic (ax³ + bx² + cx + d = 0)")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, equationTypes)
        equationTypeSpinner.adapter = adapter

        equationTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    1 -> inputEquation.hint = "Example: 2x + 3 = 7"
                    2 -> inputEquation.hint = "Example: x² + 2x - 3 = 0"
                    3 -> inputEquation.hint = "Example: x³ - 4x² + 5x - 2 = 0"
                    else -> inputEquation.hint = "Select equation type"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnSolve.setOnClickListener {
            val equation = inputEquation.text.toString().replace(" ", "")
            val result = when (equationTypeSpinner.selectedItemPosition) {
                1 -> solveLinear(equation)
                2 -> solveQuadratic(equation)
                3 -> solveCubic(equation)
                else -> "Please select an equation type"
            }
            txtResult.text = result
        }
    }

    private fun solveLinear(equation: String): String {
        val parts = equation.split("=")
        if (parts.size != 2) return "Invalid equation format"

        val leftSide = parts[0]
        val rightSide = parts[1]

        val regex = """(-?\d*\.?\d*)x([+-]?\d*\.?\d*)?""".toRegex()
        val match = regex.find(leftSide) ?: return "Invalid equation"

        val (aStr, bStr) = match.destructured
        val a = if (aStr.isEmpty() || aStr == "-") aStr.replace("-", "-1").toDouble() else aStr.toDouble()
        val b = if (bStr.isEmpty()) 0.0 else bStr.toDouble()

        val rightValue = Expression(rightSide).calculate()

        val xValue = (rightValue - b) / a
        return "x = $xValue"
    }

    private fun solveQuadratic(equation: String): String {
        val formattedEq = equation.replace("^2", "²").replace(" ", "")

        val regex = """(-?\d*\.?\d*)?x²([+-]?\d*\.?\d*)?x([+-]?\d*\.?\d*)?=(-?\d*\.?\d*)?""".toRegex()
        val match = regex.find(formattedEq) ?: return "Invalid quadratic equation"

        val (aStr, bStr, cStr, rightSideStr) = match.destructured
        val a = if (aStr.isEmpty() || aStr == "-") aStr.replace("-", "-1").toDouble() else aStr.toDouble()
        val b = if (bStr.isEmpty()) 0.0 else bStr.toDouble()
        val c = if (cStr.isEmpty()) 0.0 else cStr.toDouble()
        val rightSide = if (rightSideStr.isEmpty()) 0.0 else rightSideStr.toDouble()

        val cCorrected = c - rightSide
        val discriminant = b * b - 4 * a * cCorrected

        return when {
            discriminant > 0 -> {
                val x1 = (-b + sqrt(discriminant)) / (2 * a)
                val x2 = (-b - sqrt(discriminant)) / (2 * a)
                "x1 = $x1, x2 = $x2"
            }
            discriminant == 0.0 -> {
                val x = -b / (2 * a)
                "x = $x"
            }
            else -> "No real solutions"
        }
    }

    private fun solveCubic(equation: String): String {
        return "Cubic equation solving coming soon!"
    }
}