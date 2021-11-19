package com.zmy.demo.controller;

// RestController、RequestMapping、RequestParam、RequestBody
import com.zmy.demo.util.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.zmy.demo.service.VacationService;
import com.zmy.demo.bean.*;

/**
 * 请假管理,发起请假申请以及查看请假申请
 */
@RestController
@RequestMapping("/vacation")
public class VacationController {
    @Autowired private VacationService service;

    // 测试ing
    @PostMapping()
    @PreAuthorize("hasAnyRole('staff', 'manager', 'general', 'personnel')")
    public Result apply(@RequestBody VacationVO vac) {
        return service.apply(vac.toBO());
    }

    // 测试pass
    @GetMapping("/self")
    @PreAuthorize("hasAnyRole('staff', 'manager', 'general', 'personnel')")
    public Result getSelf(@RequestParam("userId") String userId) {
        return service.getSelf(userId);
    }

    // DONE
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('staff', 'manager', 'general', 'personnel')")
    public void delete(@RequestParam("userId") String userId) {
        service.delete(userId);
    }

    // 测试pass
    @GetMapping("/department")
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
    @GetMapping("/company")
    @PreAuthorize("hasRole('general')")
    public Result getByGeneral(
            @RequestParam("query") String query,
            @RequestParam("currentPage") int currentPage,
            @RequestParam("pagesize") int pagesize
    ) {
        return service.getByGeneral(query, currentPage, pagesize);
    }
}
