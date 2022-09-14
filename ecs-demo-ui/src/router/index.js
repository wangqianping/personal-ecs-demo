import Router from "vue-router";
import LoginForm from "@/components/LoginForm";
import indexForm from "@/components/IndexForm";


export default new Router({

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