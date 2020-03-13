package com.example.applambaikiemtra.ui.boMon


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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applambaikiemtra.R
import com.example.applambaikiemtra.databinding.FragmentBoMonBinding
import com.example.applambaikiemtra.databinding.FragmentDeBaiBinding
import com.example.applambaikiemtra.ui.debai.AdapterRecycelView
import com.example.applambaikiemtra.ui.debai.Fragment_DeBaiDirections
import com.example.applambaikiemtra.ui.debai.OnItemClicks
import com.example.applambaikiemtra.ui.debai.ViewModel_DeBai
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class Fragment_BoMon : Fragment() {
   lateinit var adapterRecycelView:AdapterRecycelView
    val viewModel: ViewModel_BoMon by viewModel<ViewModel_BoMon>()
    val x :MutableList<String> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd: FragmentBoMonBinding =
            DataBindingUtil.inflate(inflater,R.layout.fragment__bo_mon,container,false)
        bd.lifecycleOwner=this
        bd.viewmodel=viewModel

        viewModel.loadData()


        viewModel.list.observe(viewLifecycleOwner, Observer {
                if(it != null)
                {
                    x.clear()
                    for(i in viewModel.list.value?.values!!)
                        x.add(i)
                    adapterRecycelView= AdapterRecycelView(x,R.layout.custom_bo_mon)
                    val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(context!!)
                    bd.recyclerView.adapter=adapterRecycelView
                    bd.recyclerView.layoutManager=linearLayout
                    adapterRecycelView.setOnItemClick(object : OnItemClicks
                    {
                        override fun OnItemClick(v: View, position: Int) {
                            val actionToFinsh: NavDirections =Fragment_BoMonDirections.toBai(x[position])
                            findNavController().navigate(actionToFinsh  )
                        }

                    })
                }
            }
        )
        viewModel.toLogin.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it == true)
                {
                    Toast.makeText(context,"Cập nhật thành công",Toast.LENGTH_SHORT).show()
                    viewModel.toLogin.postValue(false)
                }
            }
        })
        return bd.root
    }


}
