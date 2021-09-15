import React from "react";

export default function useFormValidation(initState){
    const [values,setValues] = React.useState(initState)
    
    function handleChange(event){
        setValues({
            ...values,
            [event.target.id]:event.target.value
        })
      };

    return {handleChange,values}
}