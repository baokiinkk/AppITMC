package com.example.applambaikiemtra.ui.cauhoi


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.applambaikiemtra.R
import com.example.applambaikiemtra.data.db.model.DeThi
import com.example.applambaikiemtra.databinding.FragmentBaiThiBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.fragment_bai_thi.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit
import kotlin.math.abs


/**
 * A simple [Fragment] subclass.
 */
class Fragment_BaiThi : Fragment() {
    val viewmodel:ViewModel_BaiThi by viewModel<ViewModel_BaiThi>()
    val args :Fragment_BaiThiArgs by navArgs()
    var tabLayoutMediator:TabLayoutMediator? = null
    lateinit var adapterRecycelView: AdapterRecycleView
    var start:CountDownTimer? = null
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
            viewmodel.loadBaiThiToSQL(args.mon,args.tenDeThi)
        else
            getData()
        viewmodel.list.observe(viewLifecycleOwner, Observer {
            if(it != null)
            {
                bd.progressBar.visibility=View.GONE
                adapterRecycelView=  AdapterRecycleView(viewmodel.list,args.listChon)
                {
                   for(i in 0..adapterRecycelView.listLuuVitri.size-1)
                    {
                        if(adapterRecycelView.listLuuVitri[i] != '0')
                        {
                            val text =TextView(context)
                            text.setTextColor(Color.WHITE)
                            text.setBackgroundResource(R.drawable.selected)
                            text.text=(i+1).toString();
                            text.textAlignment=View.TEXT_ALIGNMENT_CENTER
                            text.textSize=20f
                            val tab:TabLayout.Tab= tablayout.getTabAt(i)!!

                                tab.setCustomView(null)
                                tab.setCustomView(text)
                        }

                    }
                }
                bd.recyclerView.adapter=adapterRecycelView

                seekBar.max=20
                seekBar.setProgress(10)
                var processDefault=10
                seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        var k=1;
                        if(progress <processDefault) k=-1;
                        bd.recyclerView.scaleX=bd.recyclerView.scaleX+k*(abs(progress-processDefault)*1.0f)/100
                        bd.recyclerView.scaleY=bd.recyclerView.scaleY+k*(abs(progress-processDefault)*1.0f)/100

                        processDefault=progress
                    }

                })

                 tabLayoutMediator = TabLayoutMediator(
                    bd.tablayout,
                    bd.recyclerView,
                    TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                        tab.text = (position + 1).toString()
                    }
                )
                tabLayoutMediator!!.attach()

                adapterRecycelView.notifyDataSetChanged()
            }
            start = object :CountDownTimer((it.size*50000).toLong(), 1000)
            {
                override fun onFinish() {
                    viewmodel.text.value="00:00"
                    if(args.check ==false)
                        openDialog()
                    showErrorTabLayout()
                    adapterRecycelView.boolean=true
                    adapterRecycelView.notifyDataSetChanged()
                }

                override fun onTick(millisUntilFinished: Long) {
                    val FORMAT:String = "%02d:%02d:%02d"
                    viewmodel.text.value=""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
                }

            }
            if(args.check == true)
            {
                (start as CountDownTimer).onFinish()
            }
            else (start as CountDownTimer).start()
            viewmodel.check.observe(viewLifecycleOwner, Observer {
                if(it == true)
                {
                    (start as CountDownTimer).onFinish()
                    (start as CountDownTimer).cancel()
                    txtNopBai.isEnabled=false

                }

            })
        })



        return bd.root
    }
    fun getData()
    {
        viewmodel.getData(args.tenDeThi)
    }
    @SuppressLint("WrongConstant", "ResourceType")
    fun showErrorTabLayout()
    {
        var x=adapterRecycelView.listLuuVitri;
        var a= mutableListOf<Int>()
        for(i in 0..x.size-1)
        {
            if(x[i].toString() != viewmodel.list.value?.get(i)?.dapan)
                a.add(i)
        }
        tabLayoutMediator!!.detach()
        tabLayoutMediator = TabLayoutMediator(
            tablayout,
            recyclerView,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->

                if(x[position].toString() != viewmodel.list.value?.get(position)?.dapan)
                    {
                        val text =TextView(context)
                        tab.setCustomView(null)
                        text.setTextColor(Color.WHITE)
                        text.textAlignment=View.TEXT_ALIGNMENT_CENTER
                        text.textSize=20f
                        text.text= (position+1).toString();
                        text.setBackgroundResource(R.drawable.item_tab)
                        tab.setCustomView(text);
                    }
                else{
                        val text =TextView(context)
                        tab.setCustomView(null)
                        text.setTextColor(Color.WHITE)
                        text.textAlignment=View.TEXT_ALIGNMENT_CENTER
                        text.textSize=20f
                        text.text= (position+1).toString();
                        text.setBackgroundResource(R.drawable.selected)
                        tab.setCustomView(text);

                }
            }
        )
        tabLayoutMediator!!.attach()
    }
    fun openDialog() {
        var dem=0
        var x=adapterRecycelView.listLuuVitri
        var updateListChon:String=""
        for(i in 0..x.size-1){
            if(x[i].toString() == viewmodel.list.value?.get(i)?.dapan)
                dem++
            updateListChon+=x[i]
        }
        viewmodel.updateDeThi(DeThi(args.tenDeThi,args.mon,dem,viewmodel.list.value!!.size,updateListChon))
        val dialog: Dialog=Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog)
        dialog.soCau.text=dem.toString()+"/"+ viewmodel.list.value!!.size.toString()+" Câu"
        dialog.diem.text=((Math.ceil(((dem*10)*1.0/viewmodel.list.value!!.size)*100)).toDouble()/100).toString()
        dialog.button.setOnClickListener { dialog.cancel() }
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        start?.cancel()
    }

}

