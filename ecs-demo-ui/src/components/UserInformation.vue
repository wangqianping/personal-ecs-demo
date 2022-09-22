<template>
  <el-container>
    <el-aside width="200px">
      <MenuForm/>
    </el-aside>
    <el-container>
      <el-header height="60px">
        <HeaderForm/>
      </el-header>
      <el-main>
        <el-form :model="currentUser" class="demo-form-inline">
          <el-row :gutter="100">
            <el-col :span="10">
                <el-image style="height: 150px;width: 150px;border-radius: 50%" :src="currentUser.profilePhoto"/>
              <el-upload
                class="upload-demo"
                action=""
                :on-exceed="handleExceed"
                :file-list="currentUser.profilePhoto">
                <el-button style="margin-top:15px" size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">上传文件且不超过500kb</div>
              </el-upload>
            </el-col>
            <el-col :span="14">
              <el-form-item label="名称" label-width="50px">
                <el-input v-model="currentUser.name"></el-input>
              </el-form-item>
              <el-form-item label="账号" label-width="50px">
                <el-input v-model="currentUser.account" disabled></el-input>
              </el-form-item>
              <el-form-item label="邮箱" label-width="50px">
                <el-input v-model="currentUser.email"></el-input>
              </el-form-item>
              <el-form-item label="电话" label-width="50px">
                <el-input v-model="currentUser.phone"></el-input>
              </el-form-item>
              <el-form-item label="密码" label-width="50px">
                <el-input type="password" v-model="currentUser.password" disabled>
                  <el-button slot="append" type="primary" @click="dialogVisible = true">修改</el-button>
                </el-input>
              </el-form-item>
              <el-form-item label-width="50px">
                <el-button  type="primary" @click="updateUserInfo">保存</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-main>
    </el-container>
    
    <el-dialog
            title="修改密码"
            :visible.sync="dialogVisible"
            width="40%">
                <el-form :model="password">
                  <el-form-item label="原密码" label-width="70px">
                      <el-input type="password" v-model="password.oldWord"></el-input>
                  </el-form-item>
                  <el-form-item label="新密码" label-width="70px">
                      <el-input type="password" v-model="password.newWord"></el-input>
                  </el-form-item>
                  <el-form-item label="确认密码" label-width="70px">
                      <el-input type="password" v-model="password.confirmWord"></el-input>
                  </el-form-item>
                </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible=false">取 消</el-button>
                <el-button type="primary" @click="updatePassword">确 定</el-button>
            </span>
    </el-dialog>

  </el-container>
</template>

<script>

import MenuForm from "./MenuForm.vue";
import HeaderForm from "./HeaderForm.vue";

export default {
  name: "UserInformation",
  components: {MenuForm, HeaderForm},
  data() {
    return {
      currentUser: {
        id: null,
        account: '',
        name: '',
        phone: '',
        email: '',
        profilePhoto: '',
        password:''
      },
      profilePhoto: '',
      password:{
        oldWord: "",
        newWord: "",
        confirmWord: ""
      },
      dialogVisible: false
    }
  },
  created() {
    this.getUserDetail()
    this.profilePhoto = this.$store.getters.USER.profilePhoto;
  },
  methods: {

    getUserDetail() {
      this.$axios.get("/user/getUserById", {
        params: {
          id: this.$store.getters.USER.id
        }
      })
          .then(rsp => {
            const data = rsp.data;
            const result = data.data;
            this.currentUser = result;
          })
          .catch(error => {
            this.$message.error({
              message: error.message,
              center: true
            });
          });
    },

    updateUser() {
      this.$axios.post("/user/updateUser", this.currentUser)
          .then(rsp => {
            this.$message.success({
              message: rsp.data.message,
              center: true
            })
          })
          .catch(error => {
            this.$message.error({
              message: error.message,
              center: true
            });
          })
    },

    updatePassword(){
      alert("还没做!")
    },

    updateUserInfo(){
      alert("还没做!")
    }

  },
}
</script>

<style scoped>

.el-aside {
  background-color: rgb(48, 65, 86);
  color: white;
  text-align: left;
  line-height: 10px;
  height: 100%;
  position: fixed;
}

.el-main {
  margin-left: 200px;
  line-height: 10px;
}
.demo-form-inline {
  margin-top: 100px;
}

</style>