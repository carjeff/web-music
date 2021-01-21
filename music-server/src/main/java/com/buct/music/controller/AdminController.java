package com.buct.music.controller;

import com.buct.music.controller.dto.AdminReq;
import com.buct.music.result.CodeMsg;
import com.buct.music.result.Result;
import com.buct.music.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Zhang JunFeng
 * @date 2020/12/31 13:47
 */

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login/status")
    public Result<CodeMsg> loginStatus(@RequestBody AdminReq adminReq) {
        try {
            boolean flag = adminService.verifyPassword(adminReq.getUsername(), adminReq.getPassword());
            log.info("登录用户信息为：username: {}, password: {}", adminReq.getUsername(), adminReq.getPassword());
            if (flag) {
                return Result.success(CodeMsg.LOGIN_SUCCESS);
            } else {
                return Result.error(CodeMsg.LOGIN_FAILURE);
            }
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }

    }
}
