<template>
    <div class='main'>
         <!--面包屑导航区域-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>请假管理</el-breadcrumb-item>
            <el-breadcrumb-item>员工请假信息</el-breadcrumb-item>
        </el-breadcrumb>

        <!-- 搜索区域-->
        <el-row :gutter='30'>
            <el-col :span='7' style='margin-left:18px'>
                <el-input placeholder="请输入员工号查找" clearable v-model='queryInfo.query' @clear='getVacationList'>
                    <el-button slot="append" icon="el-icon-search"@click='getVacationList'></el-button>
                </el-input>
            </el-col> 
        </el-row>
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

                <el-table-column label="申请状态">
                    <template slot-scope='scope'>
                        <span :class='statusColor(scope.row.status)'>{{scope.row.status}}</span>

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
            //{
        //     userId: '123456',
        //     userName:'黄鸿',
        //     department: 'A部门',
        //     startDate: '2020-04-11',
        //     startTime: '7:00',
        //     endDate:'2020-05-11',
        //     endTime:'7:00',
        //     type:'陪产假',
        //     reason:'陪老婆生孩子',
        //     status:'申请已通过'
           
        //   }, 
        //   {
        //     userId: '123456',
        //     userName:'黄鸿',
        //     department: 'A部门',
        //     startDate: '2020-04-11',
        //     startTime: '7:00',
        //     endDate:'2020-05-11',
        //     endTime:'7:00',
        //     type:'陪产假',
        //     reason:'陪老婆生孩子',
        //     status:'申请已拒绝'
        //   }, 
        //   {
        //     userId: '123456',
        //     userName:'黄鸿',
        //     department: 'A部门',
        //     startDate: '2020-04-11',
        //     startTime: '7:00',
        //     endDate:'2020-05-11',
        //     endTime:'7:00',
        //     type:'陪产假',
        //     reason:'陪老婆生孩子',
        //     status:'申请已拒绝'
        //   }, 
        //   {
        //    userId: '123456',
        //     userName:'黄鸿',
        //     department: 'A部门',
        //     startDate: '2020-04-11',
        //     startTime: '7:00',
        //     endDate:'2020-05-11',
        //     endTime:'7:00',
        //     type:'陪产假',
        //     reason:'陪老婆生孩子',
        //     status:'申请已拒绝'
        //   },
        //    {
        //    userId: '123456',
        //     userName:'黄鸿',
        //     department: 'A部门',
        //     startDate: '2020-04-11',
        //     startTime: '7:00',
        //     endDate:'2020-05-11',
        //     endTime:'7:00',
        //     type:'陪产假',
        //     reason:'陪老婆生孩子',
        //     status:'申请已通过'
        //   }
        vacationList: [],
                
        total: 0,
        //获取用户列表的参数对象
        queryInfo:{
            query:'',
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
  },
  methods: {
        statusColor(status){
            console.log(status);
            if(status==='尚未审批'|| status=='申请已拒绝'){
                return 'red';
            }else if(status==='申请已通过'){
                return 'green';
            }else{
                return 'yellow';
            }
        },
        async getVacationList(){
            // console.log(window.sessionStorage.getItem("token"));
            //res中包含的请假表列表和请假表列表总和
            let userId = window.sessionStorage.getItem("userId");
            const{data: res} = await this.$http.get("staff/role?userId="+userId);
            // console.log(res);
            if(res === "manager"){
                const{data: res1} = await this.$http.get("/vacation/department?userId="+userId,{
                    params: this.queryInfo
                });
                //  console.log(res1);
                if(res1.code===0){
                this.vacationList = res1.data.list;
                this.total = res1.data.total;
                }
                else if(res1.code===1){
                    this.$message.error(res1.info);
                }
                else if(res1.code===2){
                   this.$message.error(res1.info);
                }
                else {
                    this.vacationList = res1.data.list;
                    this.total = res1.data.total;
                    // this.$message.success(res1.info);
                }

            }

             if(res === "general"){
                const{data: res2} = await this.$http.get("/vacation/company",{
                    params: this.queryInfo
                });
                //  console.log(res2);
                if(res2.code===0){
                    this.vacationList = res2.data.list;
                    this.total = res2.data.total;
                }
                else if(res2.code===1){
                    this.$message.error(res2.info);
                }
                else if(res2.code===2){
                    this.vacationList = res2.data.list;
                    this.total = res2.data.total;
                }
                else{
                    this.vacationList = res2.data.list;
                    this.total = res2.data.total;
                    // this.$message.success(res2.info);
                }

            }
            console.log(this.vacationList);
            
           
            
        },

        // 监听CurrentChange事件
        handleCurrentChange(newPage){
            this.queryInfo.currentPage = newPage;
            this.getVacationList();
        },


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
    .red{
        color:#F56C6C;
    }
    .green{
        color:#67C23A;
    }
    .yellow{
        color:#E6A23C;
    }
    
</style>