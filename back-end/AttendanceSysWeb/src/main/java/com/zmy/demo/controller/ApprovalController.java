package com.zmy.demo.controller;

// RestController、RequestMapping、RequestParam、RequestBody
import com.zmy.demo.util.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.zmy.demo.service.ApprovalService;
import com.zmy.demo.bean.*;

/**
 * 请假审核,管理层查看并审核请假申请
 */
@RestController
@RequestMapping("/approval")
public class ApprovalController {
    @Autowired private ApprovalService service;

    // 测试pass
    @GetMapping("/get/personnel")
    @PreAuthorize("hasRole('personnel')")
    public Result getByPersonnel(
            @RequestParam("query") String query,
            @RequestParam("currentPage") int currentPage,
            @RequestParam("pagesize") int pagesize
    ) {
        return service.getByPersonnel(query, currentPage, pagesize);
    }

    // 测试pass
    @GetMapping("/get/manager")
    @PreAuthorize("hasRole('manager')")
    public Result getByManager(
            @RequestParam("userId") String userId,
            @RequestParam("query") String query,
            @RequestParam("currentPage") int currentPage,
            @RequestParam("pagesize") int pagesize
    ) {
        return service.getByManager(userId, query, currentPage, pagesize);
    }

    // 测试pass
    @GetMapping("/get/general")
    @PreAuthorize("hasRole('general')")
    public Result getByGeneral(
            @RequestParam("query") String query,
            @RequestParam("currentPage") int currentPage,
            @RequestParam("pagesize") int pagesize
    ) {
        return service.getByGeneral(query, currentPage, pagesize);
    }

    // 测试pass
    @PutMapping("/personnel")
    @PreAuthorize("hasRole('personnel')")
    public void approveByPersonnel(
            @RequestParam("userId") String userId,
            @RequestParam("pass") String pass
    ) {
        service.approveByPersonnel(userId, pass);
    }

    // 测试pass
    @PutMapping("/manager")
    @PreAuthorize("hasRole('manager')")
    public void approveByManager(
            @RequestParam("userId") String userId,
            @RequestParam("pass") String pass
    ) {
        service.approveByManager(userId, pass);
    }

    // 测试pass
    @PutMapping("/general")
    @PreAuthorize("hasRole('general')")
    public void approveByGeneral(
            @RequestParam("userId") String userId,
            @RequestParam("pass")String pass
    ) {
        service.approveByGeneral(userId, pass);
    }
}
