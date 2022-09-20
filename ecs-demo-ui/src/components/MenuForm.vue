<template>
  <div>
    <div id="logo">
      <a  href="/index" class="sidebar-logo-link router-link-active">
        <img  src="../assets/logo.png" class="sidebar-logo">
      </a>
    </div>
    <div>
      <el-menu class="el-menu-vertical-demo"  text-color="rgb(191, 203, 217)" active-text-color="rgb(64, 158, 255)"
               background-color="rgb(48, 65, 86)">
        <el-menu-item index="1-1"><i class="el-icon-message"></i>首页</el-menu-item>
        <el-submenu index="2">
          <template slot="title"><i class="el-icon-message"></i>系统管理</template>
          <el-menu-item-group>
            <el-menu-item index="2-1" @click="getAllUser">用户管理</el-menu-item>
            <el-menu-item index="2-2">菜单管理</el-menu-item>
            <el-menu-item index="2-3">权限管理</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MenuForm',
  methods: {
    getAllUser(){
      this.$axios.get("/user/listUser")
          .then(
              rsp => {
                const data = rsp.data;
                if (data.code == 200) {
                  const result = data.data;
                  console.log(result)
                } else {
                  this.$message.error({
                    message: data.message,
                    center: true
                  })
                }
              })
          .catch(
              error => {
                this.$message.error({
                  message: error.message,
                  center: true
                })
              })
    }
  }
}
</script>

<style>



#logo {
  background-color: rgb(48, 65, 86);
  text-align: center;
  height: 60px;
  width: 200px;
}
.el-menu-vertical-demo {
  width: 200px;
}


</style>