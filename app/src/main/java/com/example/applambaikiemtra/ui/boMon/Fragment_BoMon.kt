package com.example.applambaikiemtra.ui.boMon


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applambaikiemtra.R

import com.example.applambaikiemtra.databinding.FragmentBoMonBinding
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel



/**
 * A simple [Fragment] subclass.
 */
class Fragment_BoMon : Fragment() {
    val viewModel: ViewModel_BoMon by viewModel<ViewModel_BoMon>()
    var x    :MutableList<String> = mutableListOf()
    var listAdapter:BoMonAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val catload = CatLoadingView()
        catload.show(activity!!.supportFragmentManager, "loading")
        catload.setText("Đợi chút nhoa!")
        catload.setClickCancelAble(false)

        val cm: ConnectivityManager? = activity?.getSystemService(Context.CONNECTIVITY_SERVICE ) as ConnectivityManager?
        val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if(isConnected ==true)
            viewModel.loadDatatoSQl()
        else
            viewModel.loadData()
        val bd: FragmentBoMonBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment__bo_mon,container,false)
        bd.lifecycleOwner=this
        bd.viewmodel=viewModel

        viewModel.list.observe(viewLifecycleOwner, Observer {
                if(it != null)
                {
                    catload.dismiss()
                    viewModel.test.value= it.size.toString()+" môn"

                    listAdapter= BoMonAdapter { position ->
                        val actionToFinsh: NavDirections =Fragment_BoMonDirections.toBai(
                                it[position].tenBoMon)
                            findNavController().navigate(actionToFinsh  )
                    }
                    listAdapter!!.submitList(it)
                    val linearLayout: RecyclerView.LayoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
                    bd.recyclerView.adapter=listAdapter
                    bd.recyclerView.layoutManager=linearLayout


                 }
            }
        )
        viewModel.toLogin.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it == true)
                {
                    if(isConnected ==true) {
                        viewModel.loadDatatoSQl()
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                        viewModel.toLogin.postValue(false)
                    }
                    else
                        Toast.makeText(context,"Thiết bị hiện không có mạng.xin vui lòng kiểm tra lại!!",Toast.LENGTH_SHORT).show()
                }
            }
        })


        return bd.root
    }

}
