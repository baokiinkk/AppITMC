package com.ptithcm.applambaikiemtra.ui.debai


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ptithcm.applambaikiemtra.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ptithcm.applambaikiemtra.data.db.model.BaiThi
import com.ptithcm.applambaikiemtra.data.db.model.DeThi
import com.ptithcm.applambaikiemtra.databinding.FragmentDeBaiBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.fragment__bo_mon.*
import kotlinx.android.synthetic.main.fragment__de_bai.*
import kotlin.math.abs


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

        val cm: ConnectivityManager? = activity?.getSystemService(Context.CONNECTIVITY_SERVICE ) as ConnectivityManager?
        val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        viewModel.getScore()
        if(isConnected)
            viewModel.loadDataDeThitoSQl(args.mon)
        else
            viewModel.loadData(args.mon)

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
                deBai_swipe.setWaveRGBColor(255,255,255)
                deBai_swipe.setOnRefreshListener {
                    deBai_swipe.postDelayed(
                        Runnable {
                            if(isConnected ==true)
                                viewModel.loadDataDeThitoSQl(args.mon)
                            else
                                viewModel.loadData(args.mon)
                            it.sortBy { it.ten }
                            adapterRecycelView.submitList(it)
                            Toast.makeText(context,"tải lại thành công.",Toast.LENGTH_SHORT).show()
                            deBai_swipe.setRefreshing(false) }, 1000
                    )
                }
                adapterRecycelView = DeBaiAdapter { position, chosse ->
                        if (chosse == 1) {
                            if(it[position].socausql < 0)
                            {
                                Toast.makeText(context,"Vui lòng kết nối mạng và tải đề thi",Toast.LENGTH_SHORT).show()
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
                            if(isConnected ==true){
                                catload.show(activity!!.supportFragmentManager, "loading")
                                viewModel.loadBaiThiToSQL(args.mon, it[position].ten)
                                tentemp = it[position].ten
                            }
                            else
                                Toast.makeText(context,"Thiết bị không có mạng để tải đề,vui lòng kết nối và thử lại sau!!",Toast.LENGTH_SHORT).show()

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
                it.sortByDescending { it.ten }
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
            }
        })
        return bd.root
    }



}
