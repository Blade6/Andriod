<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Home\Model;
use Think\Model;

class FriendsModel extends Model{

    public function getUserFriends1($userid, $para){
       $friends=M('friends');
       $data["userid"] = $userid;
       $result = $friends->field($para)->where($data)->select();
       return $result;
    }
    
    public function getUserFriends2($userid, $para){
       $friends=M('friends');  
       $data["freid"] = $userid;
       $result = $friends->field($para)->where($data)->select();
       return $result;
    }
    
    public function getFriends($userid,$para1,$para2) {
        $re1=$this->getUserFriends1($userid,$para1);// 获取到userid的多条freid
        $re2=$this->getUserFriends2($userid,$para2);
        
        $result = array_merge($re1,$re2);
        return $result;
    }

    public function findFriends1($userid,$friendId){
        $friends=M('friends');
        $result=$friends->query("select * from `friends` where userid='$userid'and freid='$friendId' ");
        return $result;
    }

    public function findFriends2($userid,$friendId){
        $friends=M('friends');
        $result=$friends->query("select * from `friends` where userid='$friendId'and freid='$userid' ");
        return $result;
    }

    public function addFriends($userid,$username,$friendId,$frename){
       $friends=M('friends');
       $re = $friends->field('id')->order('id desc')->limit(1)->find();
       $data['id'] = (int)$re['id'] + 1;
       
       $data['userid']=$userid;
       $data['username']=$username;
       $data['freid']=$friendId; 
       $data['frename']=$frename;
       $result=$friends->data($data)->add();
       return $result;
    }
}