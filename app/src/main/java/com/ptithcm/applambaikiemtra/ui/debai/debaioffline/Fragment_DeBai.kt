package com.ptithcm.applambaikiemtra.ui.debai.debaioffline


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ptithcm.applambaikiemtra.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ptithcm.applambaikiemtra.databinding.FragmentDeBaiBinding
import com.ptithcm.applambaikiemtra.ui.boMon.Fragment_BoMonDirections
import com.ptithcm.applambaikiemtra.ui.debai.DeBaiAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.fragment__de_bai.*


/**
 * A simple [Fragment] subclass.
 */
class Fragment_DeBai : Fragment() {
    lateinit var adapterRecycelView: DeBaiAdapter
    val viewModel: ViewModel_DeBai by viewModel<ViewModel_DeBai>()
    val args: Fragment_DeBaiArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.loadData(args.mon)

        val catload = CatLoadingView()
        catload.show(activity!!.supportFragmentManager, "loading")
        catload.setText("Đợi chút nhoa!")
        catload.setClickCancelAble(false)

        val bd: FragmentDeBaiBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment__de_bai, container, false)
        bd.lifecycleOwner = this
        bd.viewmodel = viewModel
        viewModel.test.value = "Môn " + args.mon

        viewModel.list.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                catload.dismiss()
                deBai_swipe.setWaveRGBColor(255, 255, 255)
                deBai_swipe.setOnRefreshListener {
                    deBai_swipe.postDelayed(
                        Runnable {
                            viewModel.loadData(args.mon)
                            it.sortBy { it.ten }
                            adapterRecycelView.submitList(it)
                            Toast.makeText(context, "tải lại thành công.", Toast.LENGTH_SHORT)
                                .show()
                            deBai_swipe.setRefreshing(false)
                        }, 1000
                    )
                }
                adapterRecycelView =
                    DeBaiAdapter({ position, chosse ->
                        if (chosse == 1) {
                            var list = ""
                            for (i in 0..it[position].socau)
                                list += "0"
                            val actionToFinsh: NavDirections =
                                Fragment_DeBaiDirections.toCauHoi(
                                    it[position].ten, it[position].bomon,
                                    false, list, it[position].socaulamdung
                                )
                            findNavController().navigate(actionToFinsh)
                        } else if (chosse == 3) {
                            val actionToFinsh: NavDirections =
                                Fragment_DeBaiDirections.toCauHoi(
                                    it[position].ten,
                                    it[position].bomon,
                                    true,
                                    it.get(position).list, it[position].socaulamdung
                                )
                            findNavController().navigate(actionToFinsh)
                        }
                    }, { false })

                val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(context!!)
                bd.recyclerView.adapter = adapterRecycelView
                bd.recyclerView.layoutManager = linearLayout
                it.sortByDescending { it.ten }
                adapterRecycelView.submitList(it)

            }
        })

        bd.floatBtn.setOnClickListener()
        {
            val actionToFinsh: NavDirections = Fragment_DeBaiDirections.toDown(
                args.mon
            )
            findNavController().navigate(actionToFinsh)
        }
        return bd.root
    }


}
