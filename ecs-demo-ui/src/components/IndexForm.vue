<template>
  <div>
    <el-container>
      <el-aside width="180px"> 
        <div id="logo">
          <a  href="/index" class="sidebar-logo-link router-link-active">
            <img  src="../assets/logo.png" class="sidebar-logo">
          </a>
        </div> 
        <el-menu class="el-menu-vertical-demo" text-color="rgb(191, 203, 217)" active-text-color="rgb(64, 158, 255)"
                  background-color="rgb(48, 65, 86)">
          <el-menu-item index="1-1"><i class="el-icon-message"></i>首页</el-menu-item>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-message"></i>系统管理</template>
            <el-menu-item-group>
              <el-menu-item index="2-1">用户管理</el-menu-item>
              <el-menu-item index="2-2">菜单管理</el-menu-item>
              <el-menu-item index="2-3">权限管理</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>
      
      <el-container>
        <el-header>
          <div id="touxiang">
              <el-dropdown>
                <span>
                      <el-row :gutter="45">     
                        <el-col :span="6">
                          <div class="grid-content img">
                            <el-avatar class="el-dropdown-link" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
                          </div>
                        </el-col>
                    </el-row>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item>个人信息</el-dropdown-item>
                  <el-dropdown-item>修改密码</el-dropdown-item>
                  <el-dropdown-item @click.native="logout">退出登入</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
          </div>  
        </el-header>
          
        <el-main> 
         
        </el-main>
      
      </el-container>

  </el-container>
</div>
</template>

<script>

export default {
  name: 'IndexForm',
  methods: {
    logout(){
      localStorage.removeItem("token")
      this.$store.commit("REMOVE_TOKEN")
      this.$router.push("/")
    }
  }
}
</script>

<style scoped>

.el-header {
    background-color: white;
    color: #333;
    text-align: center;
    line-height: 60px;
    border-bottom: 0.1px rgb(80, 72, 72) solid;
  }

  #touxiang {
    float: right;
    padding-top: 10px;
  }

  .el-dropdown-link{
    background-color: rgba(64, 255, 236, 0.556);
  }
  
  #logo {
   background-color: rgb(48, 65, 86);
   text-align: center;
   height: 60px;
  }

  .el-aside {
    background-color: rgb(48, 65, 86);
    color: white;
    text-align: left;
    line-height: 10px;
    height: 100%;
    position: fixed;
  }
  
  .el-menu-vertical-demo {
    width: 180px;
  }

  .el-main {
    background-color: white;
    color: #333;
    text-align: center;
    line-height: 160px;
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }
  
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }


</style>
