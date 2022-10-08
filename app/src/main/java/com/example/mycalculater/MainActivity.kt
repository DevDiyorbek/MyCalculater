package com.example.mycalculater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var mainText : TextView? = null
    private var lastDigit: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainText = findViewById(R.id.tv_text)
    }

    fun onDigit(view: View) {
        mainText?.append((view as Button).text)
        lastDigit = true
        lastDot = false
    }

    fun onClear(view: View) {
        mainText?.text = ""
    }

    fun onDecimalPoint(view: android.view.View) {
        if (lastDigit && !lastDot){
            mainText?.append(".")
            lastDigit = false
            lastDot = true
        }}

    fun onOperation(view: View){
        mainText?.text?.let {
            if (lastDigit && !isOperationAdded(it.toString())){
                mainText?.append((view as Button).text)
                lastDigit = false
                lastDot = false
            }
        }
    }
    private fun isOperationAdded(value:String) :Boolean{
        return if (value.startsWith("-")){
            false
        } else {value.contains("/") ||
                value.contains("*") ||
                value.contains("+") ||
                value.contains("-")
        }
    }

    fun onEqual(view: android.view.View) {
        var prefix = ""
        if (lastDigit){
            var valueText = mainText?.text.toString()

            try {
                if (valueText.startsWith("-")){
                    prefix = "-"
                    valueText = valueText.substring(1)
                }
                if (valueText.contains("-")){
                    val values = valueText.split("-")
                    var first = values[0]
                    val second = values[1]
                    if (prefix != ""){
                        first = prefix + first
                    }
                    mainText?.text = removeZero((first.toDouble() - second.toDouble()).toString())
                } else if (valueText.contains("+")){
                    val values = valueText.split("+")
                    var first = values[0]
                    val second = values[1]
                    if (prefix != ""){
                        first = prefix + first
                    }
                    mainText?.text = removeZero((first.toDouble() + second.toDouble()).toString())
                } else if (valueText.contains("*")){
                    val values = valueText.split("*")
                    var first = values[0]
                    val second = values[1]
                    if (prefix != ""){
                        first = prefix + first
                    }
                    mainText?.text = removeZero((first.toDouble() * second.toDouble()).toString())
                } else if (valueText.contains("/")){
                    val values = valueText.split("/")
                    var first = values[0]
                    val second = values[1]
                    if (prefix != ""){
                        first = prefix + first
                    }
                    mainText?.text = removeZero((first.toDouble() / second.toDouble()).toString())
                }

            } catch (e: ArithmeticException){
                e.printStackTrace()
            }

        }
    }
    fun removeZero(value: String) : String{
        var result = value
        if (result.contains(".0"))
            result = result.substring(0,result.length-2)
        return result
    }
}