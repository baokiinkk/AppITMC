package com.example.applambaikiemtra.ui.cauhoi


import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applambaikiemtra.R
import com.example.applambaikiemtra.databinding.FragmentBaiThiBinding
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd:FragmentBaiThiBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_bai_thi,container,false)
        bd.lifecycleOwner = this
        bd.viewmodel=viewmodel
        getData()
        viewmodel.list.observe(viewLifecycleOwner, Observer {
            if(it == null)
                Toast.makeText(context,"Rong",Toast.LENGTH_SHORT).show()
            else
            {
                adapterRecycelView=  AdapterRecycleView(viewmodel.list)
               // val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(context!!,LinearLayoutManager.HORIZONTAL,false)
                bd.recyclerView.adapter=adapterRecycelView
            }
        })
        val start = object :CountDownTimer(10000, 1000)
        {
            override fun onFinish() {
                viewmodel.text.value="00:00"
               // viewmodel.cauDung.value=adapterRecycelView.socauDung.toString()
                openDialog()
                adapterRecycelView.boolean=true
                adapterRecycelView.notifyDataSetChanged()
            }

            override fun onTick(millisUntilFinished: Long) {
                var phut: Long =millisUntilFinished/60000
                var giay:Long=(millisUntilFinished/1000)%60
                viewmodel.text.value=phut.toString()+":"+giay.toString()
            }

        }.start()
        viewmodel.check.observe(viewLifecycleOwner, Observer {
            if(it == true)
            {
                start.onFinish()
                start.cancel()
                textView2.isEnabled=false

            }

        })


        return bd.root
    }
    fun getData()
    {
        viewmodel.getData(args.mon,args.ten)
    }
    fun openDialog() {

        val dialog: Dialog=Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog)
        dialog.soCau.text=viewmodel.cauDung.value+"/"+ viewmodel.list.value!!.size
        dialog.button.setOnClickListener { dialog.cancel() }
        dialog.show()
    }

}

