package com.ptithcm.applambaikiemtra.ui.debai.debaionline

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ptithcm.applambaikiemtra.R
import com.ptithcm.applambaikiemtra.databinding.FragmentDeBaiBinding
import com.ptithcm.applambaikiemtra.databinding.FragmentDownloadBinding
import com.ptithcm.applambaikiemtra.ui.debai.DeBaiAdapter
import com.ptithcm.applambaikiemtra.ui.debai.debaioffline.Fragment_DeBaiArgs
import com.ptithcm.applambaikiemtra.ui.debai.debaioffline.Fragment_DeBaiDirections
import com.ptithcm.applambaikiemtra.ui.debai.debaioffline.ViewModel_DeBai
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.fragment__de_bai.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DownloadFragment : Fragment() {
    lateinit var adapterRecycelView: DeBaiAdapter
    val viewModel: DownDeBaiViewModel by viewModel()
    val args: Fragment_DeBaiArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cm: ConnectivityManager? =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        val catload = CatLoadingView()
        catload.show(activity!!.supportFragmentManager, "loading")
        catload.setText("Đợi chút nhoa!")
        catload.setClickCancelAble(false)

        val bd: FragmentDownloadBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_download, container, false)
        bd.lifecycleOwner = this
        bd.viewmodel = viewModel
        viewModel.test.value = "Môn " + args.mon
        viewModel.loadDataFromFireBase(args.mon)
        viewModel.listFirebase.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                catload.dismiss()
                adapterRecycelView =
                DeBaiAdapter ({ position, chosse ->
                    if (chosse == 1 ) {
                        if(it[position].isDownload == false) {
                            viewModel.loadDataDeThitoSQl(it[position].bomon)
                            it[position].isDownload = true
                            viewModel.updateDethi(it[position])
                        }else{
                            Toast.makeText(context, "Đã tải đề này rồi không thể tải lại",Toast.LENGTH_SHORT).show()
                        }
                    }
                }, {position-> it[position].isDownload})
                adapterRecycelView.submitList(it)
                deBai_swipe.setWaveRGBColor(255, 255, 255)
                deBai_swipe.setOnRefreshListener {
                    deBai_swipe.postDelayed(
                        Runnable {
                            viewModel.loadDataFromFireBase(args.mon)
                            it.sortBy { it.ten }
                            Toast.makeText(context, "tải lại thành công.", Toast.LENGTH_SHORT)
                                .show()
                            adapterRecycelView.submitList(it)
                            deBai_swipe.setRefreshing(false)
                        }, 1000
                    )
                }
                adapterRecycelView.submitList(it)
                val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(context!!)
                bd.recyclerView.adapter = adapterRecycelView
                bd.recyclerView.layoutManager = linearLayout
                it.sortByDescending { it.ten }

            }
        })
        return bd.root
    }
}