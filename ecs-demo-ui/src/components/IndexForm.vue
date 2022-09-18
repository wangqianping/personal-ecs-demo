<template>
  <div>
    <el-container>
      <el-aside width="180px"> 
        <div id="logo">
          logo 区
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
                <span class="el-dropdown-link">
                  <el-avatar>admin</el-avatar>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item>个人信息</el-dropdown-item>
                  <el-dropdown-item>修改密码</el-dropdown-item>
                  <el-dropdown-item>推出登入</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
          </div>  
        </el-header>
          
        <el-main>内容区</el-main>
      
      </el-container>

  </el-container>
</div>
</template>

<script>
export default {
  name: 'IndexForm',
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
    text-align: center;
    padding-top: 10px;
  }
  
  #logo {
   text-align: center;
  }

  .el-aside {
    background-color: rgb(48, 65, 86);
    color: white;
    text-align: left;
    line-height: 50px;
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
