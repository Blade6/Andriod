<?php
namespace Home\Controller;
use Think\Controller;
use Home\Event\JsonEvent;
class UserController extends Controller {
    
    public function login($userid, $pwd) {
        $result = D('user')->userLogin($userid, $pwd);
        $json = (new JsonEvent())->encapsulate($result, "userInfo");
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    public function test() {
        $user = D('user');
        $result = $user->getInsertID();
        echo str_pad($result, 6, "0", STR_PAD_LEFT);
       // var_dump($result);
    }
    
    public function signup($username, $pwd) {
        $result = D('user')->userSignup($username, $pwd);
        if ($result) {
            $json['returnCode'] = 1;
            $json['msg'] = "success";
        }
        else {
            $json['returnCode'] = 0;
            $json['msg'] = "fail";
        }
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
}