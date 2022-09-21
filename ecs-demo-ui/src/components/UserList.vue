<template>
    <el-container>
        <el-aside width="200px"><MenuForm/></el-aside>
        <el-container>
            <el-header height="60px"><HeaderForm/></el-header>
            <el-main>
                <div id="queryForm">
                    <el-row :gutter="50">
                        <el-col :span="6">
                            <el-input v-model="queryParam.account" placeholder="请输入账号"></el-input>
                        </el-col>
                        <el-col :span="6">
                            <el-input v-model="queryParam.name" placeholder="请输入姓名"></el-input>
                        </el-col>
                        <el-col :span="1">
                            <el-button
                                size="large"
                                type="primary"
                                @click="handleQuery()">查询</el-button>
                        </el-col>
                        <el-col :span="1">
                          <el-button
                              size="large"
                              type="primary"
                              @click="handleCreate">创建</el-button>
                        </el-col>
                    </el-row>
                </div>
                <div id="dataTable">
                    <el-row >
                        <el-table :data="tableData">
                            <el-table-column
                                prop="account"
                                label="账号"
                                align="center">
                            </el-table-column>
                            <el-table-column
                                prop="name"
                                label="姓名"
                                align="center">
                            </el-table-column>
                            <el-table-column
                                prop="phone"
                                label="电话"
                                align="center">
                            </el-table-column>
                            <el-table-column
                                prop="email"
                                label="邮箱"
                                align="center">
                            </el-table-column>
                            <el-table-column label="操作"  align="center">
                                <template slot-scope="scope">
                                    <el-button
                                    size="mini"
                                    type="primary"
                                    @click="handleDetail(scope.$index, scope.row)">详情</el-button>
                                    <el-button
                                    size="mini"
                                    type="primary"
                                    @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                                    <el-button
                                    size="mini"
                                    type="danger"
                                    @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-row>
                </div>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>

import MenuForm from "./MenuForm.vue";
import HeaderForm from "./HeaderForm.vue";

 export default {
    name: "UserList",
    components: { MenuForm, HeaderForm },
    data(){
        return {
            tableData: [],
            queryParam: {
                account: null,
                name: null
            }
        }    
    },
    created(){
        this.getAllUser()    
    },
    methods: {
        getAllUser() {
            this.$axios.get("/user/listUser")
                .then(rsp => {
                const data = rsp.data;
                if (data.code == 200) {
                    const result = data.data;
                    this.tableData = result;
                }
                else {
                    this.$message.error({
                        message: data.message,
                        center: true
                    });
                }
            })
                .catch(error => {
                this.$message.error({
                    message: error.message,
                    center: true
                });
            });
        },
        handleQuery(){
          this.$message.error("这个正在加班做！")
        },
        handleDetail(){
          this.$message.error("这个没做！")
        },
        handleEdit(){
          this.$message.error("这个也没做！")
        },
        handleDelete(){
          this.$message.error("这个还是没做！")
        },
        handleCreate(){
          this.$message.error("别点了，都没做！")
        }

    },
}
</script>

<style>

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

#queryForm {
    height: 70px;
    margin-top: 10px;
}

</style>