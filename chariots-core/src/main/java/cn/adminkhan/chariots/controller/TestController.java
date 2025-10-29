/**
 * Copyright 2025, Wujingjie. All rights reserved.
 */
package cn.adminkhan.chariots.controller;


import cn.adminkhan.chariots.service.Dsl2SqlService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Package : cn.adminkhan.chariots.controller
 * @Description : 
 * @Date : 2025/10/16 
 * @Author Wujingjie
 * @Version v1.0.0
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private Dsl2SqlService dsl2SqlService;

    @PostMapping("/dsl2sql")
    public Map<String, String> json2sql(@RequestBody String dslJson) {
        return Map.of("sql", dsl2SqlService.jsonToSql(dslJson));
    }

}
