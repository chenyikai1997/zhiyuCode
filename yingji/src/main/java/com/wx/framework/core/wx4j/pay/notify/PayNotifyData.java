package com.wx.framework.core.wx4j.pay.notify;

public class PayNotifyData
{
  private String return_code;
  private String return_msg;
  private String appid;
  private String mch_id;
  private String device_info;
  private String nonce_str;
  private String sign;
  private String result_code;
  private String err_code;
  private String err_code_des;
  private String openid;
  private String is_subscribe;
  private String trade_type;
  private String bank_type;
  private String total_fee;
  private String fee_type;
  private String cash_fee;
  private String cash_fee_type;
  private String coupon_fee;
  private String coupon_count;
  private String coupon_id_$n;
  private String coupon_fee_$n;
  private String transaction_id;
  private String out_trade_no;
  private String attach;
  private String time_end;

  public String getReturn_code()
  {
    return this.return_code;
  }

  public void setReturn_code(String return_code) {
    this.return_code = return_code;
  }

  public String getReturn_msg() {
    return this.return_msg;
  }

  public void setReturn_msg(String return_msg) {
    this.return_msg = return_msg;
  }

  public String getAppid() {
    return this.appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  public String getMch_id() {
    return this.mch_id;
  }

  public void setMch_id(String mch_id) {
    this.mch_id = mch_id;
  }

  public String getDevice_info() {
    return this.device_info;
  }

  public void setDevice_info(String device_info) {
    this.device_info = device_info;
  }

  public String getNonce_str() {
    return this.nonce_str;
  }

  public void setNonce_str(String nonce_str) {
    this.nonce_str = nonce_str;
  }

  public String getSign() {
    return this.sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getResult_code() {
    return this.result_code;
  }

  public void setResult_code(String result_code) {
    this.result_code = result_code;
  }

  public String getErr_code() {
    return this.err_code;
  }

  public void setErr_code(String err_code) {
    this.err_code = err_code;
  }

  public String getErr_code_des() {
    return this.err_code_des;
  }

  public void setErr_code_des(String err_code_des) {
    this.err_code_des = err_code_des;
  }

  public String getOpenid() {
    return this.openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public String getIs_subscribe() {
    return this.is_subscribe;
  }

  public void setIs_subscribe(String is_subscribe) {
    this.is_subscribe = is_subscribe;
  }

  public String getTrade_type() {
    return this.trade_type;
  }

  public void setTrade_type(String trade_type) {
    this.trade_type = trade_type;
  }

  public String getBank_type() {
    return this.bank_type;
  }

  public void setBank_type(String bank_type) {
    this.bank_type = bank_type;
  }

  public String getTotal_fee() {
    return this.total_fee;
  }

  public void setTotal_fee(String total_fee) {
    this.total_fee = total_fee;
  }

  public String getFee_type() {
    return this.fee_type;
  }

  public void setFee_type(String fee_type) {
    this.fee_type = fee_type;
  }

  public String getCash_fee() {
    return this.cash_fee;
  }

  public void setCash_fee(String cash_fee) {
    this.cash_fee = cash_fee;
  }

  public String getCash_fee_type() {
    return this.cash_fee_type;
  }

  public void setCash_fee_type(String cash_fee_type) {
    this.cash_fee_type = cash_fee_type;
  }

  public String getCoupon_fee() {
    return this.coupon_fee;
  }

  public void setCoupon_fee(String coupon_fee) {
    this.coupon_fee = coupon_fee;
  }

  public String getCoupon_count() {
    return this.coupon_count;
  }

  public void setCoupon_count(String coupon_count) {
    this.coupon_count = coupon_count;
  }

  public String getCoupon_id_$n() {
    return this.coupon_id_$n;
  }

  public void setCoupon_id_$n(String coupon_id_$n) {
    this.coupon_id_$n = coupon_id_$n;
  }

  public String getCoupon_fee_$n() {
    return this.coupon_fee_$n;
  }

  public void setCoupon_fee_$n(String coupon_fee_$n) {
    this.coupon_fee_$n = coupon_fee_$n;
  }

  public String getTransaction_id() {
    return this.transaction_id;
  }

  public void setTransaction_id(String transaction_id) {
    this.transaction_id = transaction_id;
  }

  public String getOut_trade_no() {
    return this.out_trade_no;
  }

  public void setOut_trade_no(String out_trade_no) {
    this.out_trade_no = out_trade_no;
  }

  public String getAttach() {
    return this.attach;
  }

  public void setAttach(String attach) {
    this.attach = attach;
  }

  public String getTime_end() {
    return this.time_end;
  }

  public void setTime_end(String time_end) {
    this.time_end = time_end;
  }

  public String toString()
  {
    return "PayNotifyData{return_code='" + this.return_code + '\'' + ", return_msg='" + this.return_msg + '\'' + ", appid='" + this.appid + '\'' + ", mch_id='" + this.mch_id + '\'' + ", device_info='" + this.device_info + '\'' + ", nonce_str='" + this.nonce_str + '\'' + ", sign='" + this.sign + '\'' + ", result_code='" + this.result_code + '\'' + ", err_code='" + this.err_code + '\'' + ", err_code_des='" + this.err_code_des + '\'' + ", openid='" + this.openid + '\'' + ", is_subscribe='" + this.is_subscribe + '\'' + ", trade_type='" + this.trade_type + '\'' + ", bank_type='" + this.bank_type + '\'' + ", total_fee='" + this.total_fee + '\'' + ", fee_type='" + this.fee_type + '\'' + ", cash_fee='" + this.cash_fee + '\'' + ", cash_fee_type='" + this.cash_fee_type + '\'' + ", coupon_fee='" + this.coupon_fee + '\'' + ", coupon_count='" + this.coupon_count + '\'' + ", coupon_id_$n='" + this.coupon_id_$n + '\'' + ", coupon_fee_$n='" + this.coupon_fee_$n + '\'' + ", transaction_id='" + this.transaction_id + '\'' + ", out_trade_no='" + this.out_trade_no + '\'' + ", attach='" + this.attach + '\'' + ", time_end='" + this.time_end + '\'' + '}';
  }
}