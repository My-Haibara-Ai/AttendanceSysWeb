package com.zmy.demo.util.menu;

import java.util.*;

// 根菜单栏
@lombok.Data public class MenuRoot {
    private int id;                     // 菜单项编号
    private String authName;            // 菜单项名
    private List<MenuChild> children;   // 孩子菜单项列表

    public MenuRoot(int id, String authName, MenuChild... childList) {
        this.id = id;
        this.authName = authName;
        this.children = new ArrayList<>();

        for (MenuChild child: childList) {
            children.add(child);
        }
    }
}
