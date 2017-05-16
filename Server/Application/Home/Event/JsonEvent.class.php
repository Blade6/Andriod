<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
namespace Home\Event;
/**
 * Description of JsonEvent
 *
 * @author jianhong
 */
class JsonEvent {
   
    public function encapsulate($item, $datatype) {
        if (count($item) > 0) {
            $json['returnCode'] = 1;
            $json['msg'] = "success";
            $json['datatype'] = $datatype;
            $json['data'] = $item;
        }
        else {
            $json['returnCode'] = 0;
            $json['msg'] = "fail";
        }
        return $json;
    }
    
}
