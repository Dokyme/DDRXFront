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
        for (item in card!!.cardDisplayItem) {
            val newLine = LinearLayout(context)

            if (item.isName_visible) {
                val itemName = TextView(context)
                itemName.text = item.name
                itemName.gravity = Gravity.START
                newLine.addView(itemName)
            }

            val itemData = TextView(context)
            itemData.textSize = item.text_size * 1.0f
            when (item.align) {
                Model.LEFT_ALIGN -> {
                    val margins = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    margins.setMargins(4, 4, 4, 4)
                    itemData.layoutParams = margins
                }
                Model.MID_ALIGN -> itemData.gravity = Gravity.CENTER
                Model.RIGHT_ALIGN -> itemData.gravity = Gravity.END
            }
            newLine.addView(itemData)
            linearout_training_fragment.addView(newLine)
        }

        return view
    }
}