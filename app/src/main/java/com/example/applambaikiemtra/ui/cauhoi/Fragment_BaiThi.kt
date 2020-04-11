package com.example.applambaikiemtra.ui.cauhoi


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.applambaikiemtra.R
import com.example.applambaikiemtra.data.db.model.DeThi
import com.example.applambaikiemtra.databinding.FragmentBaiThiBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.fragment_bai_thi.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class Fragment_BaiThi : Fragment() {
    val viewmodel:ViewModel_BaiThi by viewModel<ViewModel_BaiThi>()
    val args :Fragment_BaiThiArgs by navArgs()
    lateinit var adapterRecycelView: AdapterRecycleView
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd:FragmentBaiThiBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_bai_thi,container,false)
        bd.lifecycleOwner = this
        bd.viewmodel=viewmodel
        viewmodel.title= args.mon

        //check online
        val cm: ConnectivityManager? = activity?.getSystemService(Context.CONNECTIVITY_SERVICE ) as ConnectivityManager?
        val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if(isConnected ==true)
            viewmodel.loadBaiThiToSQL(args.mon,args.tenDeThi,args.idDeThi)
        else
            getData()
        viewmodel.list.observe(viewLifecycleOwner, Observer {
            if(it == null)
            {
                val progressBar = ProgressBar(context)
                progressBar.showContextMenu()
            }

            else
            {
                adapterRecycelView=  AdapterRecycleView(viewmodel.list,args.listChon)
                bd.recyclerView.adapter=adapterRecycelView
                TabLayoutMediator(
                    bd.tablayout,bd.recyclerView, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                        tab.text=(position+1).toString()
                    }
                ).attach()
            }
            val start = object :CountDownTimer(20000, 1000)
            {
                override fun onFinish() {
                    viewmodel.text.value="00:00"
                    if(args.check ==false)
                        openDialog()
                    adapterRecycelView.boolean=true
                    adapterRecycelView.notifyDataSetChanged()
                }

                override fun onTick(millisUntilFinished: Long) {
                    var phut: Long =millisUntilFinished/60000
                    var giay:Long=(millisUntilFinished/1000)%60
                    viewmodel.text.value=phut.toString()+":"+giay.toString()
                }

            }
            if(args.check == true)
            {
                start.onFinish()
            }
            else start.start()
            viewmodel.check.observe(viewLifecycleOwner, Observer {
                if(it == true)
                {
                    start.onFinish()
                    start.cancel()
                    textView2.isEnabled=false

                }

            })
        })



        return bd.root
    }
    fun getData()
    {
        viewmodel.getData(args.idDeThi)
    }
    fun openDialog() {
        var dem=0
        var x=adapterRecycelView.listLuuVitri
        Log.d("ssssssssss",x.toString())
        var updateListChon:String=""
        for(i in 0..x.size-1){
            if(x[i].toString() == viewmodel.list.value?.get(i)?.dapan)
                dem++
            updateListChon+=x[i]
        }
        viewmodel.updateDeThi(DeThi(args.idDeThi,args.tenDeThi,args.mon,dem,viewmodel.list.value!!.size,updateListChon))
        val dialog: Dialog=Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog)
        dialog.soCau.text=dem.toString()+"/"+ viewmodel.list.value!!.size.toString()+" Câu"
        dialog.diem.text=((dem*10)/viewmodel.list.value!!.size).toString()
        dialog.button.setOnClickListener { dialog.cancel() }
        dialog.show()
    }

}

