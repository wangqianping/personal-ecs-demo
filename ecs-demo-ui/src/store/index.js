import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const actions = {

}

const mutations = {
    
    SET_TOKEN(state,token){
          state.token = token;
          localStorage.setItem('token',token)  
    },
    
    SET_USER(state,userInfo){
        state.userInfo.token = userInfo.token;
        state.userInfo.userId = userInfo.userId;
        state.userInfo.username = userInfo.username;
        state.userInfo.account = userInfo.account; 
    },

    REMOVE_TOKEN(state){
         state.token = '';
         state.userInfo = {
            token:'',
            userId:'',
            username:'',
            account:'' 
         }
         localStorage.setItem('token')
    }
}

const state = {
    token:'',
    userInfo:{
        token:'',
        userId:'',
        username:'',
        account:'' 
    }
}

const getters = {
    TOKEN(state){
        return state.token;
    }
}

//创建并暴露store
export default new Vuex.Store({
    actions,
    mutations,
    state,
    getters
})