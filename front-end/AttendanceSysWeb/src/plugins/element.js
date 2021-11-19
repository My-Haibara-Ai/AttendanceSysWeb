import Vue from 'vue'
import { 
    Button,Form,FormItem,Input,Message,
    Container,Header,Aside,Main,
    Menu,Submenu,MenuItem,
    Card,Row,Col,
    Select,Option,
    DatePicker,TimePicker,
    Upload,
    MessageBox,
    TableColumn, Table,
    Pagination,
    BreadcrumbItem, Breadcrumb,
    Tooltip,
    Dialog,
} from 'element-ui'

Vue.use(Button)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)
Vue.use(Container)
Vue.use(Header)
Vue.use(Aside)
Vue.use(Main)
Vue.use(Menu)
Vue.use(Submenu)
Vue.use(MenuItem)
Vue.use(Card)
Vue.use(Col)
Vue.use(Row)
Vue.use(Select)
Vue.use(Option)
Vue.use(DatePicker)
Vue.use(TimePicker)
Vue.use(Upload)
Vue.use(TableColumn)
Vue.use(Table)
Vue.use(Pagination)
Vue.use(BreadcrumbItem)
Vue.use(Breadcrumb)
Vue.use(Tooltip)
Vue.use(Dialog)


//将弹框组件挂载到vue的原型上，使得每个组件都可以通过this.$message访问
Vue.prototype.$message = Message;
Vue.prototype.$confirm = MessageBox.confirm;