package com.zmy.demo.controller;

// RestController、RequestMapping、RequestParam、RequestBody
import com.zmy.demo.util.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.zmy.demo.service.StaffService;
import com.zmy.demo.bean.*;
import com.zmy.demo.util.menu.Menu;

/**
 * 信息管理,对人员信息进行增删改查
 */
@RestController
@RequestMapping("/staff")
public class StaffController {
    @Autowired private StaffService service;

    // DONE
    @PostMapping()
    @PreAuthorize("hasRole('personnel')")
    public Result add(@RequestBody StaffVO staff) {
        return service.add(staff.toBO());
    }

    // 测试pass
    @GetMapping("/company")
    @PreAuthorize("hasRole('personnel')")
    public Result getByPersonnel(
            @RequestParam("query") String query,
            @RequestParam("currentPage") int currentPage,
            @RequestParam("pagesize") int pagesize
    ) {
        return service.getByPersonnel(query, currentPage, pagesize);
    }

    // 测试pass
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('personnel')")
    public Result delete(@RequestParam("userId") String userId) {
        return service.delete(userId);
    }

    // DONE
    @PutMapping("/update")
    @PreAuthorize("hasRole('personnel')")
    public Result update(@RequestBody StaffVO staff) {
        return service.update(staff.toBO());
    }

    // 测试pass
    @GetMapping("/self")
    @PreAuthorize("hasAnyRole('staff', 'manager', 'general', 'personnel')")
    public Result getSelf(@RequestParam("userId") String userId) {
        return service.getSelf(userId);
    }

    // 测试pass
    /**
     * 获取用户名为userId的员工的菜单栏信息
     * @param userId 用户名
     * @return 用户名为userId的员工的菜单
     */
    @GetMapping("/menu")
    @PreAuthorize("hasAnyRole('staff', 'manager', 'general', 'personnel')")
    public Menu getMenu(@RequestParam("userId") String userId) {
        return service.getMenu(userId);
    }

    // DONE
    @GetMapping("/role")
    @PreAuthorize("hasAnyRole('staff', 'manager', 'general', 'personnel')")
    public String getRoleName(@RequestParam("userId") String userId) {
        return service.getRoleName(userId);
    }
}
