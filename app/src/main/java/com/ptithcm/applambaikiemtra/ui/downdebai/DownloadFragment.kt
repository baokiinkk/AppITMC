package com.ptithcm.applambaikiemtra.ui.downdebai

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ptithcm.applambaikiemtra.R
import com.ptithcm.applambaikiemtra.databinding.FragmentDownloadBinding

class DownloadFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: FragmentDownloadBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_download, container, false)
        return root.root
    }
}