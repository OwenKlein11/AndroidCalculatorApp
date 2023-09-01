package com.example.calculatorappp1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.text.DecimalFormat

// Int: current total, stores total of equation, when operator is clicked, then used to operate
var currentTotal = 0.0
// Int: this is the main storage variable of the program, stores whats displayed on screen, and used in operations
var currentInput = 0.0
// String: stores current operator when operator button is clicked, then used to operate on currentInput and total
var currentOperator = ""
// Boolean: checks if equation is possible, true at all times unless divided by 0
var ifPossible = true
// Boolean: sets if the point has been clicked and is used in conditionals in multiple functions
var hasClickedPoint = false
// String: what is displayed when point is clicked, used until another number is clicked then appended to the input
var beforePoint = "0"


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // below are all the variables taken from the buttons on app
        val clearButton = findViewById<Button>(R.id.bClear)
        val negPosButton = findViewById<Button>(R.id.bNegPos)
        val modButton = findViewById<Button>(R.id.bMod)
        val divButton = findViewById<Button>(R.id.bDivide)

        val multiplyButton = findViewById<Button>(R.id.bMultiply)
        val subButton = findViewById<Button>(R.id.bSubtract)
        val addButton = findViewById<Button>(R.id.bAdd)
        val equalsButton = findViewById<Button>(R.id.bEquals)

        val calcText = findViewById<TextView>(R.id.tvResult)

        // this efficiently creates variables for all number and point buttons
        val numButtons = arrayOf(
            findViewById<Button>(R.id.b0),
            findViewById<Button>(R.id.b1),
            findViewById<Button>(R.id.b2),
            findViewById<Button>(R.id.b3),
            findViewById<Button>(R.id.b4),
            findViewById<Button>(R.id.b5),
            findViewById<Button>(R.id.b6),
            findViewById<Button>(R.id.b7),
            findViewById<Button>(R.id.b8),
            findViewById<Button>(R.id.b9),
            findViewById<Button>(R.id.bPoint),
        )

        // cycling through the array created above, this for loop creates listeners and runs updateScreen()
        for (button in numButtons) {
            button.setOnClickListener {
                onNumButtonClick(button)
                updateScreen(calcText)
            }
        }


        // handles when clear button is clicked
        clearButton.setOnClickListener {
            currentTotal = 0.0
            currentInput = 0.0
            currentOperator = ""
            ifPossible = true
            updateScreen(calcText)
        }

        // handles when negPos button is clicked
        negPosButton.setOnClickListener {
            if (currentInput != 0.0) {
                currentInput *= -1
            }
            updateScreen(calcText)
        }

        // handles when modButton button is clicked
        modButton.setOnClickListener {
            val inputDouble = currentInput
            currentInput = inputDouble / 100
            updateScreen(calcText)
        }

        // handles when add button is clicked
        addButton.setOnClickListener {
            currentTotal = currentInput
            currentInput = 0.0

            currentOperator += "+"
        }

        // handles when sub button is clicked
        subButton.setOnClickListener {
            currentTotal = currentInput
            currentInput = 0.0

            currentOperator += "-"
        }

        // handles when multiplication button is clicked
        multiplyButton.setOnClickListener {
            currentTotal = currentInput
            currentInput = 0.0

            currentOperator += "x"
        }

        // handles when div button is clicked
        divButton.setOnClickListener {
            currentTotal = currentInput
            currentInput = 0.0

            currentOperator += "/"
        }

        // handles when equals button is clicked
        equalsButton.setOnClickListener {
            equals()
            currentOperator = ""
            updateScreen(calcText)
        }


    }

    // takes in button and turns it into string, appending it by running appendToInput function
    fun onNumButtonClick(button: Button) {

        ifPossible = true
        val buttonText = button.text.toString()

        if (buttonText == "." && !hasClickedPoint && !formatNumber(currentInput).contains(".")) {
            // when the point button is clicked and theres not already a point in the input, this runs
            hasClickedPoint = true
            appendPoint()
        } else if (buttonText != "." && beforePoint.contains(".")) {
            // when there is no decimal, this runs, continues like normal
            appendToPoint(buttonText)
        } else if (buttonText != "."){
            // when there is already a point in the input, but another number needs to be appended
            appendToInput(buttonText)
        }

    }


    // helps format number into decimal and uses imported class DecimalFormat to do so
    fun formatNumber(value: Double): String {
        val decimalFormat = DecimalFormat("#.######") // Adjust the pattern as needed
        return decimalFormat.format(value)
    }

    // updates the screen to the currentInput
    @SuppressLint("SetTextI18n")
    fun updateScreen(tv: TextView) {
        if (ifPossible){
            // checks if divided by zero or not
            if (hasClickedPoint){
                // checks if point button has been clicked, handles accordingly
                tv.text = beforePoint
                hasClickedPoint = false
            } else {
                val formattedValue = formatNumber(currentInput)
                tv.text = formattedValue
            }
        } else {
            tv.text = "Error"
        }
    }

    // appends number onto input, runs when number/. is clicked after another number/.
    fun appendToInput(buttonText: String) {
        if (buttonText == ".") {
            // Append the decimal point only if it hasn't been appended already
            if (!currentInput.toString().contains(".")) {
                currentInput = (currentInput.toString() + buttonText).toDouble()
            }
        } else if (currentInput == 0.0) {
            currentInput = buttonText.toDouble()
        } else {
            currentInput = currentInput * 10 + buttonText.toDouble()
        }
    }

    // this function sets beforePoint to whatever the current input is and adds point
    fun appendPoint(){
        beforePoint = currentInput.toInt().toString() + "."
    }

    // this function adds the input to the before point and then resets before point
    fun appendToPoint(buttonText: String) {
        currentInput = (beforePoint + buttonText).toDouble()
        beforePoint = "0"
    }

    // run when equals sign is clicked, checks operator and completes operation, updating currentInput
    fun equals() {
        if (currentOperator == "+") {
            currentInput += currentTotal
        } else if (currentOperator == "-") {
            currentInput = currentTotal - currentInput
        } else if (currentOperator == "/") {
            if (currentInput != 0.0) {
                currentInput = currentTotal / currentInput
            } else {
                // handle division by zero
                ifPossible = false
            }
        } else if (currentOperator == "x") {
            currentInput *= currentTotal
        } else {
            currentInput += 0.0
        }
    }


}