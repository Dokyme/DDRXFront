package com.ddrx.ddrxfront

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.ddrx.ddrxfront.Model.Card
import com.ddrx.ddrxfront.Model.Model
import kotlinx.android.synthetic.main.fragment_training.*

/**
 * Created by dokym on 2018/3/30.
 */
class TrainingPageFragment : Fragment() {

    var card: Card? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_training, container, false)
        val linearlayout = view.findViewById<LinearLayout>(R.id.linearout_training_fragment)
        for (item in card!!.cardDisplayItem) {
            val linearLayout = LinearLayout(linearlayout.context)

            if (item.isName_visible) {
                val itemName = TextView(linearlayout.context)
                itemName.text = item.name
                itemName.gravity = Gravity.START
                linearLayout.addView(itemName)
            }

            val textView = TextView(linearlayout.context)
            textView.text = item.data
            textView.textSize = item.text_size * 15.0f
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            when (item.align) {
                Model.LEFT_ALIGN -> {
                    linearLayout.gravity = Gravity.START
                    layoutParams.gravity = Gravity.START
                    layoutParams.setMargins(4, 4, 4, 4)
                }
                Model.MID_ALIGN -> linearLayout.gravity = Gravity.CENTER
                Model.RIGHT_ALIGN -> linearLayout.gravity = Gravity.END
            }
            textView.layoutParams = layoutParams
            linearLayout.addView(textView)
            linearlayout.addView(linearLayout)
        }

        return view
    }
}