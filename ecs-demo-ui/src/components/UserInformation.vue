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
          <el-row :gutter="10">
            <el-col :span="8">
                <el-row :span="12"><div id="profilePhoto" ></div></el-row>
                <el-row :span="12"><el-button type="primary" size="small">修改头像</el-button></el-row>
            </el-col>
            <el-col :span="6">
              <el-form-item label="名称" label-width="50px">
                <el-input v-model="currentUser.name"></el-input>
              </el-form-item>
              <el-form-item label="账号" label-width="50px">
                <el-input v-model="currentUser.account"></el-input>
              </el-form-item>
              <el-form-item label="邮箱" label-width="50px">
                <el-input v-model="currentUser.email"></el-input>
              </el-form-item>
              <el-form-item label="电话" label-width="50px">
                <el-input v-model="currentUser.phone"></el-input>
              </el-form-item>
              <el-form-item label="密码" label-width="50px">
                <el-input v-model="currentUser.password"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-main>
    </el-container>


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
      profilePhoto: ''
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

#profilePhoto{
  width: 200px;
  height: 200px;
  border-radius: 50%;
  background-color: black;
}
</style>