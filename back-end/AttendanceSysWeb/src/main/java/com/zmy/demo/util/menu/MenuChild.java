package com.zmy.demo.util.menu;

// 孩子菜单栏
@lombok.Data public class MenuChild {
    private int id;                     // 菜单项编号
    private String authName;            // 菜单项名
    private String path;                // 路径名

    public MenuChild(int id, String authName, String path) {
        this.id = id;
        this.authName = authName;
        this.path = path;
    }
}
