<?php
namespace Home\Controller;
use Think\Controller;
use Home\Event\JsonEvent;
use Home\Model\FriendsModel;
class UserController extends Controller {
    
    // 判断给定userid是否登录
    public function islegal($userid){
       if($_SESSION[$userid]==1) return TRUE ;
        else return FALSE;        
    }
    
    // 登录
    public function login($username, $pwd) {
        $userid = D('user')->getUserId($username);
        $SESSION[$userid] = 1;
        $result = D('user')->userLogin($username, $pwd);
        $json = (new JsonEvent())->encapsulate($result, "userInfo");
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    // 退出登录
    public function logout($userid){
        if($this->islegal($userid)){
            session($userid,null);
            $json['returnCode'] = 1;
            $json['msg'] = "success";
        }else{
            $json['returnCode'] = 0;
            $json['msg'] = "fail";
        }
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);  
    }
    
    // 注册
    public function register($username,$password,$quetionOne,$answerOne,$quetionTwo,$answerTwo) {
        $result = D('user')->userSignup($username,$password,$quetionOne,$answerOne,$quetionTwo,$answerTwo);
        switch ($result) {
        case -1:
            $json['returnCode'] = 0;
            $json['msg'] = "用户名已存在！";
            break;
        case 0:
            $json['returnCode'] = 0;
            $json['msg'] = "系统错误，注册失败！";
            break;
        default:
            $json['returnCode'] = 1;
            $json['msg'] = "success";
            $json['userid'] = $result;
            break;
        }
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    // 获取好友列表
    public function getFriends($userid){
        $re1=D('friends')->getUserFriends1($userid);// 获取到userid的多条freid
        $re2=D('friends')->getUserFriends2($userid);
        
        $result=  array_merge($re1,$re2);
        $json = (new JsonEvent())->encapsulate($result, "friends");
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    public function getShare($userid) {
       
    }
    
}