<template>
    <el-container class='home_container'>
        <el-header>
            <div>
                <img src="../assets/timg.png" alt="" style="width:50px;height:50px">
                <span>{{vocation}}</span>
            </div>
             <el-button type="info" @click="logout">退出</el-button>
        </el-header>
        <el-container>
            <el-aside width="200px">
                <!-- 侧边菜单栏 -->
                <el-menu active-text-color="#409BFF" text-color="#fff" 
                 background-color="#333744" unique-opened router :default-active="$route.path">
                    <el-menu-item  index='/signInAndOut'>
                        <template>
                            <i class="el-icon-s-home"></i>
                            <span>首页</span>
                        </template>
                    </el-menu-item>
                    <el-menu-item  index="/myInformation">
                        <template>
                            <i class="el-icon-view"></i>
                            <span>我的</span>
                        </template>
                    </el-menu-item>
                    <!-- 一级菜单栏 -->
                    <el-submenu :index="item.id+''" v-for="item in menuList" :key="item.id">
                        <template slot="title">
                            <i :class="icons[item.id]"></i>
                            <span>{{item.authName}}</span>
                        </template>
                        <!-- 二级菜单栏 -->
                        <el-menu-item :index="'/'+subItem.path" 
                        v-for="subItem in item.children" :key="subItem.id"
                       >
                            <template>
                                <i class="el-icon-menu"></i>
                                <span>{{subItem.authName}}</span>
                            </template>
                        </el-menu-item>
                    </el-submenu>
                     
                </el-menu>
            </el-aside>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>

</template>

<script>
export default {
  name: '',
  data () {
    return {
        icons:{
            '1': "el-icon-postcard",
            '2': "el-icon-folder-opened",
                 
            '3': "el-icon-edit",
            '4': "el-icon-user-solid"

        },
        menuList:[
            
           
        ],
        vocation:"",
      
    };
  },
  components: {},
  mounted () {},
  created(){
      this.getMenu();
      this.getVocation()
  },
  methods: {
      async getMenu(){
          let userId = window.sessionStorage.getItem("userId")
          const{data: res} = await this.$http.get("/staff/menu?userId="+userId);
        //   console.log(res);
          this.menuList = res.menuList;
      },

      logout(){
          window.sessionStorage.clear();
          this.$router.push("/login");
          this.$message.success('退出成功');  
      },

      async getVocation(){
        let userId = window.sessionStorage.getItem("userId");
        const{data: res} = await this.$http.get("staff/role?userId="+userId);
        // this.vocation = res
        if(res==='personnel'){
            this.vocation = "人事科"
        }
        else if(res==='staff'){
            this.vocation = '普通员工'
        }
        else if(res==='manager'){
            this.vocation = '部门经理'
        }
        else {
            this.vocation = '总经理'
        }
        
      }
  },
}
</script>

<style lang="less" scoped>

.home_container{
    height:100%;
}
.el-header{
    background-color:#373d41;
    display: flex;
    justify-content: space-between;
    align-items: center;
    >div{
        display: flex;
        align-items: center;
        span{
            margin-left:20px;
            font-size:20px;
            color:#fff;
        }
    }
}

.el-aside{
    height:100%;
    background-color:#333744;
    .el-menu{
    border-right: none;
  }
    // margin-left: -1px;
}

.el-main {
  background-color: #eaedf1;
}

</style>