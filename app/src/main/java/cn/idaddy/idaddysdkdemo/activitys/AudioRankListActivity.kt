package cn.idaddy.idaddysdkdemo.activitys

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import cn.idaddy.android.opensdk.lib.IdaddySdk
import cn.idaddy.android.opensdk.lib.OutBean.OutAudioRankBean
import cn.idaddy.android.opensdk.lib.audioinfo.rank.OnGetAudioRankListCallback
import cn.idaddy.idaddysdkdemo.adapters.AudioRankListAdapter
import cn.idaddy.idaddysdkdemo.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_audio_rank_list_layout.*

class AudioRankListActivity :AppCompatActivity(){
    lateinit var  audioRankLIstAdapter: AudioRankListAdapter

    var list: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_rank_list_layout)
        audioRankLIstAdapter = AudioRankListAdapter(layoutInflater)
        audio_rank_list.adapter = audioRankLIstAdapter

        intent?.let {
            val rankType = it.getStringExtra("rankType")

            IdaddySdk.getAudioRankList(rankType,10,null,object : OnGetAudioRankListCallback {
                override fun onGetAudioRankSuccess(successRet: String?) {
                    val audioRankBean = Gson().fromJson(successRet, OutAudioRankBean::class.java)
                    for (audioBean in audioRankBean.list?.let { it}
                            ?: ArrayList<OutAudioRankBean.OutAudioBean>()) {
                        audioBean?.let { list.add(Gson().toJson(audioBean)) }
                    }
                    audioRankLIstAdapter.refresh(list)
                    if (list.isEmpty()){
                        Toast.makeText(this@AudioRankListActivity,"暂无排行信息",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onGetAudioRankFailure(failureRet: String?) {
                    Toast.makeText(this@AudioRankListActivity,failureRet,Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
