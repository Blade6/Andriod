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
    
    public function userSignup($username,$password,$quetionOne,$answerOne,$quetionTwo,$answerTwo) {
        // 先检验用户名是否存在
        $user=M('user');
        $data['username']=$username;
        if($user->where($data)->find()) return -1;
        
        $this->insertID+=1;
        $userid=  str_pad($this->insertID, 6, '0', STR_PAD_LEFT);
        $data['userid']=$userid;
        $data['passward']=$password;
        $data['Q1']=$quetionOne;
        $data['A1']=$answerOne;
        $data['Q2']=$quetionTwo;
        $data['A2']=$answerTwo;
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
    
    public function getUserId($username) {
        $user = M('user');
        $condition['username'] = $username;
        $result = $user->where($condition)->find();
        return $result["userid"];
    }
    
}
