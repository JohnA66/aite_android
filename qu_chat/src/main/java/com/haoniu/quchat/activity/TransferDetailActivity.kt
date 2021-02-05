package com.haoniu.quchat.activity

import android.os.Bundle
import com.aite.chat.R
import com.haoniu.quchat.base.BaseActivity
import com.haoniu.quchat.entity.EventCenter
import com.haoniu.quchat.http.ApiClient
import com.haoniu.quchat.http.AppConfig
import com.haoniu.quchat.http.ResultListener
import kotlinx.android.synthetic.main.activity_transfer_detail.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 *@创建者 Mr.zou
 *@创建时间 2020/2/25
 *@描述
 */
class TransferDetailActivity : BaseActivity() {

    private var status: String? = null
    private var money: String? = null
    private var turnId: String? = null
    private var isSelf: Boolean? = false

    override fun onEventComing(center: EventCenter<*>?) {
    }

    override fun initContentView(bundle: Bundle?) {
        setContentView(R.layout.activity_transfer_detail)
    }

    override fun getBundleExtras(extras: Bundle?) {
        status = extras?.getString("status", "")
        money = extras?.getString("money", "")
        turnId = extras?.getString("turnId", "")
        isSelf = extras?.getBoolean("isSelf", false)
    }

    override fun initLogic() {
        toolbar_title.text = "转账"
        tv_money.text = if (money.isNullOrEmpty()) "￥ 0.00" else "￥ $money"
        viewUI()
        tv_collection.setOnClickListener {
            confirmMoney()
        }
    }

    private fun viewUI() {
        tv_status.text = if (status == "0") "待确认收款" else "已确认收款"
        tv_collection.setBackgroundResource(if (status == "0" && isSelf ==
            false) R.drawable.group_search else R.drawable.group_search_gray)
        tv_collection.isEnabled = status == "0" && isSelf == false
        img_status.setImageResource(if (status != "0") R.drawable
                .yx else R.drawable.daiqueren)
    }

    private fun confirmMoney() {
        val map = mapOf("transferId" to turnId)
        ApiClient.requestNetHandle(this
                , AppConfig.CONFIRM
                , "请稍后..."
                , map
                , object : ResultListener() {
            override fun onSuccess(json: String?, msg: String?) {
                status = "1"

                viewUI()
            }

            override fun onFailure(msg: String?) {
                toast(msg)
            }
        })
    }

}