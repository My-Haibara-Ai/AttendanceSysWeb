package com.zmy.demo.util.menu;

import java.util.*;

// 菜单信息
@lombok.Data public class Menu {
    private List<MenuRoot> menuList;    // 根菜单项列表

    public Menu(MenuRoot... rootList) {
        menuList = new ArrayList<>();

        for (MenuRoot root: rootList) {
            menuList.add(root);
        }
    }
}


