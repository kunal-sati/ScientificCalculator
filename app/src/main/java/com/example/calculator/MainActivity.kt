package com.example.calculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // UI Components
    lateinit var bMatrix: Button // Add this line
    lateinit var tvsec: TextView
    lateinit var tvMain: TextView
    lateinit var bac: Button
    lateinit var bc: Button
    lateinit var bbrac1: Button
    lateinit var bbrac2: Button
    lateinit var bsin: Button
    lateinit var bcos: Button
    lateinit var btan: Button
    lateinit var blog: Button
    lateinit var bln: Button
    lateinit var bfact: Button
    lateinit var bsquare: Button
    lateinit var bsqrt: Button
    lateinit var binv: Button
    lateinit var b0: Button
    lateinit var b9: Button
    lateinit var b8: Button
    lateinit var b7: Button
    lateinit var b6: Button
    lateinit var b5: Button
    lateinit var b4: Button
    lateinit var b3: Button
    lateinit var b2: Button
    lateinit var b1: Button
    lateinit var bpi: Button
    lateinit var bmul: Button
    lateinit var bminus: Button
    lateinit var bplus: Button
    lateinit var bequal: Button
    lateinit var bdot: Button
    lateinit var bdiv: Button
    lateinit var bShift: Button

    // Shift State
    private var isShiftActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI Components
        bMatrix = findViewById(R.id.bMatrix) // Add this line
        tvsec = findViewById(R.id.idTVSecondary)
        tvMain = findViewById(R.id.idTVprimary)
        bac = findViewById(R.id.bac)
        bc = findViewById(R.id.bc)
        bbrac1 = findViewById(R.id.bbrac1)
        bbrac2 = findViewById(R.id.bbrac2)
        bsin = findViewById(R.id.bsin)
        bcos = findViewById(R.id.bcos)
        btan = findViewById(R.id.btan)
        blog = findViewById(R.id.blog)
        bln = findViewById(R.id.bln)
        bfact = findViewById(R.id.bfact)
        bsquare = findViewById(R.id.bsquare)
        bsqrt = findViewById(R.id.bsqrt)
        binv = findViewById(R.id.binv)
        b0 = findViewById(R.id.b0)
        b9 = findViewById(R.id.b9)
        b8 = findViewById(R.id.b8)
        b7 = findViewById(R.id.b7)
        b6 = findViewById(R.id.b6)
        b5 = findViewById(R.id.b5)
        b4 = findViewById(R.id.b4)
        b3 = findViewById(R.id.b3)
        b2 = findViewById(R.id.b2)
        b1 = findViewById(R.id.b1)
        bpi = findViewById(R.id.bpi)
        bmul = findViewById(R.id.bmul)
        bminus = findViewById(R.id.bminus)
        bplus = findViewById(R.id.bplus)
        bequal = findViewById(R.id.bequal)
        bdot = findViewById(R.id.bdot)
        bdiv = findViewById(R.id.bdiv)
        bShift = findViewById(R.id.bshift)

        // Shift Button Click Listener
        bShift.setOnClickListener {
            isShiftActive = !isShiftActive
            updateButtonLabels()
        }

        bMatrix.setOnClickListener {
            if (bMatrix.text == "Matrix") {
                val intent = Intent(this, MatrixActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, EquationActivity::class.java)
                startActivity(intent)
            }
        }
        // Number and Basic Operation Buttons
        b1.setOnClickListener { tvMain.text = (tvMain.text.toString() + "1") }
        b2.setOnClickListener { tvMain.text = (tvMain.text.toString() + "2") }
        b3.setOnClickListener { tvMain.text = (tvMain.text.toString() + "3") }
        b4.setOnClickListener { tvMain.text = (tvMain.text.toString() + "4") }
        b5.setOnClickListener { tvMain.text = (tvMain.text.toString() + "5") }
        b6.setOnClickListener { tvMain.text = (tvMain.text.toString() + "6") }
        b7.setOnClickListener { tvMain.text = (tvMain.text.toString() + "7") }
        b8.setOnClickListener { tvMain.text = (tvMain.text.toString() + "8") }
        b9.setOnClickListener { tvMain.text = (tvMain.text.toString() + "9") }
        b0.setOnClickListener { tvMain.text = (tvMain.text.toString() + "0") }
        bdot.setOnClickListener { tvMain.text = (tvMain.text.toString() + ".") }
        bplus.setOnClickListener { tvMain.text = (tvMain.text.toString() + "+") }
        bdiv.setOnClickListener { tvMain.text = (tvMain.text.toString() + "/") }
        bbrac1.setOnClickListener { tvMain.text = (tvMain.text.toString() + "(") }
        bbrac2.setOnClickListener { tvMain.text = (tvMain.text.toString() + ")") }
        bpi.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "3.142")
            tvsec.text = (bpi.text.toString())
        }
        bminus.setOnClickListener {
            val str: String = tvMain.text.toString()
            if (!str.get(index = str.length - 1).equals("-")) {
                tvMain.text = (tvMain.text.toString() + "-")
            }
        }
        bmul.setOnClickListener {
            val str: String = tvMain.text.toString()
            if (!str.get(index = str.length - 1).equals("*")) {
                tvMain.text = (tvMain.text.toString() + "*")
            }
        }

        // Trigonometric Functions
        bsin.setOnClickListener {
            if (isShiftActive) {
                tvMain.text = tvMain.text.toString() + "asin("
            } else {
                tvMain.text = tvMain.text.toString() + "sin("
            }
        }
        bcos.setOnClickListener {
            if (isShiftActive) {
                tvMain.text = tvMain.text.toString() + "acos("
            } else {
                tvMain.text = tvMain.text.toString() + "cos("
            }
        }
        btan.setOnClickListener {
            if (isShiftActive) {
                tvMain.text = tvMain.text.toString() + "atan("
            } else {
                tvMain.text = tvMain.text.toString() + "tan("
            }
        }

        // Logarithmic Functions
        blog.setOnClickListener {
            if (isShiftActive) {
                tvMain.text = tvMain.text.toString() + "log₂("
            } else {
                tvMain.text = tvMain.text.toString() + "log("
            }
        }
        bln.setOnClickListener {
            if (isShiftActive) {
                tvMain.text = tvMain.text.toString() + "e^("
            } else {
                tvMain.text = tvMain.text.toString() + "ln("
            }
        }

        // Advanced Functions
        bsqrt.setOnClickListener {
            if (tvMain.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter a valid number..", Toast.LENGTH_SHORT).show()
            } else {
                val str: String = tvMain.text.toString()
                val r = Math.sqrt(str.toDouble())
                tvMain.setText(r.toString())
            }
        }
        bsquare.setOnClickListener {
            if (tvMain.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter a valid number..", Toast.LENGTH_SHORT).show()
            } else {
                val d: Double = tvMain.getText().toString().toDouble()
                val square = d * d
                tvMain.setText(square.toString())
                tvsec.text = "$d²"
            }
        }
        bfact.setOnClickListener {
            if (tvMain.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter a valid number..", Toast.LENGTH_SHORT).show()
            } else {
                val value: Int = tvMain.text.toString().toInt()
                val fact: Int = factorial(value)
                tvMain.setText(fact.toString())
                tvsec.text = "$value`!"
            }
        }
        binv.setOnClickListener {
            tvMain.text = tvMain.text.toString() + "^(-1)"
        }

        // Equal Button
        bequal.setOnClickListener {
            val str: String = tvMain.text.toString()
            val result: Double = evaluate(str)
            tvMain.setText(result.toString())
            tvsec.text = str
        }

        // Clear Buttons
        bac.setOnClickListener {
            tvMain.setText("")
            tvsec.setText("")
        }
        bc.setOnClickListener {
            var str: String = tvMain.text.toString()
            if (!str.equals("")) {
                str = str.substring(0, str.length - 1)
                tvMain.text = str
            }
        }
    }

    // Function to Update Button Labels based on Shift Mode
    private fun updateButtonLabels() {
        if (isShiftActive) {
            bsin.text = "asin"
            bcos.text = "acos"
            btan.text = "atan"
            blog.text = "log₂"
            bln.text = "e^x"
            bMatrix.text = "Equation"
        } else {
            bsin.text = "sin"
            bcos.text = "cos"
            btan.text = "tan"
            blog.text = "log"
            bln.text = "ln"
            bMatrix.text= "Matrix"
        }
    }

    // Factorial Calculation
    private fun factorial(n: Int): Int {
        return if (n == 1 || n == 0) 1 else n * factorial(n - 1)
    }

    // Expression Evaluation
    private fun evaluate(str: String): Double {
        return object : Any() {
            var pos = -1
            var ch = 0

            fun nextChar() {
                ch = if (++pos < str.length) str[pos].toInt() else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.toInt()) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (eat('+'.toInt())) x += parseTerm()
                    else if (eat('-'.toInt())) x -= parseTerm()
                    else return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'.toInt())) x *= parseFactor()
                    else if (eat('/'.toInt())) x /= parseFactor()
                    else return x
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.toInt())) return parseFactor()
                if (eat('-'.toInt())) return -parseFactor()
                var x: Double
                val startPos = pos
                if (eat('('.toInt())) {
                    x = parseExpression()
                    eat(')'.toInt())
                } else if (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) {
                    while (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) nextChar()
                    x = str.substring(startPos, pos).toDouble()
                } else if (ch >= 'a'.toInt() && ch <= 'z'.toInt()) {
                    while (ch >= 'a'.toInt() && ch <= 'z'.toInt()) nextChar()
                    val func = str.substring(startPos, pos)
                    x = parseFactor()
                    x = when (func) {
                        "sqrt" -> Math.sqrt(x)
                        "sin" -> Math.sin(Math.toRadians(x))
                        "cos" -> Math.cos(Math.toRadians(x))
                        "tan" -> Math.tan(Math.toRadians(x))
                        "asin" -> Math.toDegrees(Math.asin(x))
                        "acos" -> Math.toDegrees(Math.acos(x))
                        "atan" -> Math.toDegrees(Math.atan(x))
                        "log" -> Math.log10(x)
                        "log₂" -> Math.log(x) / Math.log(2.0)
                        "ln" -> Math.log(x)
                        "e^" -> Math.exp(x)
                        else -> throw RuntimeException("Unknown function: $func")
                    }
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }
                if (eat('^'.toInt())) x = Math.pow(x, parseFactor())
                return x
            }
        }.parse()
    }
}