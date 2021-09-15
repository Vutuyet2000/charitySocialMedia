import { createRef } from 'react';
import { useState, useEffect, useRef } from 'react'
import { Form, Button, Grid, Header, Segment, Message, Checkbox } from 'semantic-ui-react'
import API, { endpoints } from '../API'
import useFormValidation from '../useFormValidation';

const INIT_STATE = {
    first_name: "",
    last_name: "",
    email: "",
    username: "",
    password: "",
    confirm_password: ""
}

export default function RegisterForm() {

  const {handleChange,values } = useFormValidation(INIT_STATE)
  const [picture, setPicture] = useState(null);

  function handleFileUpload(e) {
    const file = e.target.files[0];
    setPicture(file)  
  }
  useEffect(() => {
    document.title = "Sign up"

  })

  function handleSubmit(event) {
    if (values.password === values.confirm_password) {
      const formData = new FormData()
      for (let k in values) {

        if (k !== 'confirm_password') { 
          formData.append(k, values[k])
        }

      }
      formData.append('avatar',picture)
      API.post(endpoints['sign-up'], formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
          
        }
      }).then(res => {
        console.log(res)
      }).catch(err => {
        console.error(err)
      })
      // Chặn trang tự reload theo mặc định để hàm async trên kia chạy xong
      event.preventDefault();

    }
  }

  return (

    <>

      <Grid textAlign='center' style={{ height: '100vh' }} verticalAlign='middle'>
        <Grid.Column style={{ maxWidth: 450 }}>
          <Header as='h2' color='teal' textAlign='center'>
            Sign up
          </Header>
          <Form size='large' onSubmit={handleSubmit}>
            <Segment stacked>
              <FormInput id="first_name" placeholder="First Name" value={values.first_name}
                type="text" 
                change={handleChange} />
              <FormInput id="last_name" placeholder="Last Name" name={values.last_name}
                type="text" value={values.last_name} 
                change={handleChange} />
              <FormInput id="email" placeholder="Email" type="email" value={values.email}
                change={handleChange} />
              <FormInput id="username" placeholder="Username" 
                type="text" value={values.username}
                change={handleChange} />
              <FormInput id="password" placeholder="Password" 
                type="password" value={values.password}
                change={handleChange} />
              <FormInput id="confirm_password" placeholder="Confirm Password"
                value={values.confirm_password} 
                type="password" change={handleChange} />
              <Form.Input type="file" onChange={handleFileUpload}  id="avatar" />
              <Form.Field>
                <Checkbox required label='I agree to the Terms and Conditions' />
              </Form.Field>
              <Button color='teal' type="submit" fluid size='large'>
                Sign up
              </Button>
            </Segment>
          </Form>
          <Message>
            Have an account? <a href='/sign-in'>Sign in</a>
          </Message>
        </Grid.Column>
      </Grid>
    </>
  );
}

function FormInput(props) {
  return (
    <>
      <Form.Input
        id={props.id}
        fluid
        required
        type={props.type}
        placeholder={props.placeholder}
        name={props.name}
        value={props.value}
        onChange={props.change}
      />
    </>

  )
} 