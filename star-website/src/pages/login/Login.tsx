import React from 'react';
import './Login.scss'
import {Controller, useForm} from "react-hook-form";
import {Button, Input, message} from "antd";
import axios from "axios";
import {useNavigate} from "react-router-dom";

function Login() {

    const navigate = useNavigate();

    const login = (data) => {
        axios({
            method: 'post',
            url: process.env.API_PREFIX_URL + '/login',
            data: {
                account: data.account,
                password: data.password
            }
        }).then(function (response) {
            message.success("登录成功").then(() => {});
            localStorage.setItem('Authorization', "true");
            navigate("/index");
        }).catch(function (error) {
            message.error(error.response.data.message).then(() => {});
            reset()
        })
    }

    const {reset, register, handleSubmit, watch, formState: {errors}} = useForm();

    return <div className={"login-div"}>

        <div className={"logo-img"} onClick={() => {
            window.open("https://zhiqingyun.isxcode.com")
        }}>至轻云
        </div>
        <form onSubmit={handleSubmit(login)}>

            <input className={"login-input"} {...register("account", {required: true})}
                   placeholder={"账号/手机号/邮箱"}/>
            {/*{errors.account && <span>This field is 1</span>}*/}

            <input type={"password"} className={"login-input"} {...register("password",{ required: true })} placeholder={"密码"}/>
            {/*{errors.password && <span>This field is 2</span>}*/}

            <button className={"login-btn"} type="submit">登录</button>
        </form>
        <div className={"welcome-font"}>欢迎使用至轻云大数据平台</div>
    </div>;
}

export default Login;
