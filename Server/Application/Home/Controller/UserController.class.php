<?php
namespace Home\Controller;
use Think\Controller;
use Home\Event\JsonEvent;
class UserController extends Controller {
    
    public function login($username, $pwd) {
        $result = D('user')->userLogin($username, $pwd);
        $json = (new JsonEvent())->encapsulate($result, "userInfo");
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    public function signup($username, $pwd) {
        $result = D('user')->userSignup($username, $pwd);
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
    
}