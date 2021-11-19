<template>
    <div>
        <!--面包屑导航区域-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>请假管理</el-breadcrumb-item>
            <el-breadcrumb-item>请假表状态</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 请假表状态区域 -->
        <el-card v-if="hasVcation">
            <div slot="header" class="clearfix">
                <span class='head'>请假表</span>
            </div>
            <div>
                <el-row>
                    <el-col :span="4">
                        <span>员工号:</span>
                    </el-col>
                    <el-col :span="4">
                        <span class='info'><span class='info'>{{vacationTable.userId}}</span></span>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="4">
                        <span>姓名：</span>
                    </el-col>
                    <el-col :span="4">
                        <span class='info'><span class='info'>{{vacationTable.userName}}</span></span>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="4">
                        <span>请假类型：</span>
                    </el-col>
                    <el-col :span="4">
                        <span class='info'><span class='info'>{{vacationTable.type}}</span></span>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="4">
                        <span>部门:</span>
                    </el-col>
                    <el-col :span="4">
                        <span class='info'><span class='info'>{{vacationTable.department}}</span></span>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="4">
                        <span>请假开始时间：</span> </span>
                    </el-col>
                    <el-col :span="4">
                        <span class='info'><span class='info'>{{vacationTable.startDate}} {{vacationTable.startTime}}</span></span>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="4">
                        <span>请假结束时间：</span>
                    </el-col>
                    <el-col :span="4">
                        <span class='info'><span class='info'>{{vacationTable.endDate}} {{vacationTable.endTime}}</span></span>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="4">
                        <span>请假原因：</span>
                    </el-col>
                    <el-col :span="4">
                        <span class='info'>{{vacationTable.reason}}</span>
                    </el-col>
                    
                </el-row>

                <el-row id='status'>
                    <el-col :span="24">
                        <span>申请状态：<span :class='statusColor'>{{vacationTable.status}}</span></span>
                    </el-col>
                </el-row>
            </div>

                
            <div id='btns'>
                <el-button type="warning" @click="reSubmit">重新提交</el-button>
                <el-button type="danger" @click='deleteSubmit'>删除申请</el-button>
            </div>
        </el-card>
        <el-card v-else>
            <span class="noVac">当前没有请假申请</span>
        </el-card>
    </div>
</template>

<script>
export default {
  data () {
    return {
        hasVcation: true,

        vacationTable:{
            userId: '',
            userName:"",
            type:"",
            department:"",
            startDate:"",
            startTime:"",
            endDate:"",
            endTime:"",
            reason:"",
            status:"",

        }
    };
  },
  components: {},
  created () {
      this.initData();
  },
  methods: {

      async initData(){
          let userId = window.sessionStorage.getItem("userId");
          const{data: res} = await this.$http.get("/vacation/self?userId="+userId);
          console.log(res);
          if(res.code === 0){
              this.vacationTable = res.data
            // console.log(this.vacationTable);
          }else {
              this.hasVcation = false;
          }
          
         
      },
      
      reSubmit(){
          this.$confirm('是否重新提交请假申请', '提示',{
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
          }).then(()=>{
              this.$router.push("/vacationTable");
              this.$message({
                   type: 'success',
                   message: '已返回申请界面'
              })
          }).catch(()=>{
              this.$message({
                  type: 'info',
                  message: '已取消'
              })
          })
 

          
      },
    async deleteSubmit(){
        const confirmResult = await this.$confirm(
            '是否删除该请假申请',
            '提示',
            {
                confirmButtonText:'确定',
                cancelButtonText:'取消',
                type:'warning'
            }

        ).catch(err => err);

        if(confirmResult !== 'confirm'){
            return this.$message.info('已取消删除')
        }
        //    this.$http.delete('');
         let userId = window.sessionStorage.getItem("userId");
        const{data: res} = await this.$http.delete("/vacation/delete?userId="+userId);
        if(res.code === 0){
            this.$message.success("删除成功")
        }else{
            this.$message.error("当前没有请假申请");
        }
        this.hasVcation = false;
    
       }
  },
  computed:{
      statusColor(){
          if(this.vacationTable.status==='尚未审批'|this.vacationTable.status=='审批已拒绝'){
              return 'red';
          }else if(this.vacationTable.status==='审批已通过'){
              return 'green';
          }else{
              return "yellow";
          }
      }

  },


}
</script>

<style lang='less' scoped>
    .head{
        display:block;
        font-size:20px;
        text-align:center;
        color:#303133
        // margin:0 auto;
    }

    .el-row{
        font-size: 17px;
        margin-bottom: 20px;
        color: #909399
    }

    #status{
        margin-top:70px;
        text-align:right;
        color:#303133

    }

    #btns{
        margin:0 auto;
        width:250px;
        .el-button{
             margin-left:20px;
        }
    }
    .noVac{
        display: block;
        margin: 0 auto;
        color:#F56C6C;
        text-align:center;       
    }

    .red{
        color:#F56C6C;
    }
    .green{
        color:#67C23A;
    }
    .yellow{
        color:#E6A23C;
    }

    .info{
        color:#606266;
        
    }
</style>

