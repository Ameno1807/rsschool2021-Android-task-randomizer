package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import java.lang.NumberFormatException
import kotlin.math.min

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private lateinit var communicator: Communicator


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        communicator = activity as Communicator
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        // TODO: val min = ...
        val min = view.findViewById<EditText>(R.id.min_value)
        // TODO: val max = ...
        val max = view.findViewById<EditText>(R.id.max_value)

        generateButton?.setOnClickListener {
            if(validationCheck(min,max)) {
                communicator.sendNumber(
                    max.text.toString().toInt(),
                    min.text.toString().toInt()
                )
            }
           if (!validationCheck(min, max)) {
               Toast.makeText(context, "The input data is invalid", Toast.LENGTH_LONG).show()
               view.hideKeyboard()
           }

        }
    }

    fun validationCheck(min: EditText, max: EditText): Boolean {
        val maxValue = view?.findViewById<EditText>(R.id.max_value)?.text.toString().toIntOrNull()
        val minValue = view?.findViewById<EditText>(R.id.min_value)?.text.toString().toIntOrNull()

        if (minValue == null||minValue>Int.MAX_VALUE){
            return false
        }

        if (maxValue==null||maxValue>Int.MAX_VALUE){
            return false
        }

        if (minValue > maxValue){
            return false
        }
        return true
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}