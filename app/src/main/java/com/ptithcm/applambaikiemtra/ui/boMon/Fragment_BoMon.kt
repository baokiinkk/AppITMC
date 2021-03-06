package com.ptithcm.applambaikiemtra.ui.boMon


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ptithcm.applambaikiemtra.R
import com.ptithcm.applambaikiemtra.databinding.FragmentBoMonBinding
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.fragment__bo_mon.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress
import java.util.*


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

        GlobalScope.launch(Dispatchers.IO){
            if(isOnline())
                viewModel.loadDatatoSQl()
            else
                viewModel.loadData()
        }

        val bd: FragmentBoMonBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment__bo_mon,container,false)
        bd.lifecycleOwner=this
        bd.viewmodel=viewModel

        viewModel.list.observe(viewLifecycleOwner, Observer {
                if(it != null)
                {
                    boMon_swipe.setRefreshing(false)
                    catload.dismiss()
                    boMon_swipe.setRefreshing(false)
                    boMon_swipe.setWaveARGBColor(100,109,36,248)
                    boMon_swipe.setOnRefreshListener {
                            GlobalScope.launch(Dispatchers.IO){
                                if(isOnline()) {
                                    viewModel.loadDatatoSQl()
                                    GlobalScope.launch(Dispatchers.Main){
                                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()

                                    }
                                }
                                else
                                    GlobalScope.launch(Dispatchers.Main){
                                        Toast.makeText(context,"Thiết bị hiện không có mạng.xin vui lòng kiểm tra lại!!",Toast.LENGTH_SHORT).show()
                                        boMon_swipe.setRefreshing(false)
                                    }

                            }

                    }

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
        return bd.root
    }
    fun isOnline(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockaddr: SocketAddress = InetSocketAddress("8.8.8.8", 53)
            sock.connect(sockaddr, timeoutMs)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }
    }

}
