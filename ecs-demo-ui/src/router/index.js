import Router from "vue-router";
import LoginForm from "@/components/LoginForm";
import indexForm from "@/components/IndexForm";
// import store from "@/store";


const router = new Router({

        routes: [
            {
                path: '/',
                component: LoginForm
            },
            {
                path: '/index',
                component: indexForm
            },
        ]
    }
)

// router.beforeEach((to,from,next) =>{
//     console.log(to,from,next)
//     if(to.path == '/' && store.getters.token != '' ){
//         next('/index')
//     }
//     if(store.getters.token == ''){
//         next('/')
//     }
// })

export default router