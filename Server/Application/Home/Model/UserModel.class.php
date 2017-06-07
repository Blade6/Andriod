<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of UserModel
 *
 * @author jianhong
 */
namespace Home\Model;
use Think\Model;
class UserModel extends Model {
    
    // 插入的id
    private $insertID;
    
    public function __construct() {
        $user = M('user');
        $result = $user->field('id')->order('id desc')->find();
        $this->insertID = (int)$result["id"];
    }
    
    public function getinsertID() {
        return $this->insertID;
    }
    
    public function userSignup($username,$password,$questionOne,$answerOne,$questionTwo,$answerTwo) {
        // 先检验用户名是否存在
        $user=M('user');
        $data['username']=$username;
        if($user->where($data)->find()) return -1;
        
        $this->insertID+=1;
        $userid=  str_pad($this->insertID, 6, '0', STR_PAD_LEFT);
        $data['id'] = $this->insertID;
        $data['userid']=$userid;
        $data['password']=$password;
        $data['Q1']=$questionOne;
        $data['A1']=$answerOne;
        $data['Q2']=$questionTwo;
        $data['A2']=$answerTwo;
        $data['pic'] = '/wechat/Public/Users/default.png';
        $result=$user->data($data)->add();
        if($result) return $userid;
        else return 0;
    }
    
    public function userLogin($username, $pwd) {
        $user = M('user');
        $data['username'] = $username;
        $data['password'] = $pwd;
        $result = $user->where($data)->find();
        return $result;
    }
    
    // 根据username返回相应的userid
    public function getUserId($username) {
        $user = M('user');
        $condition['username'] = $username;
        $result = $user->where($condition)->find();
        return $result["userid"];
    }
    
    // 根据userid返回相应的username
    public function getUserName($userid) {
        $data["userid"] = $userid;
        $re=M('user')->where($data)->find();
        return $re["username"];
    }
    
    // 判断用户名是否已经存在
    public function IsUserExist($username) {
        $data["username"] = $username;
        $re = M('user')->where($data)->find();
        if ($re) return true;
        else return false;
    }
    
    // 根据userid返回用户头像
    public function getUserPic($userid) {
        $data["userid"] = $userid;
        $re = M('user')->where($data)->find();
        return $re["pic"];
    }
    
    public function changePwd($userid,$newpwd){
        $user = M('user');
        $data['password'] = $newpwd;
        $result = $user->where("userid='$userid'")->save($data);
        return $result;
    }
    
    // 修改用户名
    public function changeUserInfo($userid,$username){
        $user=M('user');
        $data['userid']=$userid;
        $data['username']=$username;
        $result=$user->where($data)->save();
        return $result;
    }
    
    // 获取用户信息
    public function findUser($userid){
        $user=M('user');
        $data['userid']=$userid;
        $result=$user->where($data)->find();
        return $result;
    }
    
}
