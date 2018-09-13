package com.runer.toumai.dao;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunnerParam;
import com.runer.toumai.util.ParamUtil;

import java.io.IOException;
/**
 * Created by szhua on 2017/7/31/031.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * LoginDao
 * 登录网络请求
 */
public class LoginDao extends RunerBaseRequest {

    String userId ;
    String sign ;
    String info ;

    public String getInfo() {
        return info;
    }

    public String getSign(){
        return sign;
    }
    public String getUserId() {
        return userId;
    }
    public LoginDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.LOGIN){
            userId =result.findValue("user_id").asText();
        }else if(requestCode==RequestCode.THIRD_LOGIN){
            userId =result.findValue("user_id").asText();
        }else if(requestCode==RequestCode.ALIOpen){
            sign =result.findValue("sign").asText();
            info =result.findValue("str").asText() ;
        }else if (requestCode==RequestCode.DELETE_OPEN){

        }else if (requestCode==RequestCode.reg){
            userId =result.findValue("user_id").asText() ;
        }else  if (requestCode==RequestCode.eidtEmail){

        }else if (requestCode==RequestCode.bind_email){

        }else if (requestCode==RequestCode.emailReg){

        }else  if (requestCode==RequestCode.editPass){

        }

    }
    /*mobile	是	string	手机号
code	是	string	验证码
name	否	string	昵称*/
    /*登录*/
    public void login(String mobile ,String code ,String name  ){
        ArrayMap<String,String> params =new ArrayMap<>() ;
        params.put("mobile",mobile);
        params.put("code",code);
        params.put("name",name);
        request(NetInter.USER_LOGIN,params, RequestCode.LOGIN);
    }
    //获取验证码
    public void getCode(String mobile,String type ){
        ArrayMap<String,String> params =new ArrayMap<>() ;
        params.put("mobile",mobile);
        params.put("type",type);
        params.put("length","6");
        request(NetInter.GET_CODE,params, RequestCode.SEND_CODE);
    }
    /**第三方登录*/
    public void openLogin(String openid ,String nickname,String type ,String user_id){
        RunnerParam param =new RunnerParam() ;
        param.put("openid",openid);
        param.put("nickname",nickname);
        param.put("type",type) ;
        param.put("user_id",user_id);
        request(NetInter.openLogin,param,RequestCode.THIRD_LOGIN);
    }

    /**第三方登录*/
    public void openLogin(String openid ,String nickname,String type ,String user_id ,String opid2){
        RunnerParam param =new RunnerParam() ;
        param.put("openid",openid);
        param.put("nickname",nickname);
        param.put("type",type) ;
        param.put("openid2",opid2);
        param.put("user_id",user_id);
        request(NetInter.openLogin,param,RequestCode.THIRD_LOGIN);
    }

    /*id	是	int	用户id
mobile	是	string	手机号*/
    public void bindPhone(String id ,String mobile ,String code){
        RunnerParam param =new RunnerParam() ;
        param.put("id",id);
        param.put("mobile",mobile);
        param.put("code",code);
        request(NetInter.bindPhone,param,RequestCode.BIND_PHONE);
    }
    /*mobile	是	string	手机号
type	是	string	类型：login登录，
code	是	string	验证码*/
    public void validPhone(String mobile, String type ,String code){
        RunnerParam param =new RunnerParam() ;
        param.put("mobile",mobile) ;
        param.put("type",type);
        param.put("code",code);
        request(NetInter.valid,param,RequestCode.VALID);
    }

    /**
     *
     * @param user_id
     * @param type wechat weibo ali qq
     */
    public void deleteOpen(String user_id ,String type ){
        RunnerParam param =new RunnerParam() ;
        param.put("user_id",user_id) ;
        param.put("type",type);
        request(NetInter.deleteOpen,param,RequestCode.DELETE_OPEN);
    }


    public void aLiOpen(){
        RunnerParam param =new RunnerParam() ;
        request(NetInter.aliOpen,param,RequestCode.ALIOpen);
    }

    /**['user_name'=>$_POST['user_name'],'code'=>$_POST['code'],'email'=>$_POST['email'],'pwd'=>$_POST['pwd']]
     * 用户注册；
     */
    public void userRegister(String user_name ,String code ,String email ,String pwd ){
        RunnerParam params = ParamUtil.generateRunparam(param -> {
            param.put("user_name", user_name);
            param.put("code", code);
            param.put("email", email);
            param.put("pwd", pwd);
        });
        request(NetInter.reg,params,RequestCode.reg);
    }


    /**
     * @param email
     * @param code_type register
     *                  获得验证码
     *                  edit
     *                  编辑
     */
    public void getEmalCode(String email ,String code_type){
        RunnerParam params =ParamUtil.generateRunparam(param -> {
            param.put("email",email);
            param.put("code_type",code_type) ;
        });
        request(NetInter.email_code_add,params,RequestCode.email_code_add);
    }

    /**
     * 用户邮箱密码的登录
     */
    public void emailLogin(String email ,String pwd ){
        RunnerParam params =ParamUtil.generateRunparam(param->{
           param.put("email",email) ;
           param.put("pwd",pwd) ;
        });
        request(NetInter.userLogin1,params ,RequestCode.user_login1);
    }


    /**
     * $res = $datac->result('user', 'edit_email', ['id'=>$_user['id'],'email'=>trim($_POST['email'],''),'code'=>trim($_POST['code'],'')]);
     */
    /**
     * 编辑用呼的邮箱；
     * @param id
     * @param email
     * @param code
     */
    public void eidtEmail(String id ,String email ,String code ){
        RunnerParam params =ParamUtil.generateRunparam(param->{
            param.put("id",id) ;
            param.put("email",email);
            param.put("code",code) ;
        });
        request(NetInter.eidtEmail,params,RequestCode.eidtEmail);
    }


    /**
     * $res = $datac->result('user', 'bind_email',['id'=>$_user['id'],'code'=>$_POST['code'],'email'=>$_POST['email'],'pwd'=>$_POST['pwd']]);
     绑定用户的邮箱
     */
    public void bindEmail(String id ,String code ,String email ,String pwd ){
        RunnerParam params =ParamUtil.generateRunparam(param->{
            param.put("id",id) ;
            param.put("code",code) ;
            param.put("email",email);
            param.put("pwd",pwd) ;
        });
        request(NetInter.bindEmail,params,RequestCode.bind_email);
    }


    /**
     * $res = $datac->result('user', 'email_yanzheng', ['email'=>trim($_POST['email'],''),'code'=>trim($_POST['code'],'')]);
     * 邮箱的验证
     */
    public void  emailReg(String email, String code ){
        RunnerParam params =ParamUtil.generateRunparam(param->{
           param.put("email",email);
           param.put("code",code) ;
        });
        request(NetInter.emailReg,params,RequestCode.emailReg);
    }


    /**
     * 修改用户的密码
     * $res = $datac->result('user', 'edit_pwd', ['id'=>$_user['id'],'pwd'=>trim($_POST['pwd'],'')]);
     */
    public void  editPass(String pwd ,String id ){
        RunnerParam params = ParamUtil.generateRunparam(param->{
           param.put("pwd",pwd) ;
           param.put("id",id) ;
        });
        request(NetInter.editPass,params,RequestCode.editPass);


    }




}
