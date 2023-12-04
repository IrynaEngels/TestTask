package com.ireneengels.myapplication.presentation.text

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ireneengels.myapplication.R

class TextFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_text, container, false)


        val textView: BgColorTextView = view.findViewById(R.id.centerTextView)
        displayText(getString(R.string.example_text), textView)

        return view
    }

    private fun displayText(input: String, textView: BgColorTextView) {
        val modifiedText = input.replace("\n", " ")
        textView.setMaxLength(150)
        textView.text = modifiedText.uppercase()
    }
}

