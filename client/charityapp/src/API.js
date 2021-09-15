import axios from 'axios'
import cookies from 'react-cookies'


export let endpoints ={
    'post':'/post/',
    'auction':'/post/create-auction/',
    'login':'/oauth/token/',
    'users':'/users/',
    'sign-up':'/sign-up',
    'current-user':'/users/current-user/'
}

export let AuthAPI = axios.create({
    baseURL:'http://127.0.0.1:8080/',
    headers:{
        'Authorization':`Bearer ${cookies.load('access_token')}`
    }
})

export default axios.create({
    baseURL: "http://127.0.0.1:8080/"
    
})