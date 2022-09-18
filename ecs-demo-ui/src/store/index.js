import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const actions = {}

const mutations = {

    SET_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem("token",token);
    },

    SET_USER(state, userInfo) {
        state.userInfo.token = userInfo.token;
        state.userInfo.id = userInfo.id;
        state.userInfo.name = userInfo.name;
        state.userInfo.isAdmin = userInfo.isAdmin;
        state.userInfo.account = userInfo.account;
    },

    REMOVE_TOKEN(state) {
        state.token = null;
        state.userInfo = {
            token: null,
            id: null,
            name: null,
            account: null,
            isAdmin: false
        }
        localStorage.removeItem("token");
    }
}

const state = {
    token: '',
    userInfo: {
        token: null,
        id: null,
        name: null,
        account: null,
        isAdmin: false
    }
}

const getters = {
    TOKEN(state) {
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