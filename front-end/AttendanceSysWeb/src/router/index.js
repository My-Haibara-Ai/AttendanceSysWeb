import Vue from 'vue'
import VueRouter from 'vue-router'
const Login = () => import('../components/Login.vue')
const Home = () => import('../components/Home.vue')
const SignInAndOut = () => import('../components/SignInAndOut.vue')
const VacationTable = () => import('../components/VacationApply/VacationTable.vue')
const CheckByGeneral = () => import('../components/VacationCheck/CheckByGeneral.vue')
const MyVacationTable = () => import('../components/VacationManage/MyVacationTable.vue')
const OthersVacationTable = () => import('../components/VacationManage/OthersVacationTable.vue')
const CheckByManager = () => import('../components/VacationCheck/CheckByManager.vue') 
const StaffManage = () => import('../components/Personnel/StaffManage.vue')
const CheckByPersonnel = () => import('../components/Personnel/CheckByPersonnel.vue')
const BaoBiao = () => import('../components/Personnel/BaoBiao.vue')
const MyInformation = () => import('../components/MyInformation.vue')
Vue.use(VueRouter)

const routes = [
  {path: '/', redirect: '/login'},
  {path: '/login', component: Login},
  {path: '/home',
   component: Home, 
   redirect: '/signInAndOut',
   children:[
    {path: '/signInAndOut', component: SignInAndOut},
    {path: '/vacationTable', component: VacationTable},
    {path: '/checkByManager', component: CheckByManager},
    {path: '/checkByGeneral', component: CheckByGeneral},
    {path: '/myVacationTable', component: MyVacationTable},
    {path: '/othersVacationTable', component: OthersVacationTable},
    {path: '/staffManage', component: StaffManage},
    {path: '/checkByPersonnel', component: CheckByPersonnel},
    {path: '/baoBiao', component: BaoBiao},
    {path: '/myInformation', component: MyInformation}

  ]},
]

const router = new VueRouter({
  routes
})

export default router
