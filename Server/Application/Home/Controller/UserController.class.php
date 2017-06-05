<?php
namespace Home\Controller;
use Think\Controller;
use Home\Event\JsonEvent;
use Home\Model\FriendsModel;
class UserController extends Controller {
    
    // 登录
    public function login($username, $pwd) {
        $userid = D('user')->getUserId($username);
        $result = D('user')->userLogin($username, $pwd);
        $json = (new JsonEvent())->encapsulate($result, "userInfo");
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    // 退出登录
    public function logout($userid){
        $json['returnCode'] = 1;
        $json['msg'] = "success";
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);  
    }
    
    // 注册
    public function register($username,$password,$questionOne,$answerOne,$questionTwo,$answerTwo) {
        $result = D('user')->userSignup($username,$password,$questionOne,$answerOne,$questionTwo,$answerTwo);
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
    
    //搜索账号接口
    public function search($userid,$searchname){
        $result=D('user')->getUserId($searchname);
        $json = (new JsonEvent())->encapsulate($result, "searchinfo");
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    public function getShare($userid) {
       
    }
    
     //获取个人信息中的相册
    public function getUserShare($userid){
        $share=D('moment')->getMoments($userid);
        $json = (new JsonEvent())->encapsulate($share, "userShare");
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    
}