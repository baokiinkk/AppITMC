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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.applambaikiemtra.R
import com.example.applambaikiemtra.data.db.model.DeThi
import com.example.applambaikiemtra.databinding.FragmentBaiThiBinding
import com.example.applambaikiemtra.ui.boMon.Fragment_BoMonDirections
import com.google.android.gms.ads.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.roger.catloadinglibrary.CatLoadingView
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
    private var mInterstitialAd: InterstitialAd? = null
    lateinit var mAdView : AdView
    var start:CountDownTimer? = null
    var demtg = 0
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        viewmodel.updateDeThi(DeThi(args.tenDeThi,args.mon,args.caulamdung,args.listChon.length,args.listChon.length,args.listChon))
        val catload = CatLoadingView()
        catload.show(activity!!.supportFragmentManager, "loading")
        catload.setText("Đợi chút nhoa!")
        catload.setClickCancelAble(false)

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
        mInterstitialAd = newInterstitialAd(R.string.interstitial_ad_unit_id)
        loadInterstitial()
        MobileAds.initialize(context) {}

        val adRequest = AdRequest.Builder()
            .build()
        mAdView = bd.adView
        mAdView.loadAd(adRequest)

        var temp=args.tenDeThi
        for(i in temp.length-1 downTo 0)
        {
            if(temp[i] == '-')
            {
                temp=temp.substring(0,i);
                break;
            }
        }
        bd.text3.text=temp
        viewmodel.list.observe(viewLifecycleOwner, Observer {
            if(it != null)
            {
                catload.dismiss()
                adapterRecycelView=  AdapterRecycleView(viewmodel.list,args.listChon)
                {
                    viewmodel.updateDeThi(DeThi(args.tenDeThi,args.mon,args.caulamdung,adapterRecycelView.listLuuVitri.size,adapterRecycelView.listLuuVitri.size,args.listChon))
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

            var time = (it.size*50000).toLong();
            if(args.mon == "Vật lí 3")
                time =time*3-600000

            // hàm đếm thời gian với tốc độ đếm là 1s .
            start = object :CountDownTimer(time, 1000)
            {
                //hàm khi kết thúc thì sẽ mở hộp thoại báo điểm
                override fun onFinish() {
                    viewmodel.text.value="00:00"
                    if(args.check ==false)
                        openDialog()
                    showErrorTabLayout()
                    adapterRecycelView.boolean=true
                    adapterRecycelView.notifyDataSetChanged()
                }

                // cứ mỗi giây thì hàm này sẽ dx gọi.
                override fun onTick(millisUntilFinished: Long) {
                    // cứ mỗi giây biến demtg++ - demtg chính là thời gian mà người dùng tốn để hoàn thành bài thi
                    demtg++

                    // thời gian sẽ được gán vào biến text.biến text sẽ liên kết với textview ở màn hình làm bài thi.
                    val FORMAT:String = "%02d:%02d:%02d"  // chuẩn hóa nó thành dạng giờ:phút:giây
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
                bd.txtNopBai.visibility=View.GONE
                bd.textView2.visibility=View.GONE
                (start as CountDownTimer).onFinish()
            }
            else (start as CountDownTimer).start()
            viewmodel.check.observe(viewLifecycleOwner, Observer {
                if(it == true)
                {
                    if (mInterstitialAd?.isLoaded == true) {
                        mInterstitialAd?.show()
                    } else {
                        mInterstitialAd = newInterstitialAd(R.string.interstitial_ad_unit_id)
                        loadInterstitial()
                    }
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

        // khi người dùng click vào những đáp án thì viewmodel.list sẽ lưu các sự lựa chọn đó.giờ ta so list đó với list kết quả.
        // nếu trùng nhau thì dem++
        // update lại listchon để có thể lần sau xem lại. và lưu update này vào csdl
        var dem=0
        var x=adapterRecycelView.listLuuVitri
        var updateListChon:String=""
        for(i in 0..x.size-1){
            if(x[i].toString() == viewmodel.list.value?.get(i)?.dapan)
                dem++
            updateListChon+=x[i]
        }
        viewmodel.updateDeThi(DeThi(args.tenDeThi,args.mon,dem,viewmodel.list.value!!.size,viewmodel.list.value!!.size,updateListChon))
        val dialog: Dialog=Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog)
        dialog.soCau.text=dem.toString()+"/"+ viewmodel.list.value!!.size.toString()

        // từ biến dem trên để tính ra số điểm.
        dialog.diem.text=((Math.ceil(((dem*10)*1.0/viewmodel.list.value!!.size)*100)).toDouble()/100).toString()
        dialog.button.setOnClickListener { dialog.cancel() }
        val FORMAT:String = "%02d:%02d"
        val tg:Long= (demtg*1000).toLong()
        dialog.textView5.text="Thời gian làm: "+String.format(FORMAT,
            TimeUnit.MILLISECONDS.toMinutes(tg) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(tg)),
            TimeUnit.MILLISECONDS.toSeconds(tg) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(tg)))
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        start?.cancel()
    }
    private fun loadInterstitial() {
        // Disable the next level button and load the ad.
        val adRequest = AdRequest.Builder()
            .setRequestAgent("android_studio:ad_template")
            .build()
        mInterstitialAd?.loadAd(adRequest)
    }
    private fun newInterstitialAd(data:Int): InterstitialAd {
        return InterstitialAd(context).apply {
            adUnitId = getString(data)
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                }

                override fun onAdClosed() {

                }
            }
        }
    }


}

