export const reducer = (state=null, action) =>{
    if(action.type==="login")
        return action.payload //chứa thông tin user
    return state;
}