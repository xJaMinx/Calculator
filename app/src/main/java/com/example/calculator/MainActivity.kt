package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    var isFirstInput:Boolean = true
    var isOperatorClick:Boolean = false
    var resultNumber:Double = 0.0
    var inputNumber:Double = 0.0
    var operator:String = "="
    var lastOperator:String = "+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    fun numButtonClick(view:View){
        if (isFirstInput) {
            mBinding?.tvResult?.text = view.tag.toString()
            isFirstInput = false
            if(operator == "="){
                mBinding?.tvMath?.text = null
                isOperatorClick = false
            }
        }else{
            if (mBinding?.tvResult?.text.toString() == "0"){
                isFirstInput = true
            }else {
                mBinding?.tvResult?.append(view.tag.toString())
            }
        }
    }
    fun allClearButton(view: View){
        mBinding?.tvResult?.text = "0"
        mBinding?.tvMath?.text = null
        operator = "+"
        resultNumber = 0.0
        isFirstInput = true
        isOperatorClick = false
    }

    fun pointButton(view: View){
        if (isFirstInput) {
            mBinding?.tvResult?.text = "0" + view.tag.toString()
            isFirstInput = false
        }else{
            if (mBinding?.tvResult?.text.toString().contains("."))
            else {
                mBinding?.tvResult?.append(view.tag.toString())
            }
        }
    }

    fun operatorClick(view:View){
        isOperatorClick = true
        lastOperator = view.tag.toString()

        if (isFirstInput){
            if(operator == "="){
                operator = view.tag.toString()
                resultNumber = mBinding?.tvResult?.text.toString().toDouble()
                mBinding?.tvMath?.text = resultNumber.toString() + operator
            }else{
                operator = view.tag.toString()
                val getMathText: String = mBinding?.tvMath?.text.toString()
                val subString: String = getMathText.substring(0, getMathText.length - 1)

                mBinding?.tvMath?.text = subString
                mBinding?.tvMath?.append(operator)
            }
        }else{
        inputNumber = mBinding?.tvResult?.text.toString().toDouble()

        resultNumber = calculator(resultNumber,inputNumber,operator)

        mBinding?.tvResult?.text = resultNumber.toString()
        isFirstInput = true
        operator = view.tag.toString()
        mBinding?.tvMath?.append("$inputNumber $operator")
        }
    }

    fun equalsButtonClick(view:View){
        if (isFirstInput) {
            if (isOperatorClick) {
                mBinding?.tvMath?.text = resultNumber.toString() + lastOperator + inputNumber
                resultNumber = calculator(resultNumber, inputNumber, lastOperator)
                mBinding?.tvResult?.text = resultNumber.toString()
            }
        }else{
            inputNumber = mBinding?.tvResult?.text.toString().toDouble()

            resultNumber = calculator(resultNumber,inputNumber,operator)

            mBinding?.tvResult?.text = resultNumber.toString()
            isFirstInput = true
            operator = view.tag.toString()
            mBinding?.tvMath?.append("$inputNumber $operator")
        }
    }

    private fun calculator(resultNumber: Double, inputNumber: Double, operator: String): Double {
        return when (operator) {
            "+" -> resultNumber + inputNumber
            "-" -> resultNumber - inputNumber
            "/" -> resultNumber / inputNumber
            "%" -> resultNumber % inputNumber
            "*" -> resultNumber * inputNumber
            "=" -> inputNumber
            else -> resultNumber
        }
    }

    fun delete(view: View){

        if (isFirstInput) {
            var getTvResult: String = mBinding?.tvResult?.text.toString()
            if (getTvResult.length > 1) {
                getTvResult = getTvResult.substring(0, getTvResult.length - 1)
                mBinding?.tvResult?.text = getTvResult
            } else {
                mBinding?.tvResult?.text = "0"
                isFirstInput = true

            }
        }
    }

    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroy()
    }
}