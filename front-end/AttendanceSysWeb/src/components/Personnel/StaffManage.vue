<template>
    <div class='main'>
            <!--面包屑导航区域-->
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>人事科管理</el-breadcrumb-item>
                <el-breadcrumb-item>员工信息管理</el-breadcrumb-item>
            </el-breadcrumb>

            <!-- 搜索区域-->
            <el-row :gutter='30'>
                <el-col :span='7' style='margin-left:18px'>
                    <el-input placeholder="请输入内容" clearable v-model='queryInfo.query' @clear='getStaffList("all")'>
                        <el-button slot="append" icon="el-icon-search"@click='getStaffList("one")'></el-button>
                    </el-input>
                </el-col>
                <el-col :span='4'>
                    <el-button type="primary" @click='addDialogVisiable=true'>添加员工</el-button>
                </el-col>
            </el-row>
            <!-- 员工列表 -->
            <div class="tableContainer">
                <el-table 
                    stripe
                    :data="staffList"
                    style="width: 100%">
                    <el-table-column type='expand'>
                        <template slot-scope='props'>
                            <el-form label-position="left" inline class="tableExpand">
                                <el-form-item label="员工号：">
                                    <span>{{props.row.userId}}</span>
                                </el-form-item>
                                <el-form-item label="姓名：">
                                    <span>{{props.row.userName}}</span>
                                </el-form-item>
                                <el-form-item label="性别：">
                                    <span>{{props.row.sex}}</span>

                                </el-form-item>
                                <el-form-item label="年龄：">
                                    <span>{{props.row.age}}岁</span>

                                </el-form-item>
                                <el-form-item label="部门：">
                                    <span>{{props.row.department}}</span>

                                </el-form-item>
                                <el-form-item label="email：">
                                    <span>{{props.row.email}}</span>

                                </el-form-item>

                                <el-form-item label="工龄：">
                                    <span>{{props.row.employed}}年</span>

                                </el-form-item>

                                <!-- <el-form-item label="年假：">
                                    <span>{{props.row.annual}}天</span>

                                </el-form-item>
                                <el-form-item label="病假：">
                                    <span>{{props.row.sick}}天</span>

                                </el-form-item>
                                <el-form-item label="婚假：">
                                    <span>{{props.row.marriage}}天</span>

                                </el-form-item>
                                <el-form-item label="产假：">
                                    <span>{{props.row.maternity}}天</span>

                                </el-form-item>
                                <el-form-item label="产检假：">
                                    <span>{{props.row.checkUp}}天</span>

                                </el-form-item>
                                <el-form-item label="哺乳假：">
                                    <span>{{props.row.feeding}}小时</span>

                                </el-form-item>
                                <el-form-item label="事假：">
                                    <span>{{props.row.affair}}小时</span>

                                </el-form-item>
                                <el-form-item label="陪产假：">
                                    <span>{{props.row.paternity}}天</span>

                                </el-form-item> -->
                            </el-form>
                        </template>
                    </el-table-column>
                    <el-table-column label="员工号" prop='userId'>

                    </el-table-column>
                    <el-table-column label="姓名" prop='userName'>

                    </el-table-column>
                    <el-table-column label="部门" prop='department'>

                    </el-table-column>

                    <el-table-column label="操作">
                        <template slot-scope='scope'>               
                            <el-tooltip class="item" effect="dark" content="编辑员工信息" placement="top-start">
                                <el-button type="primary" icon="el-icon-edit"size='mini' @click='showEditDialog(scope.row.userId)'></el-button>
                            </el-tooltip>

                            <el-tooltip class="item" effect="dark" content="删除员工" placement="top-start">
                                <el-button type="danger" icon="el-icon-delete"size='mini' @click='removeUserById(scope.row.userId)'></el-button>
                            </el-tooltip>
                             
                            <!-- <el-tooltip effect="dark" content="分配假期" placement="top-start" :enterable="false">
                                <el-button
                                    type="warning"
                                    icon="el-icon-setting"
                                    size="mini"
                                    @click="setVacation(scope.row.userId)"
                                ></el-button>
                            </el-tooltip> -->
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
            <!-- 添加员工对话框 -->
            <el-dialog title='添加用户' v-model="addForm":visible.sync='addDialogVisiable' width='50%' @close='addDialogClosed()'>
                <el-form :model='addForm' :rules='addFormRules' label-width='80px' ref='addFormRef'>
                    <el-form-item label='员工号' prop="userId">
                        <el-input v-model='addForm.userId'></el-input>
                    </el-form-item>
                    <el-form-item label='姓名' prop="userName">
                        <el-input v-model='addForm.userName'></el-input>
                    </el-form-item>
                    <el-form-item label='性别' prop="sex">
                        <el-input v-model='addForm.sex'></el-input>
                    </el-form-item>
                    <el-form-item label='年龄' prop="age">
                        <el-input v-model='addForm.age'></el-input>
                    </el-form-item>
                    <el-form-item label='部门' prop="department">
                        <!-- <el-input v-model='addForm.department'></el-input> -->
                        <el-select v-model="addForm.department" placeholder="请选择所在部门">
                            <el-option label="技术部门" value="技术部门"></el-option>
                            <el-option label="运营部门" value="运营部门"></el-option>
                            <el-option label="产品部门" value="产品部门"></el-option>
                            <el-option label="市场部门" value="市场部门"></el-option>
                            <el-option label="综合部门" value="综合部门"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label='邮箱' prop="email">
                        <el-input v-model='addForm.email'></el-input>
                    </el-form-item>
                    <span>
                        <el-button @click='addDialogVisiable=false'>取消</el-button>
                        <el-button type='primary' @click='addStaff()'>确定</el-button>
                    </span>
                </el-form>
            </el-dialog>

              <!--修改员工基本信息对话框 -->
            <el-dialog title='修改员工基本信息' v-model="editForm" :visible.sync='editDialogVisiable' width='50%' @close='editDialogClosed()'>
                <el-form :model='addForm' label-width='80px' ref='editFormRef'>
                    <el-form-item label='员工号'>
                        <el-input v-model="editForm.userId" ></el-input>
                    </el-form-item>
                    <el-form-item label='姓名'>
                        <el-input v-model='editForm.userName'></el-input>
                    </el-form-item>
                    <el-form-item label='性别'>
                        <el-input v-model='editForm.sex' disabled></el-input>
                    </el-form-item>
                     <el-form-item label='年龄'>
                        <el-input v-model='editForm.age' disabled></el-input>
                    </el-form-item>
                    <el-form-item label='部门'>
                        <el-select v-model="editForm.department" placeholder="请选择所在部门">
                         
                            <el-option label="技术部门" value="技术部门"></el-option>
                            <el-option label="运营部门" value="运营部门"></el-option>
                            <el-option label="产品部门" value="产品部门"></el-option>
                            <el-option label="市场部门" value="市场部门"></el-option>
                            <el-option label="综合部门" value="综合部门"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label='邮箱'>
                        <el-input v-model='editForm.email'></el-input>
                    </el-form-item>
                    <div class='btns'>
                        <el-button @click='editDialogVisiable=false'>取消</el-button>
                        <el-button type='primary' @click='editUserInfo()'>确定</el-button>
                    </div>
                </el-form>
            </el-dialog>

             <!--修改员工假期信息对话框 -->
            <el-dialog title='设置员工假期信息'  :visible.sync='vacationDialogVisiable' width='50%' @close='vacationDialogClosed()'>
                <el-form :model='vacationForm' label-width='80px' ref='vacationFormRef'>              
                    <el-form-item label='年假'>
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-input v-model="vacationForm.annual"></el-input>
                            </el-col>
                            <el-col :span="4">
                                <span>天</span> 
                            </el-col>
                        </el-row>
                    </el-form-item>
                    <el-form-item label='病假'>
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-input v-model='vacationForm.sick'></el-input>
                            </el-col>
                            <el-col :span="4">
                                <span>天</span> 
                            </el-col>
                        </el-row>                       
                    </el-form-item>
                    <el-form-item label='婚假'>
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-input v-model='vacationForm.marriage' ></el-input>
                            </el-col>
                            <el-col :span="4">
                                <span>天</span> 
                            </el-col>
                        </el-row>                      
                    </el-form-item>
                    <el-form-item label='产假'>
                        <el-row :gutter="20">
                            <el-col :span="12">
                               <el-input v-model='vacationForm.maternity'></el-input>
                            </el-col>
                            <el-col :span="4">
                                <span>天</span> 
                            </el-col>
                        </el-row>                              
                    </el-form-item>
                    <el-form-item label='产检假'>
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-input v-model='vacationForm.checkUp'></el-input>
                            </el-col>
                            <el-col :span="4">
                                <span>天</span> 
                            </el-col>
                        </el-row>                         
                    </el-form-item>
                    <el-form-item label='哺乳假'>
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-input v-model='vacationForm.feeding'></el-input>
                            </el-col>
                            <el-col :span="4">
                                <span>小时</span> 
                            </el-col>
                        </el-row> 
                    </el-form-item>
                    <el-form-item label='事假'>
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-input v-model='vacationForm.affair'></el-input>
                            </el-col>
                            <el-col :span="4">
                                <span>小时</span> 
                            </el-col>
                        </el-row> 
                    </el-form-item>
                     <el-form-item label='产假'>
                          <el-row :gutter="20">
                            <el-col :span="12">
                                <el-input v-model='vacationForm.paternity'></el-input>
                            </el-col>
                            <el-col :span="4">
                                <span>天</span> 
                            </el-col>
                        </el-row> 
                    </el-form-item>
                    <div class='btns'>
                        <el-button @click='vacationVisiable=false'>取消</el-button>
                        <el-button type='primary' @click='addVacation()'>确定</el-button>
                    </div>
                </el-form>
            </el-dialog>
    </div>
 
 
</template>

<script>
export default {
  userName: '',
  data () {
    return {
        staffList: [
        ],

        //设置员工请假信息相关
        vacationDialogVisiable:false,

        vacationForm:{
            userId:"",
            annual:'5',
            sick:'0',
            marriage:'10',
            maternity:'0', 
            checkUp:'0',
            feeding:'0',
            affair:'0',
            paternity:'0',
        },


        //编辑员工基本信息相关
        editDialogVisiable:false,

        editForm:{
            userId: '',
            userName: '',
            sex: '',
            age: '',
            department: '',
            email: '',
        },

        //添加员工相关
        addDialogVisiable: false,

        addForm:{
            userId: '',
            userName: '',
            sex: '',
            age: '',
            department: '',
            email:'',
        },
        addFormRules:{
            userId:[
                { required: true, message: "请输入员工号", trigger: "blur" }
            ],
            userName:[
                { required: true, message: "请输入员工姓名", trigger: "blur" }
            ],
            sex:[
                { required: true, message: "请输入员工性别", trigger: "blur" }
            ],
            age:[
                { required: true, message: "请输入员工年龄", trigger: "blur" }
            ],
            department:[
                { required: true, message: "请输入员工部门", trigger: "change" }
            ],
            email:[
                { required: true, message: "请输入员工邮箱", trigger: "blur" }
            ]
        },

              
        //分页相关
        total: 0,
        //获取用户列表的参数对象
        queryInfo:{
            query:"",
            //当前页数
            currentPage:1,
            //当前每页显示多少条数据
            pagesize:5
        },
    
    };
  },
  components: {},
  created () {
    this.getStaffList("all");
  },
  methods: {
        //设置假期
        vacationDialogClosed(){
            this.$refs.vacationFormRef.resetFields();
        },
        async setVacation(userId){

            const{data:res} = await this.$http.get('/vacation/self?userId='+userId);
            // if(res.meta.status!==200){
            //     return this.$message.error('查询员工假期信息失败');
            // }
            console.log(res);
            this.vacationForm=res.vac;
            this.vacationDialogVisiable=true;
            
        },

         //设置假期信息并提交
        editVacationInfo() {
            this.$refs.editFormRef.validate(async valid => {
                //console.log("修改信息的校验",valid);
                if (!valid) return;
                //发起修改用户的数据请求
                const { data: res } = await this.$http.put(
                "/approval/remaining?userId="+this.userId ,this.vacationForm
                );

                // if (res.meta.status !== 200) {
                // return this.$message.error("更新用户信息失败！");
                // }
                //修改一般都会有这三个操作
                //关闭对话框
                this.editDialogVisiable = false;
                //刷新数据列表
                this.getStaffList(); //直接调用method
                //提示修改成功
                this.$message.success("更新假期信息成功！");
            });
        },

        //编辑员工基本信息
        async showEditDialog(userId){
            // console.log(this.queryInfo);
            // console.log(userId);
            this.queryInfo.query = userId;         
            const{data:res} = await this.$http.get('staff/company',{
                params: this.queryInfo
            });
            
            this.editForm=res.data.list[0];
            // console.log(this.editForm)
            this.editDialogVisiable=true;
            

        },

        editDialogClosed(){
            this.$refs.editFormRef.resetFields();
        },

        //修改用户信息并提交
        editUserInfo() {
            this.$refs.editFormRef.validate(async valid => {
                //console.log("修改信息的校验",valid);
                if (!valid) return;
                //发起修改用户的数据请求
                const { data: res } = await this.$http.put(
                "/staff/update", this.editForm
                );

                // if (res.meta.status !== 200) {
                // return this.$message.error("更新用户信息失败！");
                // }
                //修改一般都会有这三个操作
                //关闭对话框
                this.editDialogVisiable = false;
                //刷新数据列表
                this.getStaffList("all"); //直接调用method
                //提示修改成功
                this.$message.success("更新员工信息成功！");
            });
        },

        //添加员工
        addDialogClosed(){
            this.$refs.addFormRef.resetFields();
        },

        addStaff(){
            this.$refs.addFormRef.validate(async valid => {
                if(!valid) return;
                const{data:res} = await this.$http.post('/staff', this.addForm);
                if(res.code === 1){
                    return this.$message.error("员工已存在");
                }
                this.$message.success('添加员工成功');
                this.addDialogVisiable = false;
                this.getStaffList("all");
            })
        },

        //删除员工信息
        async removeUserById(userId){
            //弹框询问是否删除该员工
            const confirmResult = await this.$confirm(
                '此用户该永久删除该员工，是否继续',
                '提示',
                {
                    confirmButtonText:'确定',
                    cancelButtonText:'取消',
                    type:'warning'
                }

            ).catch(err => err);

            //如果用户确认删除，则返回值为字符串 confirm
            //如果用户取消了删除，则返回值为字符串 cancel
            if(confirmResult !== 'confirm'){
                return this.$message.info('已取消删除')
            }

            const{data: res} = await this.$http.delete('/staff/delete?userId='+userId);
            if(res.code !== 0){
                return this.$message.error('删除员工信息失败！');
            }
            this.$message.success('删除员工信息成功');
            this.getStaffList("all");

        },

        //得到员工信息
        async getStaffList(choose){
            if(choose==="all"){
                this.queryInfo.query="";
            }
            //res中包含的用户列表和用户列表总和
            const{data: res} = await this.$http.get("/staff/company",{
              params: this.queryInfo
            });

            // console.log(res);
            // console.log();
            if(res.code===0){
                this.staffList = res.data.list;
                this.total = res.data.total;
            }
            else if(res.code===1){
                this.$message.error(res.info);
            }else{
                this.staffList = res.data.list;
                this.total = res.data.total;
                // this.$message.success(res.info);
            }
            
        },

        // 监听CurrentChange事件
        handleCurrentChange(newPage){
            this.queryInfo.currentPage = newPage;
            this.getStaffList("all");
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

    .btns{
        margin-left:220px;
    }
</style>