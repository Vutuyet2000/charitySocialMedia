import cookies from 'react-cookies'

export default function getUser(store){
    const auth = store.getState() // nguyên đối tượng
    let user = auth
    if (cookies.load("user")!=null)
        user = cookies.load("user")
    return {user}
}