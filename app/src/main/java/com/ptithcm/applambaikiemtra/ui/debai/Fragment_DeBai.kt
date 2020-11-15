package com.ptithcm.applambaikiemtra.ui.debai


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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ptithcm.applambaikiemtra.R
import com.ptithcm.applambaikiemtra.data.db.model.DeThi
import com.ptithcm.applambaikiemtra.databinding.FragmentDeBaiBinding
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.fragment__bo_mon.*
import kotlinx.android.synthetic.main.fragment__de_bai.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress


/**
 * A simple [Fragment] subclass.
 */
class Fragment_DeBai : Fragment() {
    lateinit var adapterRecycelView:DeBaiAdapter
    val viewModel: ViewModel_DeBai by viewModel<ViewModel_DeBai>()
    val args : Fragment_DeBaiArgs by navArgs()
    var tentemp =""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.getScore()
        GlobalScope.launch(Dispatchers.IO){
            if(isOnline())
                viewModel.loadDataDeThitoSQl(args.mon)
            else
                viewModel.loadData(args.mon)
        }


        val catload = CatLoadingView()
        catload.show(activity!!.supportFragmentManager, "loading")
        catload.setText("Đợi chút nhoa!")
        catload.setClickCancelAble(false)

        val bd:FragmentDeBaiBinding=DataBindingUtil.inflate(inflater,R.layout.fragment__de_bai,container,false)
        bd.lifecycleOwner = this
        bd.viewmodel=viewModel
        viewModel.test.value="Môn "+args.mon

        viewModel.list.observe(viewLifecycleOwner, Observer {
            if(it!=null) {
                catload.dismiss()
                deBai_swipe.setRefreshing(false)
                deBai_swipe.setWaveARGBColor(100,109,36,248)
                deBai_swipe.setOnRefreshListener {
                    GlobalScope.launch {
                        if(isOnline())
                            viewModel.loadDataDeThitoSQl(args.mon)
                        else
                            viewModel.loadData(args.mon)
                    }
                            it.sortBy { it.ten }
                            adapterRecycelView.submitList(it)
                            Toast.makeText(context,"tải lại thành công.",Toast.LENGTH_SHORT).show()

                }
                adapterRecycelView = DeBaiAdapter { position, chosse ->
                        if (chosse == 1) {
                            if(it[position].socausql < 0)
                            {
                                Toast.makeText(context,"Vui lòng nhấn vào nút bên phải để tải đề thi",Toast.LENGTH_SHORT).show()
                            }
                            else{
                            var list = ""
                            for (i in 0..it[position].socau)
                                list += "0"
                            val actionToFinsh: NavDirections =
                                Fragment_DeBaiDirections.toCauHoi(
                                    it[position].ten, it[position].bomon,
                                    false, list,it[position].socaulamdung
                                )
                            findNavController().navigate(actionToFinsh)
                            }
                        }
                        else if(chosse == 2){
                            GlobalScope.launch(Dispatchers.IO) {
                                if(isOnline()){
                                    catload.show(activity!!.supportFragmentManager, "loading")
                                    viewModel.loadBaiThiToSQL(args.mon, it[position].ten)
                                    tentemp = it[position].ten
                                }
                                else
                                    GlobalScope.launch(Dispatchers.Main){
                                        Toast.makeText(context,"Thiết bị không có mạng để tải đề,vui lòng kết nối và thử lại sau!!",Toast.LENGTH_SHORT).show()
                                    }

                            }

                        }
                        else if (chosse == 3) {
                            val actionToFinsh: NavDirections =
                                Fragment_DeBaiDirections.toCauHoi(
                                    it[position].ten,
                                    it[position].bomon,
                                    true,
                                    it.get(position).list,it[position].socaulamdung
                                )
                            findNavController().navigate(actionToFinsh)
                        }
                }

                val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(context!!)
                bd.recyclerView.adapter = adapterRecycelView
                bd.recyclerView.layoutManager = linearLayout
                it.sortBy { it.ten }
                adapterRecycelView.submitList(it)

            }

        })
        viewModel.listCauHoi.observe(viewLifecycleOwner, Observer { bt->
            if(bt != null) {
                var list = ""
                for (i in 0..bt.size)
                    list += "0"
                viewModel.updateDeThi(DeThi(ten=tentemp,bomon = args.mon,socau = bt.size,socaulamdung = -1,list = list,socausql = 0))
                catload.dismiss()
                Toast.makeText(context,"Đã tải xong",Toast.LENGTH_SHORT).show()
                viewModel.loadData(args.mon)
                viewModel.listCauHoi.postValue(null)
            }
        })
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
