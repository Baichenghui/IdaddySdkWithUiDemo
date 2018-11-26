package cn.idaddy.idaddysdkdemo.activitys

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import cn.idaddy.android.opensdk.lib.IdaddySdk
import cn.idaddy.android.opensdk.lib.audioinfo.OnGetAudioInfoCallback
import cn.idaddy.android.opensdk.lib.audioinfo.PushAudioInfo
import cn.idaddy.idaddysdkdemo.adapters.AudioRankListAdapter
import cn.idaddy.idaddysdkdemo.R

import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_audio_rank_list_layout.*

class AudioInfoActivity : AppCompatActivity(){
    lateinit var  audioRankLIstAdapter: AudioRankListAdapter

    var list: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_rank_list_layout)
        audioRankLIstAdapter = AudioRankListAdapter(layoutInflater)
        audio_rank_list.adapter = audioRankLIstAdapter

        intent?.let {
            var audioId = it.getStringExtra("audioId")
            var chapterId = it.getStringExtra("chapterId")
            if (audioId.isNullOrBlank()) {
                audioId = "3807";
            }
            IdaddySdk.getAudioInfo(null,audioId,chapterId,object : OnGetAudioInfoCallback {
                override fun onGetAudioInfoSuccess(successRet: String?) {
                    val pushAudioInfo = Gson().fromJson(successRet, PushAudioInfo::class.java)
                    if (pushAudioInfo != null) {
                        pushAudioInfo?.let {
                            list.add(Gson().toJson(pushAudioInfo))
                        }
                        audioRankLIstAdapter.refresh(list)

                        if (list.isEmpty()) {
                            Toast.makeText(this@AudioInfoActivity, "暂无列表信息", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onGetAudioInfoFailed(failureRet: String?) {
                    Toast.makeText(this@AudioInfoActivity,failureRet, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
