import axios, {AxiosRequestConfig} from "axios";
import {useUserStore} from "@/store/UserStore";

// const userStore = useUserStore();
//
// const HTTP = axios.create({
//     baseURL: process.env.VUE_APP_BASEURL,
//     headers: {
//         'Content-Type': 'application/json',
//         'Authorization': `Bearer ${userStore.getToken}`
//     }
// });
// export { HTTP };

export const HTTP = axios.create({
    baseURL: process.env.VUE_APP_BASEURL
});

HTTP.interceptors.request.use((config:AxiosRequestConfig) => {
    const userStore = useUserStore();
    // @ts-ignore
    config.headers.Authorization = `Bearer ${userStore.getToken}`;
    // @ts-ignore
    config.headers.Accept = "application/json";
    return config
})
