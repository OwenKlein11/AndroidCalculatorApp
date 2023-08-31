package com.example.calculatorappp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

var currentTotal= 0
var currentInput = 0
var currentOperator = ""


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
        val pointButton = findViewById<Button>(R.id.bPoint)

        val calcText = findViewById<TextView>(R.id.tvResult)

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
        )

        for (button in numButtons) {
            button.setOnClickListener {
                onNumButtonClick(button)
                updateScreen(calcText)
            }
        }



        // handles when clear button is clicked
        clearButton.setOnClickListener {
            currentTotal = 0
            currentInput = 0
            currentOperator = ""
            updateScreen(calcText)
        }

        // handles when negPos button is clicked
        negPosButton.setOnClickListener {
            if (currentInput != 0) {
                currentInput *= -1
            }
            updateScreen(calcText)
        }

        // handles when modButton button is clicked
        modButton.setOnClickListener{
            currentInput.toDouble()
            currentInput /= 100
            updateScreen(calcText)
        }

        // handles when add button is clicked
        addButton.setOnClickListener{
            currentTotal = currentInput
            currentInput = 0

            currentOperator += "+"
        }

        // handles when sub button is clicked
       subButton.setOnClickListener{
           currentTotal = currentInput
           currentInput = 0

           currentOperator += "-"
        }

        // handles when multiplication button is clicked
        multiplyButton.setOnClickListener{
            currentTotal = currentInput
            currentInput = 0

            currentOperator += "x"
        }

        // handles when div button is clicked
        divButton.setOnClickListener{
            currentTotal = currentInput
            currentInput = 0

            currentOperator += "/"
        }

        // handles when equals button is clicked
        equalsButton.setOnClickListener{
            equals()
            currentOperator = ""
            updateScreen(calcText)
        }


    }

    fun onNumButtonClick(button: Button) {
        val buttonText = button.text.toString()

        appendToInput(buttonText)
    }

    // updates the screen to the currentInput
    fun updateScreen(tv : TextView){
        tv.text = currentInput.toString()
    }

    // appends number onto input, runs when number/. is clicked after another number/.
    fun appendToInput(buttonText : String){
        if (currentInput == 0){
            currentInput = buttonText.toInt()
        } else {
            val currentInputAsString = StringBuilder()
            currentInputAsString.append(currentInput.toString())
            currentInputAsString.append(buttonText)
            val currentInputString = currentInputAsString.toString()
            currentInput = currentInputString.toInt()
        }
    }

    fun equals(){
        if (currentOperator == "+"){
            currentInput += currentTotal
        } else if (currentOperator == "-"){
            currentInput = currentTotal - currentInput
        } else if (currentOperator == "/"){
            currentInput = currentTotal / currentInput
        } else if (currentOperator == "x"){
            currentInput *= currentTotal
        } else {
            currentInput += 0
        }
    }

}