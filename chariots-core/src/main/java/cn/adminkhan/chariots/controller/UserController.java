/**
 * Copyright 2025, Wujingjie. All rights reserved.
 */
package cn.adminkhan.chariots.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @Package : cn.adminkhan.chariots.controller
 * @Description : 
 * @Date : 2025/10/14
 * @author Wujingjie
 * @Version v1.0.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getInfo/{username}")
    @ResponseBody
    public String getInfo(@PathVariable(name = "username") String username) {
        System.out.println(username + " SUCCESS!");
        return username;
    }

}
