package com.example.applambaikiemtra.ui.cauhoi

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.applambaikiemtra.R
import kotlinx.android.synthetic.main.dialog.*


class Dialog :AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder:AlertDialog.Builder=AlertDialog.Builder(activity)
        val layoutInflater= activity?.layoutInflater
        val view=layoutInflater?.inflate(R.layout.dialog,null)

        builder.setView(view)
        builder.setPositiveButton("Xem Lại Đáp Án", DialogInterface.OnClickListener { dialog, which ->  })
        return builder.create()
    }
}