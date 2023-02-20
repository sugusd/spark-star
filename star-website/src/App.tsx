import React from 'react';
import './App.css';
import {SubmitHandler, useForm} from "react-hook-form";
import {Button, Input} from "antd";
import axios from "axios";

function App() {

    const {register, handleSubmit, watch, formState: {errors}} = useForm();
    const onSubmit = data => {
        console.log(data)
        axios({
            method: 'post',
            url: 'http://localhost:30156/spark-star/execute',
            headers: {
                'star-key': 'star-key'
            },
            data: {
                sql: data.sql
            }
        }).then(function (response) {
            console.log(response.data)
        });
    };

    return (
        <div className="App">
            <form onSubmit={handleSubmit(onSubmit)}>
                <input {...register("sql")} />
                <Button htmlType="submit">提交</Button>
            </form>
        </div>
    );
}

export default App;
