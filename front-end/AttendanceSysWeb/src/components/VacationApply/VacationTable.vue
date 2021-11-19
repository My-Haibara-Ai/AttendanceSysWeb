<template>
    <div>
      <!--面包屑导航区域-->
      <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>请假审请</el-breadcrumb-item>
          <el-breadcrumb-item>请假表</el-breadcrumb-item>
      </el-breadcrumb>
      <div>
        <el-card class="box-card">
            <el-form :model="vacationForm" :rules="rules" ref="vacationFormRef" label-width="100px" class="demo-vacationForm">
                <!-- <el-form-item label="员工号" prop="userId">
                    <el-input v-model="vacationForm.userId"></el-input>
                </el-form-item> -->
                <el-row>
                  <el-col :span="9">
                    <div class="grid-content bg-purple-dark">
                      <el-form-item label="姓名" prop="userName">
                        <el-input v-model="vacationForm.userName"></el-input>
                      </el-form-item>
                    </div>
                  </el-col>
                </el-row>

                <el-form-item label="所在部门" prop="department">
                    <el-select v-model="vacationForm.department" placeholder="请选择所在部门">
                        <el-option label="技术部门" value="技术部门"></el-option>
                        <el-option label="运营部门" value="运营部门"></el-option>
                        <el-option label="产品部门" value="产品部门"></el-option>
                        <el-option label="市场部门" value="市场部门"></el-option>
                        <el-option label="综合部门" value="综合部门"></el-option>
                    </el-select>
                </el-form-item>

                <el-row>
                    <el-col :span="8">
                        <el-form-item label="请假类型" prop="type">
                          <el-select v-model="vacationForm.type" placeholder="请选择请假类型">
                              <el-option label="事假" value="事假"></el-option>
                              <el-option label="年假" value="年假"></el-option>
                              <el-option label="婚假" value="婚假"></el-option>
                              <el-option label="产假" value="产假"></el-option>
                              <el-option label="产检假" value="产检假"></el-option>
                              <el-option label="哺乳假" value="哺乳假"></el-option>
                              <el-option label="陪产假" value="陪产假"></el-option>  
                              <el-option label="病假" value="病假"></el-option>                    
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                          <el-form-item v-if="vacationForm.type === '婚假'?true:false" label="婚假类型" prop="marriageType">
                            <el-select v-model="vacationForm.marriageType" placeholder="请选择婚假类型">
                                <el-option label="晚婚" value="晚婚"></el-option>
                                <el-option label="早婚" value="早婚假"></el-option>            
                          </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                          <el-form-item v-if="vacationForm.type === '产假'?true:false" label="产假类型" prop="maternityType">
                            <el-select v-model="vacationForm.maternityType" placeholder="请选择产假类型">
                                <el-option label="难产" value="难产"></el-option>
                                <el-option label="多胞胎" value="多胞胎"></el-option>
                                <el-option label="未满4月流产" value="未满4月流产"></el-option>
                                <el-option label="满4月流产" value="满4月流产"></el-option>
                                <el-option label="其他" value="其他"></el-option>
                          </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                          <el-form-item v-if="vacationForm.maternityType === '多胞胎' && vacationForm.type === '产假'?true:false" label="婴儿个数" prop="maternityNum">
                            <el-input v-model="vacationForm.maternityNum"></el-input>
                          </el-form-item>
                    </el-col>
                </el-row>
                
                

                <el-form-item label="开始时间" required>
                  <el-col :span="10">
                    <el-form-item prop="startDate">
                      <el-date-picker type="date" placeholder="选择日期" v-model="vacationForm.startDate" style="width: 100%;"value-format="yyyy-M-d"></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="10">
                    <el-form-item prop="startTime">
                      <el-time-picker placeholder="选择时间" v-model="vacationForm.startTime" style="width: 100%;"value-format="H-m"></el-time-picker>
                    </el-form-item>
                  </el-col>
                </el-form-item>



                <el-form-item label="结束时间" required>
                  <el-col :span="10">
                    <el-form-item prop="endDate">
                      <el-date-picker type="date" placeholder="选择日期" v-model="vacationForm.endDate" style="width: 100%;"value-format="yyyy-M-d"></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="10">
                    <el-form-item prop="endTime">
                      <el-time-picker placeholder="选择时间" v-model="vacationForm.endTime" style="width: 100%;"value-format="H-m"></el-time-picker>
                    </el-form-item>
                  </el-col>
                </el-form-item>
    
                <el-form-item label="请假理由" prop="reason">
                    <el-input type="textarea" v-model="vacationForm.reason"></el-input>
                </el-form-item>
                <el-form-item label="申请材料" >
                  <el-upload
                    class="upload-demo"
                    auto-upload
                    :action= "uploadURL"
                    :on-success= "handleSuccess"
                    :on-error= "handleError"
                    :on-preview= "handlePreview"
                    :on-remove= "handleRemove"
                    :before-remove= "beforeRemove"
                    multiple
                    :limit="1"
                    :on-exceed="handleExceed"
                    :file-list="fileList">
                    <el-button size="small" type="primary">点击上传</el-button>
                    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                  </el-upload>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitForm('vacationForm')">提交</el-button>
                  <el-button @click="resetForm('vacationForm')">重置</el-button>
                </el-form-item>
            </el-form>
    
        </el-card>
      </div>
    </div>
</template>

<script>
export default {
  data () {
    return {

        fileList: [
        
        ],
        vacationForm: {
          userId: window.sessionStorage.getItem("userId"),
          userName:'',
          department: '',
          type:'',
          maternityType:"",
          marriageType:"",
          maternityNum:"",
          startDate: '',
          startTime: '',
          endDate:'',
          endTime:'',
          reason:''
        
        },
         uploadURL:"http://localhost:8080/file/upload?userId="+window.sessionStorage.getItem("userId"),
         rules: {
            userId: [
            { required: true, message: '请输入员工号', trigger: 'blur' },
          ],
            userName: [
            { required: true, message: '请输入姓名', trigger: 'blur' }
          ],
            department: [
            { required: true, message: '请选择部门', trigger: 'change' },
          ],
            startDate: [
            {required: true, message: '请选择日期', trigger: 'change' }
          ],
            startTime: [
            {required: true, message: '请选择时间', trigger: 'change' }
          ],
           endDate: [
            {required: true, message: '请选择日期', trigger: 'change' }
          ],
            endTime: [
            {required: true, message: '请选择时间', trigger: 'change' }
          ],
          type: [
            { required: true, message: '请选择请假类型', trigger: 'change' },
          ],
          maternityType: [
            { required: true, message: '请选择产假类型', trigger: 'change' },
          ],
          marriageType: [
            { required: true, message: '请选择婚假类型', trigger: 'change' },
          ],
          maternityNum: [
            { required: true, message: '请填写多胞胎个数', trigger: 'blur' }
          ],
          reason: [
            { required: true, message: '请填写请假理由', trigger: 'blur' }
          ],
          
    }
      
    };

   

  },
  components: {},
  mounted () {},
  created(){
   
  },
  methods: {
        handleSuccess(response, file, fileList) {
          this.$message.success("上传成功");
        },
        handleError() {
          this.$message.error("上传失败,请重新上传图片!");
        },
       submitForm(){
          console.log(this.fileList);
          this.$refs.vacationFormRef.validate(async (valid) =>{
            if(!valid){
              return;
            }
            console.log(this.vacationForm)
            const{data:res} = await this.$http.post("/vacation", this.vacationForm);
            console.log(res);
            if(res.code === 1) {
              this.$message.error('假期余额不足')
            }
            else{
              this.$message.success(res.info);
            }
            this.$refs.vacationFormRef.resetFields();

        })
        
 
      },
      resetForm(){
          this.$refs.vacationFormRef.resetFields();
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm('删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
      }

  }
}
</script>

<style lang="less" scoped>
.box-card {
    // position:absolute;
    // left: 50%;
    // transform: translateX(-50%);
    width: 82%;
    margin-left:100px;
    margin-top:20px;
    // right: 20px;
  }

.upload-demo{
  margin-left:35px;
  margin-bottom:10px;
}
</style>