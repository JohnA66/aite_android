package com.haoniu.quchat.view.pay_pass_view;

//自定义接口
public interface OnPasswordInputFinish {
	//添加密码输入完成的接口
	void inputFinish(String pass);
	//取消支付接口
	void outfo();
	//忘记密码接口
	void forgetPwd();
}
