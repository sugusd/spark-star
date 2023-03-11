import React from 'react';
import './Login.scss'
import {Controller, useForm} from "react-hook-form";
import {Button, Input, message} from "antd";
import axios from "axios";

function Login() {
    const login = (data) => {
        axios({
            method: 'post',
            url: '/login',
            data: {
                account: data.account,
                password: data.password
            }
        }).then(function (response) {
            message.success("登录成功").then(() => {});
        }).catch(function (error) {
            message.error(error.response.data.message).then(() => {});
            reset()
        })
    }

    const {control, handleSubmit, reset} = useForm({});

    return <div className={"login-div"}>

        <div className={"logo-img"} onClick={() => {
            window.open("https://zhiqingyun.isxcode.com")
        }}>至轻云
        </div>
        <form onSubmit={handleSubmit(login)}>
            <Controller
                name="name"
                control={control}
                render={({field}) =>
                    <input className={"login-input"} {...field} placeholder={"账号/手机号/邮箱"}/>}
            />
            <Controller
                name="host"
                control={control}
                render={({field}) =>
                    <input type={"password"} className={"login-input"} {...field} placeholder={"密码"}/>}
            />
            <button className={"login-btn"} type="submit">登录</button>
        </form>
        <div className={"welcome-font"}>欢迎使用至轻云大数据平台</div>
    </div>;
}

export default Login;
