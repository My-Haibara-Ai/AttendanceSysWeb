package com.zmy.demo.controller;

// RestController、RequestMapping、RequestParam、RequestBody
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.zmy.demo.service.AttendanceService;
import com.zmy.demo.util.*;

/**
 * 考勤管理,员工签到签退以及人事部获取报表
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired private AttendanceService service;

    // DONE
    @GetMapping("/report")
    @PreAuthorize("hasRole('personnel')")
    public Page getReport(
            @RequestParam("query") String query,
            @RequestParam("currentPage") int currentPage,
            @RequestParam("pagesize") int pagesize
    ) {
        return service.getReport(query, currentPage, pagesize) ;
    }


    @PostMapping("/signin")
    @PreAuthorize("hasAnyRole('staff', 'manager', 'general', 'personnel')")
    public Result signIn(@RequestParam("userId") String userId) {
        return service.signIn(userId);
    }

    @PostMapping("/signout")
    @PreAuthorize("hasAnyRole('staff', 'manager', 'general', 'personnel')")
    public Result signOut(@RequestParam("userId") String userId) {
        return service.signOut(userId);
    }
}
