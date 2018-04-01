package com.ddrx.ddrxfront


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.ddrx.ddrxfront.Model.CardFieldTrainingItem
import com.ddrx.ddrxfront.Model.Model

/**
 * Created by dokym on 2018/3/30.
 */
class TestingPageFragment : Fragment() {

    lateinit var item: CardFieldTrainingItem
    var quesIndex: Int? = null
    lateinit var answerTextview: TextView

    fun showAnswer() {
        answerTextview.visibility = View.VISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_testing, container, false)
        val layout: LinearLayout = view.findViewById(R.id.linear_fragment_testing)
        var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        if (item.isName_visible) {
            val nameTextView = TextView(context)
            nameTextView.text = item.name
            layout.addView(nameTextView, params)
        }

        val keywordsTextview = TextView(context)
        for (keyword in item.keywords)
            keywordsTextview.append(keyword)
        when (item.align) {
            Model.RIGHT_ALIGN -> keywordsTextview.gravity = Gravity.END
            Model.MID_ALIGN -> keywordsTextview.gravity = Gravity.CENTER
            Model.LEFT_ALIGN -> keywordsTextview.gravity = Gravity.START
        }
        keywordsTextview.textSize = item.text_size * 1.0f
        layout.addView(keywordsTextview, params)

        val input = EditText(context)
        input.gravity = Gravity.CENTER
        layout.addView(input, params)

        answerTextview = EditText(context)
        answerTextview.visibility = View.INVISIBLE
        answerTextview.text = item.data
        answerTextview.gravity = Gravity.CENTER
        layout.addView(answerTextview, params)

        return view
    }
}