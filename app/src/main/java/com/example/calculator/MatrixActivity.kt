package com.example.calculator

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setMargins
import kotlin.math.pow

class MatrixActivity : AppCompatActivity() {

    private lateinit var etMatrixSize: EditText
    private lateinit var etScalar: EditText
    private lateinit var gridMatrixA: GridLayout
    private lateinit var gridMatrixB: GridLayout
    private lateinit var tvResult: TextView

    private var matrixSize = 2
    private val matrixAFields = mutableListOf<EditText>()
    private val matrixBFields = mutableListOf<EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matrix)
        initializeViews()
        setupButtonActions()
    }

    private fun initializeViews() {
        etMatrixSize = findViewById(R.id.etMatrixSize)
        etScalar = findViewById(R.id.etScalar)
        gridMatrixA = findViewById(R.id.gridMatrixA)
        gridMatrixB = findViewById(R.id.gridMatrixB)
        tvResult = findViewById(R.id.tvResult)
    }

    private fun setupButtonActions() {
        findViewById<Button>(R.id.btnGenerateGrids).setOnClickListener {
            if (validateMatrixSize()) createMatrixInputGrids()
        }

        val operationsMap = mapOf(
            R.id.btnAdd to Operation.ADD,
            R.id.btnSubtract to Operation.SUBTRACT,
            R.id.btnMultiply to Operation.MULTIPLY,
            R.id.btnDeterminant to Operation.DETERMINANT,
            R.id.btnInverse to Operation.INVERSE,
            R.id.btnTranspose to Operation.TRANSPOSE,
            R.id.btnScalarMultiply to Operation.SCALAR_MULTIPLY
        )

        operationsMap.forEach { (btnId, operation) ->
            findViewById<Button>(btnId).setOnClickListener {
                if (validateMatricesReady()) {
                    try {
                        performOperation(operation)
                    } catch (e: Exception) {
                        showError("Operation failed: ${e.message}")
                    }
                }
            }
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener { clearInputs() }
    }

    private fun validateMatrixSize(): Boolean {
        etMatrixSize.text.toString().toIntOrNull()?.let { size ->
            return if (size in 1..6) {
                matrixSize = size
                true
            } else {
                showError("Size must be 1-6")
                false
            }
        } ?: run {
            showError("Invalid matrix size")
            return false
        }
    }

    private fun validateMatricesReady(): Boolean {
        return when {
            matrixAFields.isEmpty() || matrixBFields.isEmpty() -> {
                showError("Generate matrices first!")
                false
            }
            matrixAFields.any { it.text.isEmpty() } || matrixBFields.any { it.text.isEmpty() } -> {
                showError("Fill all matrix values!")
                false
            }
            else -> true
        }
    }

    private fun createMatrixInputGrids() {
        clearMatrixGrids()
        createMatrixGrid(gridMatrixA, matrixAFields, "A")
        createMatrixGrid(gridMatrixB, matrixBFields, "B")
        setupFocusNavigation(matrixAFields)
        setupFocusNavigation(matrixBFields)
    }

    private fun createMatrixGrid(grid: GridLayout, fields: MutableList<EditText>, prefix: String) {
        grid.columnCount = matrixSize
        grid.rowCount = matrixSize
        fields.clear()

        for (i in 0 until matrixSize) {
            for (j in 0 until matrixSize) {
                val editText = createMatrixEditText("$prefix[${i+1}][${j+1}]")
                grid.addView(editText)
                fields.add(editText)
            }
        }
    }

    private fun createMatrixEditText(hint: String): EditText {
        return EditText(this).apply {
            this.hint = hint
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or
                    android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
            setBackgroundResource(R.drawable.edittext_border)
            layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(8)
            }
        }
    }

    private fun setupFocusNavigation(fields: List<EditText>) {
        fields.forEachIndexed { index, editText ->
            editText.imeOptions = if (index == fields.lastIndex) EditorInfo.IME_ACTION_DONE
            else EditorInfo.IME_ACTION_NEXT

            editText.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_NEXT -> fields.getOrNull(index + 1)?.requestFocus()
                    EditorInfo.IME_ACTION_DONE -> editText.clearFocus()
                }
                true
            }
        }
    }

    private fun performOperation(operation: Operation) {
        val a = getMatrix(matrixAFields)
        val b = getMatrix(matrixBFields)

        when (operation) {
            Operation.ADD -> displayResult(addMatrices(a, b))
            Operation.SUBTRACT -> displayResult(subtractMatrices(a, b))
            Operation.MULTIPLY -> displayResult(multiplyMatrices(a, b))
            Operation.DETERMINANT -> showDeterminant(a)
            Operation.INVERSE -> displayResult(inverseMatrix(a) ?: throw Exception("Matrix not invertible"))
            Operation.TRANSPOSE -> displayResult(transposeMatrix(a))
            Operation.SCALAR_MULTIPLY -> displayResult(scalarMultiply(a))
        }
    }

    // Matrix operations
    private fun addMatrices(a: Array<DoubleArray>, b: Array<DoubleArray>): Array<DoubleArray> {
        validateSameSize(a, b)
        return Array(a.size) { i ->
            DoubleArray(a[i].size) { j ->
                a[i][j] + b[i][j]
            }
        }
    }

    private fun subtractMatrices(a: Array<DoubleArray>, b: Array<DoubleArray>): Array<DoubleArray> {
        validateSameSize(a, b)
        return Array(a.size) { i ->
            DoubleArray(a[i].size) { j ->
                a[i][j] - b[i][j]
            }
        }
    }

    private fun multiplyMatrices(a: Array<DoubleArray>, b: Array<DoubleArray>): Array<DoubleArray> {
        if (a[0].size != b.size) throw Exception("Columns of A must match rows of B")
        return Array(a.size) { i ->
            DoubleArray(b[0].size) { j ->
                (0 until b.size).sumOf { k -> a[i][k] * b[k][j] }
            }
        }
    }

    private fun showDeterminant(matrix: Array<DoubleArray>) {
        validateSquareMatrix(matrix)
        tvResult.text = "Determinant: ${calculateDeterminant(matrix).format(2)}"
    }

    private fun inverseMatrix(matrix: Array<DoubleArray>): Array<DoubleArray>? {
        validateSquareMatrix(matrix)
        val det = calculateDeterminant(matrix)
        if (det == 0.0) return null

        val adjugate = Array(matrix.size) { i ->
            DoubleArray(matrix.size) { j ->
                (-1.0).pow(i + j) * calculateDeterminant(getMinor(matrix, i, j))
            }
        }
        return scalarMultiplyMatrix(transposeMatrix(adjugate), 1.0 / det)
    }

    private fun transposeMatrix(matrix: Array<DoubleArray>): Array<DoubleArray> {
        return Array(matrix[0].size) { i ->
            DoubleArray(matrix.size) { j ->
                matrix[j][i]
            }
        }
    }

    private fun scalarMultiply(matrix: Array<DoubleArray>): Array<DoubleArray> {
        val scalar = etScalar.text.toString().toDoubleOrNull()
            ?: throw Exception("Invalid scalar value")
        return scalarMultiplyMatrix(matrix, scalar)
    }

    // Helper functions
    private fun getMatrix(fields: List<EditText>): Array<DoubleArray> {
        return Array(matrixSize) { i ->
            DoubleArray(matrixSize) { j ->
                fields[i * matrixSize + j].text.toString().toDouble()
            }
        }
    }

    private fun calculateDeterminant(matrix: Array<DoubleArray>): Double {
        if (matrix.size == 1) return matrix[0][0]
        if (matrix.size == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]

        return matrix[0].indices.sumOf { j ->
            matrix[0][j] * (-1.0).pow(j) *
                    calculateDeterminant(getMinor(matrix, 0, j))
        }
    }

    private fun getMinor(matrix: Array<DoubleArray>, row: Int, col: Int): Array<DoubleArray> {
        return Array(matrix.size - 1) { i ->
            DoubleArray(matrix.size - 1) { j ->
                matrix[if (i < row) i else i + 1][if (j < col) j else j + 1]
            }
        }
    }

    private fun scalarMultiplyMatrix(matrix: Array<DoubleArray>, scalar: Double): Array<DoubleArray> {
        return Array(matrix.size) { i ->
            DoubleArray(matrix[i].size) { j ->
                matrix[i][j] * scalar
            }
        }
    }

    // Validation
    private fun validateSameSize(a: Array<DoubleArray>, b: Array<DoubleArray>) {
        if (a.size != b.size || a[0].size != b[0].size) {
            throw Exception("Matrices must be the same size")
        }
    }

    private fun validateSquareMatrix(matrix: Array<DoubleArray>) {
        if (matrix.size != matrix[0].size) throw Exception("Matrix must be square")
    }

    // UI functions
    private fun displayResult(result: Array<DoubleArray>) {
        tvResult.text = result.joinToString("\n") { row ->
            row.joinToString("\t") { "%.2f".format(it) }
        }
    }

    private fun clearInputs() {
        matrixAFields.forEach { it.text.clear() }
        matrixBFields.forEach { it.text.clear() }
        etMatrixSize.text.clear()
        etScalar.text.clear()
        tvResult.text = ""
        gridMatrixA.requestFocus()
    }

    private fun clearMatrixGrids() {
        matrixAFields.clear()
        matrixBFields.clear()
        gridMatrixA.removeAllViews()
        gridMatrixB.removeAllViews()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun Double.format(decimals: Int): String = "%.${decimals}f".format(this)

    enum class Operation { ADD, SUBTRACT, MULTIPLY, DETERMINANT, INVERSE, TRANSPOSE, SCALAR_MULTIPLY }
}