package com.ddrx.ddrxfront


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ddrx.ddrxfront.Model.Card

/**
 * Created by dokym on 2018/3/30.
 */
class TestingPageFragment : Fragment() {

    var card: Card? = null

    fun getAnswer(): String {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}