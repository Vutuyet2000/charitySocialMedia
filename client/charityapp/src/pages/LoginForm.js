import { Grid, Form, Header, Button, Message, Segment } from "semantic-ui-react"
import React, { useState, useEffect } from 'react'
import useFormValidation from "../useFormValidation"
import API, { AuthAPI, endpoints } from "../API"
import { Redirect } from "react-router-dom"
import { useDispatch } from 'react-redux';
import cookies from 'react-cookies';

const INIT_STATE = {
  username: "",
  password: ""
}

export default function LoginForm() {
  const { handleChange, values } = useFormValidation(INIT_STATE)
  const [isLogged, setLogged] = useState(false)
  const dispatch = useDispatch()

  useEffect(() => {
    document.title = "Sign in"
  })

  const login = async (e) =>{
    e.preventDefault()
    try{
      let res= await API.post(`${endpoints['login']}?grant_type=password&client_id=first-client&client_secret=noonewilleverguess&username=${values.username}&password=${values.password}`,
    {
    },
    {
      headers:{
        'Content-Type':'application/x-www-form-urlencoded',
        'Authorization':'Basic Zmlyc3QtY2xpZW50Om5vb25ld2lsbGV2ZXJndWVzcw=='
      }
    })
     
      cookies.save('access_token',res.data.access_token)
      let user = await AuthAPI.get(endpoints['current-user'],{
        headers:{
          'Authorization':`Bearer ${cookies.load('access_token')}`
        }
      })
 
      cookies.save("user",user.data)
      
        dispatch({
          "type":"login",
          "payload":user.data
        })
        setLogged(true)
      
    }
    catch (ex){
      console.log(ex);
    }
  }

  if(isLogged)
    return <Redirect to= "/home"/>
  else
    return (
      <Grid textAlign='center' style={{ height: '100vh' }} verticalAlign='middle'>
        <Grid.Column style={{ maxWidth: 450 }}>
          <Header as='h2' color='teal' textAlign='center'>
            Sign in
          </Header>
          <Form size='large' onSubmit={login}>
            <Segment stacked>
              <FormInput 
                id='username'
                icon='user' 
                iconPosition='left' 
                placeholder='Username' 
                type='text' 
                value={values.username}
                change={handleChange}
              />
              <FormInput 
                id='password'
                icon='lock' 
                iconPosition='left' 
                placeholder='Password' 
                type='password' 
                value={values.password}
                change={handleChange}
              />
              <Button color='teal' fluid size='large'>
                Sign in
              </Button>
            </Segment>
          </Form>
          <Message>
            New to us? <a href='/sign-up'>Sign Up</a>
          </Message>
        </Grid.Column>
      </Grid>
    );
}

function FormInput(props) {
  return (
    <>
      <Form.Input
        fluid
        required
        id={props.id}
        icon={props.icon}
        iconPosition={props.iconPosition}
        placeholder={props.placeholder}
        type={props.type}
        value={props.value}
        onChange={props.change}
      />
    </>
  )

}
