package com.haoniu.quchat.activity

import android.os.Bundle
import com.aite.chat.R
import com.haoniu.quchat.base.BaseActivity
import com.haoniu.quchat.entity.BankDto
import com.haoniu.quchat.entity.EventCenter
import com.haoniu.quchat.http.ApiClient
import com.haoniu.quchat.http.AppConfig
import com.haoniu.quchat.http.ResultListener
import kotlinx.android.synthetic.main.activity_check_bank.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.greenrobot.eventbus.EventBus

/**
 *@创建者 MR.zou
 *@创建时间 2020/5/5
 *@描述
 */

class CheakBankActivity : BaseActivity() {

    private var bankBean: BankDto? = null

    override fun initLogic() {
        toolbar_title.text = "验证码"
        tv_new_bank_card_submit.setOnClickListener {
            if (et_code.text.toString().isEmpty()) {
                toast("请输入验证码")
                return@setOnClickListener
            }
            addCode()
        }
    }

    private fun addCode() {
        bankBean?.apply {
            val map = mapOf(
                    "tokenId" to tokenId
                    , "verifyCode" to et_code.text.toString()
                    , "realName" to realName
                    , "idCard" to idCard
                    , "bankCard" to bankCard
                    , "bankName" to bankName
                    , "bankPhone" to bankPhone
            )
            ApiClient.requestNetHandle(this@CheakBankActivity,AppConfig.check_bank
                    ,"请稍后...",map,object :ResultListener(){
                override fun onSuccess(json: String?, msg: String?) {
                    toast("绑定成功")
                    EventBus.getDefault().post(EventCenter<Int>(1011))
                }

                override fun onFailure(msg: String?) {
                   toast(msg)
                }
            })
        }
    }

    override fun onEventComing(center: EventCenter<*>?) {
    }

    override fun initContentView(bundle: Bundle?) {
        setContentView(R.layout.activity_check_bank)
    }

    override fun getBundleExtras(extras: Bundle?) {
        bankBean = extras?.getSerializable("bean") as BankDto
    }

}