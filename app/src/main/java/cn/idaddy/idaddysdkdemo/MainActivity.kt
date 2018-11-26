package cn.idaddy.idaddysdkdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import cn.idaddy.android.opensdk.lib.IDYCommon
import cn.idaddy.android.opensdk.lib.IDYConfig
import cn.idaddy.android.opensdk.lib.IdaddySdk
import cn.idaddy.android.opensdk.lib.utils.StringUtils
import cn.idaddy.android.opensdk.lib.utils.ToastUtils
import cn.idaddy.idaddysdkdemo.activitys.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var pushText = ""  //推送的文案
    var phoneNumber = ""  //手机号码
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<TextView>(R.id.prd_textView).text = "V"+BuildConfig.VERSION_NAME

        findViewById<View>(R.id.main_story_button).setOnClickListener {
            IdaddySdk.start()
        }
        findViewById<View>(R.id.main_story_detail_button).setOnClickListener {
            IdaddySdk.showAudioInfoPage(editText_uniqueid.text.toString())
        }

        findViewById<View>(R.id.main_user_logout_button).setOnClickListener {
            IdaddySdk.logout()
        }


        uniquedId_btn.setOnClickListener {
            TestConfig.uniquedId = editText.text.toString()
            IDYCommon.resetAuthorization()

            if (IDYConfig.useIdaddyAccountValidate) {
//                LoginActivity.start(this)
            } else {
                //静默登录
                IDYCommon.idyTokenInterface?.idyNeedAccessToken()
            }
        }

        findViewById<Button>(R.id.getOrderlistBtn).setOnClickListener{
            startActivity(Intent(this@MainActivity, OrderListActivity::class.java))
        }

        findViewById<Button>(R.id.getAudioRanklistBtn).setOnClickListener{
            startActivity(Intent(this@MainActivity, AudioRankTypeActivity::class.java))
        }

        findViewById<Button>(R.id.getPayParam_btn).setOnClickListener{
            startActivity(Intent(this@MainActivity, OrderPayParamActivity::class.java).putExtra("goodsId",editText_goodsId.text.toString()))
        }

        getTopicList_btn.setOnClickListener {
            startActivity(Intent(this@MainActivity, TopicAudioListActivity::class.java).putExtra("topicId",editText_topicId.text.toString()))
        }

        getAudioInfo_btn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AudioInfoActivity::class.java).putExtra("audioId",editText_audioId.text.toString()).putExtra("chapterId",editText_chapterId.text.toString()))
        }

        use_sdk_pay_box.isChecked = false
        use_sdk_pay_box.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked){
                    IdaddySdk.setUseIdaddyPay(true)
                }else{
                    IdaddySdk.setUseIdaddyPay(false)
                }
            }
        })

        push_to_device_box.isChecked = false
        push_to_device_btn.isEnabled = false

        push_to_device_btn.setTextColor(Color.GRAY)
        push_to_device_box.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked){
                    IdaddySdk.setPushAudioToDeviceButton(null,true)
                    push_to_device_btn.isEnabled = true
                    push_to_device_btn.setTextColor(Color.BLACK)
                }else{
                    IdaddySdk.setPushAudioToDeviceButton(null,false)
                    push_to_device_btn.isEnabled = false
                    push_to_device_btn.setTextColor(Color.GRAY)
                }
            }
        })

        push_to_device_btn.setOnClickListener{
            pushText = push_to_device_text.text.toString()
            if (pushText.isNullOrBlank()){
                IdaddySdk.setPushAudioToDeviceButton(null,true)
            }else{
                IdaddySdk.setPushAudioToDeviceButton(pushText,true)
            }
        }

        //工爸账号验证----------
        useIdaddyAccountValidate_box.isChecked = false
        edit_phoneNumber_btn.isEnabled = false

        edit_phoneNumber_btn.setTextColor(Color.GRAY)
        useIdaddyAccountValidate_box.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked){
                    IdaddySdk.setUseIdaddyAccountValidate(true)
                    edit_phoneNumber_btn.isEnabled = true
                    edit_phoneNumber_btn.setTextColor(Color.BLACK)
                }else{
                    IdaddySdk.setUseIdaddyAccountValidate(false)
                    edit_phoneNumber_btn.isEnabled = false
                    edit_phoneNumber_btn.setTextColor(Color.GRAY)
                }
            }
        })

        edit_phoneNumber_btn.setOnClickListener{
            phoneNumber = edit_phoneNumber.text.toString()
            if (StringUtils.isValidOfPhoneNumber(phoneNumber)){
                IdaddySdk.setUseIdaddyAccountValidate(true)
                IdaddySdk.setIdaddyAccountPhoneNumber(phoneNumber)
            }else{
                ToastUtils.showShort(this,"请输入正确的手机号")
            }
        }
        //工爸账号验证----------
    }
}
