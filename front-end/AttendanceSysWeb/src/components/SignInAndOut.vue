<template>
  <div>
    <!-- 考勤信息 -->
    <div>
      <section class="data_section">
        <header class="section_title">考勤统计</header>
        <el-row
          :gutter="20"
          style="margin-bottom: 10px;"
        >
          <el-col :span="4">
            <div class="data_list today_head"><span class="data_num head">本月考勤</span></div>
          </el-col>
          <el-col :span="4">
            <div class="data_list"><span class="data_num">{{attendanceCount.workHour}}</span>出勤时长</div>
          </el-col>
          <el-col :span="4">
            <div class="data_list"><span class="data_num">{{attendanceCount.paid}}</span>带薪假期时长</div>
          </el-col>
          <el-col :span="4">
            <div class="data_list"><span class="data_num">{{attendanceCount.unpaid}}</span>不带薪假期时长</div>
          </el-col>
          <el-col :span="4">
            <div class="data_list"><span class="data_num">{{attendanceCount.valid}}</span>工资小时数</div>
          </el-col>
        </el-row>
      </section>
    </div>
    <!-- 地图导入 -->
    <div id="container"></div>
    <!-- 签到签退 -->
    <div id='btns'>
      <el-button
        type="success"
        round
        v-on:click="sign('签到')"
      >签到</el-button>
      <el-button
        type="warning"
        round
        v-on:click="sign('签退')"
      >签退</el-button>
    </div>
  </div>
</template>

<script>
import AMap from 'AMap' // 引入高德地图
export default {

  name: '',
  data () {
    return {
      attendanceCount: {
          workHour: '160',
          paid: '10',
          unpaid: '5',
          valid: '170',
      },
      


    };
  },
  components: {},
  created () {
    // this.initData();

  },
  mounted () {
    this.init();
    // this.getBaoBiao();
  },
  methods: {
    async signIn(){
      let userId = window.sessionStorage.getItem("userId");
      const{data: res} = await this.$http.post("/attendance/signin?userId="+userId);
      if(res.code === 0){
        this.$message.success(this.info);
      }
      else if(res.code === 1){
        this.$message.error(this.info);
      }
      else if(res.code === 2){
        this.$message.error(this.info);
      }
      else{
        this.$message.error(this.info);
      }
    },

    async signOut(){
      let userId = window.sessionStorage.getItem("userId");
      const{data: res} = await this.$http.post("/attendance/signout?userId="+userId);
      if(res.code == 0){
        this.$message.success(this.info);
      }
      else if(res.code === 1){
        this.$message.error(this.info);
      }
      else {
        this.$message.error(this.info);
      }
    },

    init () {
      let map = new AMap.Map('container', {
        resizeEnable: true,
        // center: [116.397428, 39.90923],
        zoom: 10
      })
      AMap.plugin('AMap.Geolocation', function () {
        var geolocation = new AMap.Geolocation({
          // 是否使用高精度定位，默认：true
          enableHighAccuracy: true,
          // 设置定位超时时间，默认：无穷大
          timeout: 10000,
          // 定位按钮的停靠位置的偏移量，默认：Pixel(10, 20)
          buttonOffset: new AMap.Pixel(10, 20),
          //  定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
          zoomToAccuracy: true,
          //  定位按钮的排放位置,  RB表示右下
          buttonPosition: 'RB'
        })

        geolocation.getCurrentPosition()
        AMap.event.addListener(geolocation, 'complete', onComplete)
        AMap.event.addListener(geolocation, 'error', onError)

        function onComplete (data) {
          // data是具体的定位信息
        }

        function onError (data) {
          // 定位出错
        }
      })
    },

    sign (r) {
      let map = new AMap.Map('container', {
        resizeEnable: true,
        center: [117.3154444444, 38.99970011111111],  //地图中心点，选择北洋园校区中心经纬度
        zoom: 15.5
      })

      //初始化当前定位点的经纬度
      let curLng = 0;
      let curLat = 0;

      AMap.plugin('AMap.Geolocation', function () {
        var geolocation = new AMap.Geolocation({
          // 是否使用高精度定位，默认：true
          enableHighAccuracy: true,
          // 设置定位超时时间，默认：无穷大
          timeout: 10000,
          // 定位按钮的停靠位置的偏移量，默认：Pixel(10, 20)
          buttonOffset: new AMap.Pixel(10, 20),
          //  定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
          zoomToAccuracy: true,
          //  定位按钮的排放位置,  RB表示右下
          buttonPosition: 'RB'
        })

        geolocation.getCurrentPosition()
        AMap.event.addListener(geolocation, 'complete', onComplete)
        AMap.event.addListener(geolocation, 'error', onError)

        function onComplete (data) {
          // data是具体的定位信息，更新定位点经纬度
          curLng = data.position.getLng();
          curLat = data.position.getLat();

          //描绘当前位置所在点
          let curPointMarker = new AMap.Marker({
            position: [curLng, curLat],
            offset: new AMap.Pixel(-21, -21),//偏移量，默认以marker左上角位置为基准点
            map: map
          });

          //描绘签到范围
          let circle = new AMap.Circle({
            map: map,
            center: [117.3202444444, 38.99550011111111],
            radius: 500,
            fillColor: '#1772b4',
            fillOpacity: 0.2
          });

          let text;
          if (r === '签到') {
            text = "签到";
            this.$message.success(text + "成功");
            // alert(text + "成功");
          }
          else if (r === '签退') {
            text = "签退";
            // alert(text + "成功");
            this.$message.success(text + "成功");
          }

          if (circle.contains([curLng, curLat])) {
            if(text==="签到"){
              this.signIn();
            }
            else{
              this.signOut();
            }
          }         
          else {
            alert("不在范围内，" + text + "失败");
          }
        }

        function onError (data) {
          // 定位出错
          // alert('失败原因排查信息:' + data.message)
          // alert('请重试或退出')
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
#btns {
  margin-left: 450px;
}
#container {
  width: 1000px;
  height: 500px;
  margin-left: 60px;
  margin-bottom: 20px;
}
.data_section {
  padding: 20px;
  margin-bottom: 20px;
  .section_title {
    text-align: center;
    font-size: 30px;
    margin-bottom: 10px;
  }
  .data_list {
    text-align: center;
    font-size: 14px;
    color: #666;
    border-radius: 6px;
    background: #e5e9f2;
    .data_num {
      color: #333;
      font-size: 26px;
    }
    .head {
      border-radius: 6px;
      font-size: 22px;
      padding: 4px 0;
      color: #fff;
      display: inline-block;
    }
  }
  .today_head {
    background: #ff9800;
  }
  .all_head {
    background: #20a0ff;
  }
}
</style>