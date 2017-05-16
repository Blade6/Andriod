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
    
    private $user;
    // 插入的id
    private $insertID;
    
    public function __construct() {
        $this->user = M('user');
        
        $result = $this->user->field('id')->order('id desc')->find();
        $this->insertID = (int)$result["id"];
    }
    
    public function getInsertID() {      
        return $this->insertID;
    }
    
    public function userLogin($userid, $pwd) {
        $data['userid'] = $userid;
        $data['password'] = $pwd;
        $result = $this->user->where($data)->find();
        return $result;
    }
    
    public function userSignup($username, $pwd) {
        $this->insertID += 1;
        $userid = str_pad($this->insertID, 6, "0", STR_PAD_LEFT);
        $data['userid'] = $userid;
        $data['username'] = $username;
        $data['password'] = $pwd;
        
        $this->user->create($data);
	if($this->user->add($data)) return 1;
	return 0;
    }
    
}
