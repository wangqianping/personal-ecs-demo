import Router from "vue-router";
import LoginForm from "@/components/LoginForm";
import indexForm from "@/components/IndexForm";
import store from "@/store";


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

router.beforeEach((to,from,next) =>{
    if(store.getters.TOKEN === '' && to.path!="/"){
        next('/')
    }else{
        next()
    }
})

export default router