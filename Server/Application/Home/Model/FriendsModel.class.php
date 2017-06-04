<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Home\Model;
use Think\Model;

class FriendsModel extends Model{
    //put your code here
    public function getUserFriends1($userid){
       $friends=M('friends');
//       $data['userid']=$userid;
       $result=$friends->query("select frename from `friends` where userid='$userid' ");
       return $result;
    }
    public function getUserFriends2($freid){
       $friends=M('friends');  
//       $data['freid']=$freid;
       $result=$friends->query("select username from `friends` where freid='$freid' ");
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
//    public function getUser($userid){
//        $friends=M('friends');
//        $result=$friends->query("select username from `friends` where userid='$userid' ");
//        return $result;
//    }

    public function addFriends($userid,$username,$friendId,$frename){
       $friends=M('friends'); 
       $data['userid']=$userid;
       $data['username']=$username;
       $data['freid']=$friendId; 
       $data['frename']=$frename;
       $result=$friends->data($data)->add();
       return $result;
    }
}