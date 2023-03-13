import React, {useState} from 'react';
import {Outlet} from "react-router-dom";
import "./Datalink.scss"
import {Button, Input, Space, Table} from "antd";
import {ColumnsType} from "antd/es/table";
import {ChooseLinkModal} from "./addlink/ChooseLinkModal";

interface DataType {
    key: React.Key;
    name: string;
    address: string;
}

function Datalink() {

    const [isModalVisible, setIsModalVisible] = useState(false);
    const handleOk = () => setIsModalVisible(true);
    const handleCancel = () => setIsModalVisible(false);

    const columns: ColumnsType<DataType> = [
        {
            title: '序号',
            width: 50,
            dataIndex: 'name',
            key: 'name',
            fixed: 'left',
            align: 'center'
        },
        {
            title: '名称',
            dataIndex: 'address',
            key: '1',
            fixed: 'left',
            width: 60,
        },
        {
            title: '类型',
            dataIndex: 'address',
            key: '2',
            width: 40,
        },
        {
            title: '连接信息',
            dataIndex: 'address',
            key: '3',
            width: 150,
        },
        {
            title: '备注',
            dataIndex: 'address',
            key: '4',
            width: 100,
        },
        {
            title: '创建人',
            dataIndex: 'address',
            key: '5',
            width: 60,
        },
        {
            title: '状态',
            dataIndex: 'address',
            key: '6',
            width: 50,
        },
        {
            title: '操作',
            key: 'operation',
            fixed: 'right',
            width: 90,
            render: (_: any, record: DataType) => <Space size="middle">
                <Button className={"text-btn"}>删除</Button>
                <Button className={"text-btn"}>编辑</Button>
                <Button className={"text-btn"}>检测</Button>
            </Space>,
        },
    ];

    const data: DataType[] = [];
    for (let i = 0; i < 100; i++) {
        data.push({
            key: i,
            name: `${i + 1}`,
            address: `London Park no. ${i}`,
        });
    }

    return <>
        <Space>
            <Button type="primary" onClick={() => handleOk()}>添加数据源</Button>
            <Input placeholder={"名称/类型/连接信息/备注/创建人"}/>
            <Button type="primary" className={"datalink-search-btn"}>搜索</Button>
        </Space>
        <Table columns={columns} dataSource={data} scroll={{x: 1800, y: 400}}/>
        <ChooseLinkModal handleCancel={handleCancel} handleOk={handleOk} isModalVisible={isModalVisible}/>
    </>;
}

export default Datalink;
