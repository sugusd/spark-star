import React, {useEffect, useState} from 'react';
import './App.css';
import {Controller, useForm} from "react-hook-form";
import {Button, Input, message, Space, Table, TreeSelect} from "antd";
import axios from "axios";
import TextArea from "antd/es/input/TextArea";

function App() {

    const [serverData, setServerData] = useState([{}]);

    const [starData, setStarData] = useState('');

    const [applicationId, setApplicationId] = useState('');

    useEffect(() => getServers(), []);

    const addServer = (data) => {
        axios({
            method: 'post',
            url: '/addServer',
            data: {
                name: data.name,
                username: data.username,
                host: data.host,
                password: data.password,
                location: data.path,
                port: data.port
            }
        }).then(function (response) {
            message.success("新建成功");
            getServers()
            reset()
        }).catch(function (error) {
            message.error(error.response.data.message);
        })
    }

    const checkServer = (data) => {
        axios({
            method: 'get',
            url: '/checkStar',
            params: {
                serverId: data.id
            }
        }).then(function (response) {
            getServers()
            message.success("检查完成");
        }).catch(function (error) {
            message.error(error.response.data.message);
        })
    };

    const deleteServer = (data) => {
        axios({
            method: 'get',
            url: '/deleteServer',
            params: {
                serverId: data.id
            }
        }).then(function (response) {
            getServers()
        }).catch(function (error) {
            message.error(error.response.data.message);
        })
    };

    const installStar = (data) => {
        axios({
            method: 'get',
            url: '/installStar',
            params: {
                serverId: data.id
            }
        }).then(function (response) {
            message.success("安装成功");
            getServers()
        }).catch(function (error) {
            message.error(error.response.data.message);
        })
    };

    const getServers = () => {

        axios({
            method: 'get',
            url: '/queryServers',
        }).then(function (response) {
            response.data.map((server) => {
                server.value = server.id;
                server.title = server.name;
            })

            setServerData(response.data);
        }).catch(function (error) {
            message.error(error.response.data.message);
        })
    };

    const columns = [
        {
            title: '服务器名称',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'host',
            dataIndex: 'host',
            key: 'host',
        },
        {
            title: '用户名',
            dataIndex: 'username',
            key: 'username',
        },
        {
            title: '密码',
            dataIndex: 'password',
            key: 'password',
        },
        {
            title: '插件端口号',
            dataIndex: 'port',
            key: 'port',
        },
        {
            title: '插件安装路径',
            dataIndex: 'path',
            key: 'path',
        },
        {
            title: '状态',
            dataIndex: 'status',
            key: 'status'
        },
        {
            title: '操作',
            key: 'action',
            render: (_, record) => (
                <Space size="middle">
                    <a onClick={() => installStar(record)}>安装</a>
                    <a onClick={() => deleteServer(record)}>删除</a>
                    <a onClick={() => checkServer(record)}>检查</a>
                </Space>
            ),
        },
    ];

    const [value, setValue] = useState<string>();

    const onChange = (newValue: string) => {
        setValue(newValue);
    };

    const {control, handleSubmit, reset} = useForm({});

    const onSubmit = (data) => {

        axios({
            method: 'post',
            url: '/executeSparkSql',
            data: {
                sparkSql: data.sparkSql,
                serverId: value
            }
        }).then(function (response) {
            setApplicationId(response.data.applicationId);
            setStarData(JSON.stringify(response.data));
            message.success("提交成功");
        }).catch(function (error) {
            message.error(error.response.data.message);
        });
    };

    const getJobStatus = () => {
        axios({
            method: 'post',
            url: '/getJobStatus',
            data: {
                serverId: value,
                applicationId: applicationId
            }
        }).then(function (response) {
            setStarData(JSON.stringify(response.data));
            message.success("获取状态成功");
        }).catch(function (error) {
            message.error(error.response.data.message);
        })
    };

    const getJobLog = () => {
        axios({
            method: 'post',
            url: '/getJobLog',
            data: {
                serverId: value,
                applicationId: applicationId
            }
        }).then(function (response) {
            setStarData(JSON.stringify(response.data));
            message.success("获取日志成功");
        }).catch(function (error) {
            message.error(error.response.data.message);
        })
    };

    const getData = () => {
        axios({
            method: 'post',
            url: '/getData',
            data: {
                serverId: value,
                applicationId: applicationId
            }
        }).then(function (response) {
            setStarData(JSON.stringify(response.data));
            message.success("获取数据成功");
        }).catch(function (error) {
            message.error(error.response.data.message);
        })
    };

    const stopJob = () => {
        axios({
            method: 'post',
            url: '/stopJob',
            data: {
                serverId: value,
                applicationId: applicationId
            }
        }).then(function (response) {
            setStarData(JSON.stringify(response.data));
            message.success("中止作业成功");
        }).catch(function (error) {
            message.error(error.response.data.message);
        })
    };

    return (
        <div className="App">

            <div className={"Title"}>
                Spark-star
            </div>

            <div className={"Input-Server"}>
                <form onSubmit={handleSubmit(addServer)}>

                    <Controller
                        name="name"
                        control={control}
                        render={({field}) =>
                            <Input style={{"width": "10%", marginLeft: '15px'}}{...field} placeholder={"服务器名称"}/>}
                    />
                    <Controller
                        name="host"
                        control={control}
                        render={({field}) =>
                            <Input style={{"width": "10%", marginLeft: '15px'}}{...field} placeholder={"服务器ip"}/>}
                    />
                    <Controller
                        name="username"
                        control={control}
                        render={({field}) =>
                            <Input style={{"width": "10%", marginLeft: '15px'}}{...field} placeholder={"用户名"}/>}
                    />
                    <Controller
                        name="password"
                        control={control}
                        render={({field}) =>
                            <Input style={{"width": "10%", marginLeft: '15px'}}{...field} placeholder={"密码"}/>}
                    />
                    <Controller
                        name="port"
                        control={control}
                        render={({field}) =>
                            <Input style={{"width": "10%", marginLeft: '15px'}}{...field} placeholder={"插件端口号"}/>}
                    />
                    <Controller
                        name="path"
                        control={control}
                        render={({field}) =>
                            <Input style={{"width": "10%", marginLeft: '15px'}}{...field}
                                   placeholder={"插件安装路径"}/>}
                    />
                    <Button style={{marginLeft: '15px'}} htmlType="submit">添加服务器</Button>
                </form>
            </div>

            <div className={"Server-Table"}>
                <Table pagination={false} style={{"width": "60%", "margin": "auto"}} columns={columns}
                       dataSource={serverData}/>
            </div>

            <div className={"Work"}>
                <form onSubmit={handleSubmit(onSubmit)}>

                    <TreeSelect
                        style={{width: '30%'}}
                        showSearch
                        value={value}
                        dropdownStyle={{maxHeight: 400, overflow: 'auto'}}
                        placeholder="选择服务器"
                        allowClear
                        treeDefaultExpandAll
                        onChange={onChange}
                        treeData={serverData}
                    />

                    <Button style={{marginLeft: '15px'}} htmlType="submit">运行</Button>

                    <Button style={{marginLeft: '15px'}} onClick={() => getJobStatus()}>任务进程</Button>

                    <Button style={{marginLeft: '15px'}} onClick={() => getJobLog()}>作业日志</Button>

                    <Button style={{marginLeft: '15px'}} onClick={() => getData()}>数据查看</Button>

                    <Button style={{marginLeft: '15px'}} onClick={() => stopJob()}>中止作业</Button>

                    <Controller
                        name="sparkSql"
                        control={control}
                        render={({field}) =>
                            <TextArea {...field} style={{width: '70%', marginTop: '15px', height: '400px'}}></TextArea>}
                    />

                </form>
            </div>

            <div className={"Result"}>
                运行结果: <br/>
                {starData}
            </div>

        </div>
    );
}

export default App;
