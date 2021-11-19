<template>
    <div class='main'>
         <!--面包屑导航区域-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>请假审核</el-breadcrumb-item>
            <el-breadcrumb-item>总经理审核</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 搜索区域
        <el-input placeholder="请输入内容" clearable >
            <el-button slot="append" icon="el-icon-search"></el-button>
         </el-input> -->
        <!-- 请假列表 -->
        <div class="tableContainer">
            <el-table 
                :data="vacationList"
                style="width: 100%">
                
                    <el-table-column type='expand'>
                        <template slot-scope='props'>
                            <el-form label-position="left" inline class="table-expand">
                                <el-form-item label="员工号：">
                                    <span>{{props.row.userId}}</span>
                                </el-form-item>
                                <el-form-item label="姓名：">
                                    <span>{{props.row.userName}}</span>
                                </el-form-item>
                                <el-form-item label="部门：">
                                    <span>{{props.row.department}}</span>

                                </el-form-item>
                                <el-form-item label="请假类型：">
                                    <span>{{props.row.type}}</span>

                                </el-form-item>
                                <el-form-item label="开始时间：">
                                    <span>{{props.row.startDate}} {{props.row.startTime}}</span>

                                </el-form-item>
                                <el-form-item label="结束时间：">
                                    <span>{{props.row.endDate}} {{props.row.endTime}}</span>

                                </el-form-item>

                                <el-form-item label="请假理由：">
                                    <span>{{props.row.reason}}</span>

                                </el-form-item>


                            </el-form>
                

                        </template>
                        
                    </el-table-column>
                
                    <el-table-column label="员工号" prop='userId'>

                    </el-table-column>
                    <el-table-column label="姓名" prop='userName'>

                    </el-table-column>
                    <el-table-column label="请假类型" prop='type'>

                    </el-table-column>

                    <el-table-column label="操作" >
                        <template slot-scope="scope">
                            <el-button
                                round
                                type='success'
                                size='mini'
                                @click="pass(scope.row.userId)">
                            同意
                            </el-button>
                            <el-button
                                round
                                type='danger'
                                size='mini'
                                @click="reject(scope.row.userId)">
                            拒绝
                            </el-button>
                        </template>                   
                    </el-table-column>
               
            </el-table>
        </div>
        <!-- 分页区域 -->
        <div class="Pagination">
            <el-pagination
                background
                @current-change="handleCurrentChange"
                :current-page="queryInfo.currentPage"
                :page-size="queryInfo.pagesize"
                layout="total, prev, pager, next"
                :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
  userName: '',
  data () {
    return {
        vacationList: [

          ],
    
          
          total: 0,
          //获取用户列表的参数对象
          queryInfo:{
              query:"",
              //当前页数
              currentPage:1,
              //当前每页显示多少条数据
              pagesize:5
          }
      
    };
  },
  components: {},
  created () {
      this.getVacationList();
      this.total = 0;
  },
  methods: {
         async getVacationList(){
            //res中包含的请假表列表和请假表列表总和
            // let userId = window.sessionStorage.getItem("userId");
            const{data: res} = await this.$http.get("/approval/get/general",{
              params: this.queryInfo
            });
            console.log(res);
            if(res.code===0){
                this.vacationList = res.data.list;
                this.total = res.data.total;
            }          
        },

        // 监听CurrentChange事件
        handleCurrentChange(newPage){
            this.queryInfo.currentPage = newPage;
            this.getVacationList();
        },

         async pass(userName){
            const{data: res} = await this.$http.put("/approval/general?userId="+userName+"&pass="+"通过");
            this.$message.success("已同意");
            this.getVacationList();
        },

        async reject(userName){
            const{data: res} = await this.$http.put("/approval/general?userId="+userName+"&pass="+"通过");
            this.$message.error("已拒绝");
            this.getVacationList();
          
        }

  }
}
</script>

<style lang="less" scoped>
 
    .tableContainer{
        padding: 20px;
        margin-top: 30px;
        margin-bottom:30px;
        
    }

     .table-expand{
        .el-form-item {
            margin-right: 0;
            margin-bottom: 0;
            width: 50%;
        }
        label {
            width: 90px;
            color: #99a9bf;
        }         
    }  
    
</style>