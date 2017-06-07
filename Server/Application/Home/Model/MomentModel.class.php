<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Home\Model;
use Think\Model;
/**
 * Description of MomentModel
 *
 * @author jianhong
 */
class MomentModel extends Model {
    private $moment;
    
    public function __construct() {
        $this->moment = M('moment');
    }
    
    public function getMoments($userid) {
        $friends1 = D('friends')->getUserFriends1($userid,'frename');
        $friends2 = D('friends')->getUserFriends2($userid,'username');
        
        $username = D('user')->getUserName($userid);
        $sql = "select * from `moment` where username= '$username'";
        
        foreach ($friends1 as $key => $value) {
            foreach ($value as $key1 => $value1) {
                $sql .= " or username="."'".$value['frename']."'";
            }
        }
        
        foreach ($friends2 as $key => $value) {
            foreach ($value as $key1 => $value1) {
                $sql .= " or username="."'".$value['username']."'";
            }
        }
        
        $sql_full = "select a.username,words,image,pic,time from (".$sql.") as a join user on a.username = user.username order by time desc";
        
        $moments = M('moment')->query($sql_full);
        
        return $moments;
    }

    public function shareMoment($username,$words,$image){
        $moments=M('moment');
        $re = $moments->field('id')->order('id desc')->limit(1)->find();
        $data['id'] = (int)$re['id'] + 1;
        
        $data['username']=$username;
        $data['words']=$words;
        //$data['image']=$image;
        $result = $this->moment->data($data)->add();
        return $result;
    }
}
