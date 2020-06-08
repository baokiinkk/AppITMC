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
import com.ptithcm.applambaikiemtra.databinding.FragmentDeBaiBinding
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.fragment__de_bai.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class Fragment_DeBai : Fragment() {
    lateinit var adapterRecycelView:DeBaiAdapter
    val viewModel: ViewModel_DeBai by viewModel<ViewModel_DeBai>()
    val args : Fragment_DeBaiArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cm: ConnectivityManager? = activity?.getSystemService(Context.CONNECTIVITY_SERVICE ) as ConnectivityManager?
        val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
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
                        {
                            if (isConnected)
                                viewModel.loadDataDeThitoSQl(args.mon)
                            else
                                viewModel.loadData(args.mon)
                            it.sortBy { it.ten }
                            adapterRecycelView.submitList(it)
                            Toast.makeText(context, "tải lại thành công.", Toast.LENGTH_SHORT)
                                .show()
                            deBai_swipe.setRefreshing(false)
                        }, 1000
                    )
                }
                adapterRecycelView = DeBaiAdapter { position, chosse ->

                    if(isConnected == false && it[position].socausql == 0)
                    {
                        Toast.makeText(context,"Vui lòng kết nối mạng để tải đề thi",Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if (chosse == 1) {
                            var list = ""
                            for (i in 0..it[position].socau)
                                list += "0"
                            val actionToFinsh: NavDirections =
                                Fragment_DeBaiDirections.toCauHoi(
                                    it[position].ten, it[position].bomon,
                                    false, list,it[position].socaulamdung
                                )
                            findNavController().navigate(actionToFinsh)
                        } else if (chosse == 3) {
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
                }

                val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(context!!)
                bd.recyclerView.adapter = adapterRecycelView
                bd.recyclerView.layoutManager = linearLayout
                it.sortByDescending { it.ten }
                adapterRecycelView.submitList(it)

            }
        })


        return bd.root
    }
}
