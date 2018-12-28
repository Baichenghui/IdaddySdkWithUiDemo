package cn.idaddy.idaddysdkdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import cn.idaddy.android.opensdk.lib.IDYCommon
import cn.idaddy.android.opensdk.lib.IDYConfig
import cn.idaddy.android.opensdk.lib.IdaddySdk
import cn.idaddy.android.opensdk.lib.broadcast.IDYUpdateUserInfoReceiver
import cn.idaddy.android.opensdk.lib.broadcast.OnUpdateUserInfoCallback
import cn.idaddy.android.opensdk.lib.play.FloatWindowLocation
import cn.idaddy.android.opensdk.lib.play.IDYPlayerController
import cn.idaddy.android.opensdk.lib.story.LoginActivity
import cn.idaddy.android.opensdk.lib.utils.ToastUtils
import cn.idaddy.idaddysdkdemo.activitys.*
import cn.idaddy.idaddysdkdemo.utils.RotateUtils
import kotlinx.android.synthetic.main.activity_layout_main.*


/**
 *Created by wlx on 2018/12/19 15:19.
 */
@SuppressLint("SetTextI18n")
open class NewMainActivity : AppCompatActivity(), OnUpdateUserInfoCallback {
    private lateinit var updateUserInfoReceiver: IDYUpdateUserInfoReceiver
    private lateinit var userInfointentFilter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_main)

        registerUserInfoReceiver()

        version.text = "口袋故事Android v" + BuildConfig.VERSION_NAME
        //用户信息
        refreshAccount()
        initListener()
    }

    private fun initListener() {
        enter_main_page.setOnClickListener {
            IdaddySdk.start()
        }

        change_account_btn.setOnClickListener {
//            if (StringUtils.isValidOfPhoneNumber(phone_edittext.text.toString())) {
//                IdaddySdk.setIdaddyAccountPhoneNumber(phone_edittext.text.toString())
//
//                LoginActivity.start(this)
//            } else {
//                ToastUtils.showShort(this, "请输入正确的手机号")
//            }
            IdaddySdk.setIdaddyAccountPhoneNumber(phone_edittext.text.toString())

            LoginActivity.start(this)
        }

        logout.setOnClickListener {
            IdaddySdk.logout()
        }

        changeEnviment()

        enter_audioDetail_btn.setOnClickListener {
            IdaddySdk.showAudioInfoPage(audio_id.text.toString())
        }

        show_push_radio.isChecked = false
        show_push_save_btn.isEnabled = false
        show_push_save_btn.setTextColor(Color.GRAY)
        show_push_radio.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    IdaddySdk.setPushAudioToDeviceButton(null, true)
                    show_push_save_btn.isEnabled = true
                    show_push_save_btn.setTextColor(ContextCompat.getColor(this@NewMainActivity, R.color.text_black))
                } else {
                    IdaddySdk.setPushAudioToDeviceButton(null, false)
                    show_push_save_btn.isEnabled = false
                    show_push_save_btn.setTextColor(Color.GRAY)
                }
            }
        })

        show_push_save_btn.setOnClickListener {
            if (show_push_edittext.text.toString().length>4){
                ToastUtils.showShort(this@NewMainActivity,"推送按钮文案不能超过4个字")
            }else{
                if (show_push_edittext.text.toString().isNullOrBlank()) {
                    IdaddySdk.setPushAudioToDeviceButton(null, true)
                } else {
                    IdaddySdk.setPushAudioToDeviceButton(show_push_edittext.text.toString(), true)
                }
            }
        }

        show_play_save_btn.setOnClickListener {
            if (show_push_edittext.text.toString().length>4){
                ToastUtils.showShort(this@NewMainActivity,"播放按钮文案不能超过4个字")
            }else{
                IdaddySdk.setPlayButton(show_play_edittext.text.toString())
            }
        }


        payandorder_info_radio.isChecked = false
        payandorder_info_radio.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                IdaddySdk.setUseIdaddyPay(true)
            } else {
                IdaddySdk.setUseIdaddyPay(false)
            }
        }

        getOrderlist.setOnClickListener {
            // 验证用户是否登录，是否已经是VIP
            IDYConfig.userInfoBean.data?.let {
                if (it.is_guest) {
                    if (IDYConfig.useIdaddyAccountValidate) {
                        LoginActivity.start(this)
                    } else {
                        //静默登录
                        IDYCommon.idyTokenInterface?.idyNeedAccessToken()
                    }
                } else {
                    startActivity(Intent(this@NewMainActivity, OrderListActivity::class.java))
                }
            }?: apply {

                if (IDYConfig.useIdaddyAccountValidate) {
                    LoginActivity.start(this)
                } else {
                    //静默登录
                    IDYCommon.idyTokenInterface?.idyNeedAccessToken()
                }
            }

        }

        generateOrder_btn.setOnClickListener {
            startActivity(Intent(this@NewMainActivity, OrderPayParamActivity::class.java).putExtra("goodsId", orderid_edit.text.toString()))
        }

        //是否使用播放悬浮窗
        play_float_window_box.isChecked = false
        play_float_window_box.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    //指定方位
                    IDYPlayerController.showPlayWidow(this@NewMainActivity, FloatWindowLocation.LEFT_BOTTOM, 20, false)
                    //指定具体坐标
//                    IDYPlayerController.showPlayWidow(this@MainActivity,10,0,0,false)
                } else {
                    IDYPlayerController.hidePlayWidow()
                }
            }
        })


        show_audio_detail_push_box.isChecked = false
        show_audio_detail_push_box.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                IdaddySdk.setDetailChapterListPushToDevice(true)
                show_audio_detail_play_or_push.text = "显示声音列表试听按钮"
            }else{
                IdaddySdk.setDetailChapterListPushToDevice(false)
                show_audio_detail_play_or_push.text = "显示声音列表推送按钮"
            }
        }

        show_audio_detail_play_or_push_box.isChecked = false
        show_audio_detail_play_or_push_box.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                IdaddySdk.setDetailChapterPushOrPlay(true)
            }else{
                IdaddySdk.setDetailChapterPushOrPlay(false)
            }
        }


        getAudiolists.setOnClickListener {
            startActivity(Intent(this@NewMainActivity, AudioRankTypeActivity::class.java))
        }
        auth_create_btn.setOnClickListener {
            startActivity(Intent(this@NewMainActivity, AuthCreateActivity::class.java).putExtra("redeemCode", auth_create_edittext.text.toString()))
        }
        get_topic_btn.setOnClickListener {
            startActivity(Intent(this@NewMainActivity, TopicAudioListActivity::class.java).putExtra("topicId", get_topic_edittext.text.toString()))
        }

        audio_info_btn.setOnClickListener {
            startActivity(Intent(this@NewMainActivity, AudioInfoActivity::class.java).putExtra("audioId",audio_info_id.text.toString()).putExtra("chapterId",audio_info_chapter_id.text.toString()))
        }

        initLayoutTitle(change_env_cl, change_env_detail_cl, change_env_arrow)
        initLayoutTitle(accountinfo_cl, accountInfo_detail_cl, accountInfo_arrow)
        initLayoutTitle(audiodetailInfo_cl, audioDetail_info_cl, audiodetail_arrow)
        initLayoutTitle(payandorder_cl, payandorder_info_cl, payandorder_arrow)
        initLayoutTitle(extra_cl, extra_info_cl, extra_arrow)
    }

    private fun initLayoutTitle(root: View, hideView: View, arrow: ImageView) {
        root.setOnClickListener {
            if (hideView.visibility == View.GONE) {
                hideView.visibility = View.VISIBLE
                RotateUtils.rotateArrow(arrow, false)
            } else {
                hideView.visibility = View.GONE
                RotateUtils.rotateArrow(arrow, true)
            }
        }
    }

    private fun changeEnviment() {
        if (IDYConfig.debugMode) {
            current_env.text = "wt2"
        } else {
            current_env.text = "api"
        }

        var selectItem = 2
        change_env.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("环境切换")
            val items = arrayOf("dev", "wt1", "wt2", "正式")

            builder.setSingleChoiceItems(items, selectItem) { dialog, which ->
                selectItem = which
                current_env.text = "" + items[selectItem]
            }

            builder.setPositiveButton("确定") { dialog, which ->
                run {
                    when (selectItem) {
                        0 -> {
                            IDYConfig.BASE_URL = "https://dev-open.idaddy.cn/inside/api/v1/inner"
                            IDYConfig.BASE_OUT_URL = "https://dev-open.idaddy.cn/inside/api/v1/outer"
                            IDYConfig.BASE_WEB_URL = "https://dev-open.idaddy.cn"
                            IDYConfig.TRACE_URL = "http://dev-tr.idaddy.cn/trace/addWX/"
                            TestConfig.IDY_PAY_URL = "https://dev-open.idaddy.cn/inside/api/v1/callback/pay/simulate"
                            IDYCommon.resetAuthorization()
                            IdaddySdk.updateAppKey("7d88bb0df50d0fbdc95feea2e2ee426f6961792e")
                        }

                        1 -> {
                            IDYConfig.BASE_URL = "https://wt1-open.idaddy.cn/inside/api/v1/inner"
                            IDYConfig.BASE_OUT_URL = "https://wt1-open.idaddy.cn/inside/api/v1/outer"
                            IDYConfig.BASE_WEB_URL = "https://wt1-open.idaddy.cn"
                            IDYConfig.TRACE_URL = "https://wt1-tr.idaddy.cn/trace/addWX/"
                            TestConfig.IDY_PAY_URL = "https://wt1-open.idaddy.cn/inside/api/v1/callback/pay/simulate"
                            IDYCommon.resetAuthorization()
                            IdaddySdk.updateAppKey("5d653deaf6c5a01e4dd1133eef232070d3e41b95")
                        }
                        2 -> {
                            IDYConfig.BASE_URL = "https://wt2-open.idaddy.cn/inside/api/v1/inner"
                            IDYConfig.BASE_OUT_URL = "https://wt2-open.idaddy.cn/inside/api/v1/outer"
                            IDYConfig.BASE_WEB_URL = "https://wt2-open.idaddy.cn"
                            IDYConfig.TRACE_URL = "http://wt2-tr.idaddy.cn/trace/addWX/"
                            TestConfig.IDY_PAY_URL = "https://wt2-open.idaddy.cn/inside/api/v1/callback/pay/simulate"
                            IDYCommon.resetAuthorization()
                            IdaddySdk.updateAppKey("7d88bb0df50d0fbdc95feea2e2ee426f6961792e")
                        }
                        3 -> {
                            IDYConfig.BASE_URL = "https://open.idaddy.cn/inside/api/v1/inner"
                            IDYConfig.BASE_OUT_URL = "https://open.idaddy.cn/inside/api/v1/outer"
                            IDYConfig.BASE_WEB_URL = "https://open.idaddy.cn"
                            IDYConfig.TRACE_URL = "https://tr.idaddy.cn/trace/addWX/"
                            TestConfig.IDY_PAY_URL = "https://open.idaddy.cn/inside/api/v1/callback/pay/simulate"
                            IDYCommon.resetAuthorization()
                            IdaddySdk.updateAppKey("5d653deaf6c5a01e4dd1133eef232070d3e41b95")
                        }
                    }
                    IDYConfig.IDADDY_SDK_MAIN_PAGE = "${IDYConfig.BASE_WEB_URL}/h5/inside/version/home20181206.html"
                }
            }
            builder.setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
            builder.show()
        }
    }

    private fun refreshAccount() {
        IDYConfig.userInfoBean.data?.let {
            user_id.text = "口袋故事用户 " + it.user_id.toString()

            if (it.is_vip) {
                is_vip_tv.text = "已开通"
            } else {
                is_vip_tv.text = "未开通"
            }
        }
    }

    override fun onReceiveUpdateUserInfoCommond(command: Int, intent: Intent) {
        when (intent.getIntExtra(ACTION_USERINFO_COMMAND, -1)) {
            USER_LOGIN_SUCCESS // 登录成功
            -> {
                refreshAccount()
            }

            USER_LOGIN_FAILD// 登录失败
            -> {
                refreshAccount()
            }

            IDYUpdateUserInfoReceiver.USER_BUY_VIP_SUCCESS //用户购买Vip成功
            -> {
                refreshAccount()
            }
        }
    }

    private fun registerUserInfoReceiver() {
        updateUserInfoReceiver = IDYUpdateUserInfoReceiver(this)
        userInfointentFilter = IntentFilter()
        userInfointentFilter.addAction(ACTION_USERINFO_COMMAND)
        BroadCaseManager.registerReceiver(updateUserInfoReceiver, userInfointentFilter)
    }

    private fun unregisterReceiver() {
        updateUserInfoReceiver = IDYUpdateUserInfoReceiver(this)
        userInfointentFilter = IntentFilter()
        userInfointentFilter.addAction(ACTION_USERINFO_COMMAND)
        BroadCaseManager.registerReceiver(updateUserInfoReceiver, userInfointentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            BroadCaseManager.unRegisterReceiver(updateUserInfoReceiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }
}