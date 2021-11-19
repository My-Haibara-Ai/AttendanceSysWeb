<template>
  <div>
       <!--面包屑导航区域-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>我的</el-breadcrumb-item>
        </el-breadcrumb>
         <!-- 个人中心区域 -->
        <el-card :body-style="{ padding: '40px' }">
            <div slot="header" class="clearfix">
                <span class='head'>个人中心</span>
            </div>
            <div>
                <el-row>
                    <el-col :span="8">
                        <span>员工号:</span>
                    </el-col>
                    <el-col :span="4">
                        <span class='info'><span class='info'>{{staff.userId}}</span></span>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="8">
                        <span>姓名：</span>
                    </el-col>
                    <el-col :span="4">
                        <span class='info'><span class='info'>{{staff.userName}}</span></span>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="8">
                        <span>性别：</span>
                    </el-col>
                    <el-col :span="4">
                        <span class='info'><span class='info'>{{staff.sex}}</span></span>
                    </el-col>
                </el-row>
       
                <el-row>
                    <el-col :span="8">
                        <span>年龄:</span>
                    </el-col>
                    <el-col :span="4">
                        <span class='info'><span class='info'>{{staff.age}}</span></span>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="8">
                        <span>部门:</span>
                    </el-col>
                    <el-col :span="4">
                        <span class='info'><span class='info'>{{staff.department}}</span></span>
                    </el-col>
                </el-row>
          
                <el-row>
                    <el-col :span="8">
                        <span>工龄：</span> </span>
                    </el-col>
                    <el-col :span="4">
                        <span class='info'><span class='info'>{{staff.employed}}</span></span>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="8">
                        <span>邮件：</span>
                    </el-col>
                    <el-col :span="4">
                        <span class='info'><span class='info'>{{staff.email}}</span></span>
                    </el-col>
                </el-row>

                
            </div>

                
        </el-card>
  </div>
</template>

<script>
export default {
  name: '',
  data () {
    return {
      
        staff: {
            // userId: '123456',
            // userName:'黄鸿',
            // sex: '男',
            // age: '20',
            // employed: '4',
            // department: 'A部门',
            // email:'123@qq.com',
        }, 
      
    };
  },
  components: {},
  mounted () {},
  created () {
    this.getStaff()
  },
  methods: {
    async getStaff(){
      let userId = window.sessionStorage.getItem("userId");
      const{data: res} = await this.$http.get("/staff/self?userId="+userId);
    //  console.log(res);
      if(res.code!== 0){
          return this.$message({
          type: 'error',
          message:'初始化数据失败'
                 
        })
      }
      this.staff = res.data;
      

      
    }
  }
}
</script>

<style lang="less" scoped>
    .head{
        display:block;
        font-size:20px;
        margin-left: 25px;
        // text-align:center;
        color:#303133
        // margin:0 auto;
    }

    .el-row{
        font-size: 17px;
        margin-bottom: 20px;
        // text-align:center;
        color: #909399
    }

    #status{
        margin-top:70px;
        text-align:right;
        color:#303133

    }

    .info{
        color:#606266;
        
    }

</style>