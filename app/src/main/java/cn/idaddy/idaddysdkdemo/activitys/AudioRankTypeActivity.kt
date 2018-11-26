package cn.idaddy.idaddysdkdemo.activitys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import cn.idaddy.idaddysdkdemo.adapters.AudioRankListAdapter
import cn.idaddy.idaddysdkdemo.R
import kotlinx.android.synthetic.main.activity_audio_rank_list_layout.*

class AudioRankTypeActivity: AppCompatActivity() {
    lateinit var  audioRankLIstAdapter: AudioRankListAdapter

    var list: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_rank_list_layout)
        audioRankLIstAdapter = AudioRankListAdapter(layoutInflater)
        audio_rank_list.adapter = audioRankLIstAdapter

        list.add("likes 小朋友最爱听")
        list.add("bestseller 热销榜")
        list.add("sleeping 睡前热播榜")
        list.add("access-week 热播榜(周)")
        list.add("girls-love 女孩最爱榜")
        list.add("boys-love 男孩最爱榜")
        list.add("exclusive 编辑推荐榜")
        list.add("favorite 收藏榜")
        list.add("morning 起床热播榜")

        audioRankLIstAdapter.refresh(list)

        audio_rank_list.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var rankType = "likes"
                if (position == 0) {
                    rankType = "likes"
                } else if (position == 1) {
                    rankType = "bestseller"
                } else if (position == 2) {
                    rankType = "sleeping"
                } else if (position == 3) {
                    rankType = "access-week"
                } else if (position == 4) {
                    rankType = "girls-love"
                } else if (position == 5) {
                    rankType = "boys-love"
                } else if (position == 6) {
                    rankType = "exclusive"
                } else if (position == 7) {
                    rankType = "favorite"
                } else if (position == 8) {
                    rankType = "morning"
                }

                startActivity(Intent(this@AudioRankTypeActivity, AudioRankListActivity::class.java)
                        .putExtra("rankType",rankType))
            }
        })
    }
}