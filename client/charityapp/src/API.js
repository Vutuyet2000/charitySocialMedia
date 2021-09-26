import axios from 'axios'
import cookies from 'react-cookies'


export let endpoints ={
    'post':'/post',
    'auction':'/post/create-post-auction',
    'login':'/oauth/token',
    'users':'/users/',
    'sign-up':'/sign-up',
    'current-user':'/users/current-user',
    'comments':(post_id)=>`/comments/?post-id=${post_id}`,
    'likes':(post_id)=>`/likes/?post-id=${post_id}`,
    'winner':(post_id,userID,cost)=>`/post/${post_id}/choose-winner?cost=${cost}&winner-id=${userID}`,
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