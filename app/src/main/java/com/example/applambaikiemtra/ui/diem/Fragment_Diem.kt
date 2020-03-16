package com.example.applambaikiemtra.ui.diem


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import com.example.applambaikiemtra.R
import kotlinx.android.synthetic.main.fragment__diem.view.*

/**
 * A simple [Fragment] subclass.
 */
class Fragment_Diem : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view:View= inflater.inflate(R.layout.fragment__diem, container, false)
        view.btn.setOnClickListener(View.OnClickListener {

        })
        return view
    }


}
