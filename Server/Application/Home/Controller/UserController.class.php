<?php
namespace Home\Controller;
use Think\Controller;
use Home\Event\JsonEvent;
use Home\Model\FriendsModel;
use Home\Model\MomentModel;
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
        $result = D('friends')->getFriends($userid,"frename","username");
        $json = (new JsonEvent())->encapsulate($result, "friends");
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    // 搜索账号接口
    public function search($userid,$searchname){
        $result=D('user')->getUserId($searchname);
        $json = (new JsonEvent())->encapsulate($result, "searchinfo");
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    // 添加好友
    public function addFriend($userid,$friendid){
        $find1=(new FriendsModel())->findFriends1($userid, $friendid);
        $find2=(new FriendsModel())->findFriends2($userid, $friendid);
        if($find1||$find2){
            $json['returnCode'] = 0;
            $json['msg'] = "fail";
        }else {
            $username = D('user')->getUserName($userid);
            $frename = D('user')->getUserName($friendid);
            $result=D('friends')->addFriends($userid,$username,$friendid,$frename);
            $json['returnCode'] = 1;
            $json['msg'] = "success";
        }
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    // 获取动态
    public function getShare($userid) {
        $moments = D('moment')->getMoments($userid);
        $json = (new JsonEvent())->encapsulate($moments, "shareInfo");
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    // 发朋友圈
    public function share($userid,$words){
        $username = D('user')->getUserName($userid);
        $result=D('moment')->shareMoment($username,$words,$image);
        if($result){
            $json['returnCode'] = 1;
            $json['msg'] = "success";
        }  else {
            $json['returnCode'] = 0;
            $json['msg'] = "fail";
        }
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    // 修改密码
    public function changeUserpwd($userid,$oldpwd,$newpwd){
        $pwd=D('user')->userPwd($userid);
        if($pwd==$oldpwd){
            $result=D('user')->changePwd($userid, $newpwd);
            if ($result) {
                $json['returnCode'] = 1;
                $json['msg'] = "success";
            } else {
                $json['returnCode'] = 0;
                $json['msg'] = "fail";
            }
        } else {
            $json['returnCode'] = 0;
            $json['msg'] = "fail";
        }
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE); 
    }
    
    /**
     * 获取个人信息
     */
    public function getUserMsg($userid){
        $result = D('user') ->findUser($userid);
        if($result){
            $data['id'] = $result['id'];
            $data['userid'] = $result['userid'];
            $data['username'] = $result['username'];
            $data['pic'] = $result['pic'];
            $json = (new JsonEvent())->encapsulate($data, "userInfo");
            header('Content-type:text/json');
            echo json_encode($json,JSON_UNESCAPED_UNICODE);
        }
    }

    /**
     * 修改个人信息
     */
//    public function changeUserMsg($userid,$username){
//        if(!D('user')->isUserExist($username)) {
//            $result1 = D('user') ->changeUserInfo($userid,$username);
//            if($result1){
//                $result2 = D('user') ->findUser($userid);
//                $data['id'] = $result2['id'];
//                $data['userid'] = $result2['userid'];
//                $data['username'] = $result2['username'];
//                $data['pic'] = $result2['pic'];
//                $json = (new JsonEvent())->encapsulate($data, "userInfo");
//            }else{
//                $json['returnCode'] = 0;
//                $json['msg'] = "change fail";
//            }
//        } else {
//            $json['returnCode'] = 0;
//            $json['msg'] = "username exist";
//        }
//        header('Content-type:text/json');
//        echo json_encode($json,JSON_UNESCAPED_UNICODE);
//    }
    
    /**
     * 获取用户相册
     */
    public function getUserShare($userid){
        $username = D('user')->getUserName($userid);
        $result = D('moment') ->findMoment($username);
        if($result){
            $json = (new JsonEvent())->encapsulate($result, "userMoment");
        } else {
            $json['returnCode'] = 0;
            $json['msg'] = "fail";
        }
        $json["pic"] = D('user')->getUserPic($userid);
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
}