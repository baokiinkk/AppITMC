package com.example.applambaikiemtra.ui.debai


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
    lateinit var adapterRecycelView:DeBaiAdapter
    val viewModel: ViewModel_DeBai by viewModel<ViewModel_DeBai>()
    val args : Fragment_DeBaiArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd:FragmentDeBaiBinding=DataBindingUtil.inflate(inflater,R.layout.fragment__de_bai,container,false)
        bd.lifecycleOwner = this
        bd.viewmodel=viewModel
        viewModel.test.value="Môn"+args.mon

        // load data
        val cm: ConnectivityManager? = activity?.getSystemService(Context.CONNECTIVITY_SERVICE ) as ConnectivityManager?
        val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        viewModel.loadData(args.mon)

        viewModel.list.observe(viewLifecycleOwner, Observer {
            if(it!=null) {

                adapterRecycelView = DeBaiAdapter {position,chosse->

                    if(chosse==1){
                        if(it[position].isDown == true)
                        {
                            val actionToFinsh: NavDirections =
                                Fragment_DeBaiDirections.toCauHoi(
                                    it.get(position).id,it[position].ten,it[position].bomon
                                )
                            findNavController().navigate(actionToFinsh)
                        }
                        else
                            Toast.makeText(context,"Bạn vui lòng kết nối mạng và tải dề thi để làm offline.xin cảm ơn",Toast.LENGTH_SHORT).show()

                    }
                    else if(chosse == 2)
                    {
                        if(isConnected == true)
                        {
                            viewModel.loadDataBaiThiToSQL(args.mon,it.get(position).ten,it.get(position).id)
                            it[position].isDown=true
                            viewModel.updateDeThiToSQL(it[position])
                            Toast.makeText(context,"Tải hoàn tât",Toast.LENGTH_SHORT).show()
                        }
                        else
                            Toast.makeText(context,"Bạn vui lòng kiểm tra lại đường truyền mạng!!",Toast.LENGTH_SHORT).show()
                    }

                }

                val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(context!!)
                bd.recyclerView.adapter = adapterRecycelView
                bd.recyclerView.layoutManager = linearLayout
                adapterRecycelView.submitList(it)

            }
        })


        return bd.root
    }


}
