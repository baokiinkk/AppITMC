package com.example.applambaikiemtra.ui.debai


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applambaikiemtra.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.applambaikiemtra.databinding.FragmentDeBaiBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class Fragment_DeBai : Fragment() {
    lateinit var adapterRecycelView:AdapterRecycelView
    val viewModel: ViewModel_DeBai by viewModel<ViewModel_DeBai>()
    val args : Fragment_DeBaiArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd:FragmentDeBaiBinding=DataBindingUtil.inflate(inflater,R.layout.fragment__de_bai,container,false)
        bd.lifecycleOwner = this
        bd.viewmodel=viewModel
        Toast.makeText(context,args.mon,Toast.LENGTH_SHORT).show()
        viewModel.loadData(args.mon)

        adapterRecycelView= AdapterRecycelView(viewModel.list,R.layout.custom_cauhoi)
        val linearLayout:RecyclerView.LayoutManager =LinearLayoutManager(context!!)
        bd.recyclerView.adapter=adapterRecycelView
        bd.recyclerView.layoutManager=linearLayout
        adapterRecycelView.setOnItemClick(object :OnItemClicks
        {
            override fun OnItemClick(v: View, position: Int) {
                val actionToFinsh: NavDirections =Fragment_DeBaiDirections.toCauHoi(viewModel.list[position],args.mon)
                findNavController().navigate(actionToFinsh  )
            }

        })

        viewModel.tocheck.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it == true || it == false)
                {
                   adapterRecycelView.notifyDataSetChanged()
                }
            }
        })

        return bd.root
    }


}
