<template>
  <div>
        <!--面包屑导航区域-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>人事科管理</el-breadcrumb-item>
            <el-breadcrumb-item>月底报表</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 月底报表区 -->
        <div class="tableContainer">
                <el-table 
                    stripe
                    :data="baoBiaoList"
                    style="width: 100%">
                    <el-table-column type='expand'>
                        <template slot-scope='props'>
                            <el-form label-position="left" inline class="tableExpand">
                                <el-form-item label="出勤时长:">
                                    <span>{{props.row.workHour}}小时</span>
                                </el-form-item>
                                <el-form-item label="带薪假期时长:">
                                    <span>{{props.row.paid}}小时</span>

                                </el-form-item>
                                <el-form-item label="不带薪假期时长:">
                                    <span>{{props.row.unpaid}}小时</span>
                                </el-form-item>
                                <el-form-item label="工资小时数:">
                                    <span>{{props.row.valid}}小时</span>
                                </el-form-item>
                                                            
                            </el-form>
                        </template>
                    </el-table-column>
                    <el-table-column label="员工号" prop='userId'>

                    </el-table-column>
                    <el-table-column label="姓名" prop='userName'>

                    </el-table-column>
                    <el-table-column label="部门" prop='department'>

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
        total: 0,
        //获取用户列表的参数对象
        queryInfo:{
            query:"",
            //当前页数
            currentPage:1,
            //当前每页显示多少条数据
            pagesize:5
        },
        baoBiaoList:[
            {
                workHour: '',
                paid: '',
                unpaid: '',
                valid: '',
                userId: '',
                userName: '',
                department: '',
            },
            
        ],
      
    };
  },
  components: {},
  created () {
      this.initData();
  },
  methods: {
        // 监听CurrentChange事件
        handleCurrentChange(newPage){
            this.queryInfo.currentPage = newPage;
            this.initData();
        },
        async initData(){
             const{data: res} = await this.$http.get("/attendance/report",{
              params: this.queryInfo
            });

            this.baoBiaoList = [];

            for(let x in res.list){
                // console.log(x);
                //  workHour: '',
                // paid: '',
                // unpaid: '',
                // valid: '',
            var baoBiao = {
                workHour: '',
                paid: '',
                unpaid: '',
                valid: '',
                userId: '',
                userName: '',
                department: '',
            }
                console.log(res.list[x].staff);
                baoBiao.userName = res.list[x].staff.userName;
                baoBiao.department = res.list[x].staff.department;
                baoBiao.userId = res.list[x].staff.userId;
                baoBiao.workHour = res.list[x].attendance.workHour;
                baoBiao.paid = res.list[x].attendance.paid;
                baoBiao.unpaid = res.list[x].attendance.unpaid;
                baoBiao.valid = res.list[x].attendance.valid;
                console.log(baoBiao);
                this.baoBiaoList.push(baoBiao);
            }
            this.total = res.total;
        }

  }
}
</script>

<style lang="less" scoped>
   .tableContainer{
        padding: 20px;        
    }

     .tableExpand{
        font-size: 0;
    }  

    .Pagination{
        display: flex;
        justify-content: flex-start;
        margin-top: 8px;
    }
    
    .tableExpand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }

    .tableExpand .el-form-item span{
        color:#303133
    }

    .tableExpand .el-form-item label {
        width: 90px;
        color: #909399;
    }
</style>