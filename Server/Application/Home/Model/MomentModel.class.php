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
        $data['userid'] = $userid;
        $result = $this->moment->where($data)->select();
    }
}
