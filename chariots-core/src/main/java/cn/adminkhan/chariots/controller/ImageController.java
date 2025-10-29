/**
 * Copyright 2025, Wujingjie. All rights reserved.
 */
package cn.adminkhan.chariots.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;

/**
 * @Package : cn.adminkhan.chariots.controller
 * @Description : 
 * @Date : 2025/10/29 
 * @Author Wujingjie
 * @Version v1.0.0
 **/
@RestController
@RequestMapping("/image")
public class ImageController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("文件为空");
        }

        String imagePath = "D:/OtherFile/";

        // 创建目录（如果不存在）
        File dir = new File(imagePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 构建保存路径（可加入时间戳、UUID 防重名）
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(imagePath + fileName);

        try {

            // 1. 获取计算机名称（主机名）
            String hostname = InetAddress.getLocalHost().getHostName();
            System.out.println("计算机名称: " + hostname);

            // 2. 获取 IP 地址（首选非回环、非本地链路地址）
            String ipAddress = getLocalIpAddress();
            System.out.println("IP 地址: " + ipAddress);

            // 3. 获取 MAC 地址（基于 IP 对应的网卡）
            String macAddress = null;
            try {
                macAddress = getMacAddress(ipAddress);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("MAC 地址: " + macAddress);


            // 保存文件
            //Files.write(filePath, file.getBytes());
            return ResponseEntity.ok("图片上传成功，路径：" + filePath.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("保存文件失败");
        }
    }

    /**
     * 获取本机首选 IP 地址（跳过 127.0.0.1 和 IPv6）
     */
    private String getLocalIpAddress() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue; // 跳过回环和未启用的接口
            }
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                    return addr.getHostAddress();
                }
            }
        }
        try {
            return InetAddress.getLocalHost().getHostAddress(); // 回退
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据 IP 地址获取对应的 MAC 地址
     */
    private String getMacAddress(String ip) throws Exception {
        InetAddress inetAddress = InetAddress.getByName(ip);
        NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
        if (network == null) {
            // 尝试通过本地主机获取
            network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        }
        if (network == null) {
            return "无法获取 MAC 地址";
        }

        byte[] mac = network.getHardwareAddress();
        if (mac == null) {
            return "MAC 地址为空";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        return sb.toString();
    }



}

