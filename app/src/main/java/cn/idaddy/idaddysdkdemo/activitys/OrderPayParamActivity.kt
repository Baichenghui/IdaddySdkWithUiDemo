package cn.idaddy.idaddysdkdemo.activitys

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import cn.idaddy.android.opensdk.lib.IdaddySdk
import cn.idaddy.android.opensdk.lib.order.OnCreateOrderCallback
import cn.idaddy.idaddysdkdemo.adapters.AudioRankListAdapter
import cn.idaddy.idaddysdkdemo.R
import kotlinx.android.synthetic.main.activity_audio_rank_list_layout.*

class OrderPayParamActivity : AppCompatActivity() {
    lateinit var audioRankLIstAdapter: AudioRankListAdapter

    var list: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_rank_list_layout)
        audioRankLIstAdapter = AudioRankListAdapter(layoutInflater)
        audio_rank_list.adapter = audioRankLIstAdapter

        intent?.let {
            var goodsId = it.getStringExtra("goodsId")

            if (goodsId.isNullOrBlank()) {
                goodsId = "2938";
            }
            IdaddySdk.createOrderByGoodsId(goodsId.toInt(),object : OnCreateOrderCallback {
                override fun onGetOrderSuccess(successRet: String?) {
                    successRet?.let {
                        list.add(it)
                        audioRankLIstAdapter.refresh(list)
                    }

                    if (list.count() > 0) {
                        Toast.makeText(this@OrderPayParamActivity,"获取支付参数成功",Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@OrderPayParamActivity,"获取支付参数失败", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onGetOrderFailure(failureRet: String?) {
                    Toast.makeText(this@OrderPayParamActivity,"获取支付参数失败", Toast.LENGTH_SHORT).show()
                }
            })
        }


    }
}