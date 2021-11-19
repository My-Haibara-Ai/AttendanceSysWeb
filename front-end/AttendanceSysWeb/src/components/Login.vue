<template>
  <div class="login_container">
      <div class="login_box">
            <!-- 头像区域 -->
            <div class="avatar_box">
                <img src="../assets/考勤.png" alt="">
            </div>
            <!-- 登录表单区域 -->
            <el-form ref="loginFormRef"label-width="0px" class="login_form" :model="LoginForm" :rules="LoginFormRules">
                <!-- 用户名 -->
                <el-form-item prop="userId">
                    <el-input prefix-icon="iconfont iconuser" v-model="LoginForm.userId"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="password">
                    <el-input  prefix-icon="iconfont iconi-pwd" v-model="LoginForm.password" type="password"></el-input>
                </el-form-item>
                <!-- 按钮区 -->
                <el-form-item class='btns'>
                    <el-button type="primary" @click="login">登录</el-button>
                    <el-button type="info" @click="reset">重置</el-button>
                </el-form-item>
              
            </el-form>    
      </div>
  </div>
</template>

<script>
export default {
  name: '',
  data () {
    return {

        LoginForm:{
            userId:'',
            password:'',
        },

        //表单验证规则对象
        LoginFormRules:{
            userId:[
                {required:true, message:"请输入员工号", trigger:"blur"},
            ],
            password:[
                {required:true, message:"请输入登录密码", trigger:"blur"},
                { min:5, max:15, message:"长度在6到15个字符中间", trigger:"blur"}
            ]

        },
        
      
    }
  },
  components: {},
  mounted () {},
  methods: {

      //重置表单
      reset(){
          this.$refs.loginFormRef.resetFields();
      },
      //登录预验证 valid是验证结果 判断验证通过/失败
        login(){
            this.$refs.loginFormRef.validate(async (valid) =>{
                if(!valid) return;
                //发送请求
                const {data:res}= await this.$http.post('/login/userLogin?username='+this.LoginForm.userId+"&password="+this.LoginForm.password);//操作返回的是await，可以使用async和await简化操作
                console.log(res);
                
                
                if(res.code==="200"){
                    this.$message.success(res.msg);
                    //  1.1 项目中除了登录之外的其他API接口，必须在登录之后才能访问
                    //  1.2 token只应当在当前网站打开期间生效，所以将token保存在sessionStorage中
                    window.sessionStorage.setItem('userId',this.LoginForm.userId);
                    window.sessionStorage.setItem('token',res.token);
                    //2. 通过编程式导航跳转到后台主页，路由地址是 /home
                    this.$router.push('/home')
                }else{
                    this.$message.error(res.message);
                }
                //1. 将登陆成功之后的token，保存到客户端的sessionStorage中
                
            });
        }
    }
};
</script>

<style lang="less" scoped>
.login_container {
    background:#c2dac8;
    height: 100%;
}

.login_box {
    width:450px;
    height:300px;
    background: #fff;
    border-radius: 6px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform:translate(-50%, -50%);
    .avatar_box{
        height: 156px;
        width: 156px;
        border-radius: 50%;
        position: absolute;
        left: 50%;
        transform:translate(-50%, -50%);
        box-shadow: 0 0 5px #ddd;
        padding: 8px;
        box-sizing: border-box;
        img {
            height: 100%;
            width: 100%;
            border-radius: 50%;
            background-color: #eee;
        }
       
    }

}

.btns {
   position: relative;
   right: -140px;
}

.login_form {
     position: absolute;
     bottom: 0px;
     width:100%;
     padding-left: 20px;
     padding-right: 20px;
    //  padding:0 20px;
     box-sizing: border-box;
}
</style>