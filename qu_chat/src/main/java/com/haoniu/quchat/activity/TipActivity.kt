package com.haoniu.quchat.activity

import android.os.Bundle
import com.aite.chat.R
import com.haoniu.quchat.base.BaseActivity
import com.haoniu.quchat.entity.EventCenter
import com.haoniu.quchat.http.ApiClient
import com.haoniu.quchat.http.AppConfig
import com.haoniu.quchat.http.ResultListener
import com.zds.base.json.FastJsonUtil
import kotlinx.android.synthetic.main.activity_tips.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 *@创建者 Mr.zou
 *@创建时间 2020/2/25
 *@描述
 */
class TipActivity : BaseActivity() {

    override fun onEventComing(center: EventCenter<*>?) {
    }

    override fun initContentView(bundle: Bundle?) {
        setContentView(R.layout.activity_tips)
    }

    override fun getBundleExtras(extras: Bundle?) {
    }

    override fun initLogic() {
        confirmMoney()
    }

    private fun confirmMoney() {
        toolbar_title.text = "艾特官方"
        ApiClient.requestNetHandle(this
                , AppConfig.TIP
                , "请稍后..."
                , null
                , object : ResultListener() {
            override fun onSuccess(json: String?, msg: String?) {
                val content = FastJsonUtil.getString(json, "reminder")
                tv_content.text = if (content.isNullOrEmpty()) "" else content
            }

            override fun onFailure(msg: String?) {
            }
        })
    }

}