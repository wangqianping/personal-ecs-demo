<template>
  <div class="login">
    <el-form :model="user" class="demo-form-inline" label-width="200px">
      <el-form-item label="账号">
        <el-input v-model="user.username" placeholder="Please input account"/>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="user.password" type="password" placeholder="Please input password"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login">登入</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'LoginForm',
  data() {
    return {
      user: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    login() {
      // console.log(this.user)
      this.$axios.get("/user/listUser")
          .then(
              rsp => {
                console.log(rsp.data)
              })
          .catch(
              error => {
                console.log(error)
              })
    }

  }
}
</script>

<style scoped>
.login {
  width: 500px;
  height: 200px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
</style>