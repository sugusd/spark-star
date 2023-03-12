import React, {useState} from 'react';
import {Outlet} from "react-router-dom";
import "./Datalink.scss"
import {Button, Input, Space, Table} from "antd";
import {ColumnsType} from "antd/es/table";
import {ChooseLinkModal} from "./addlink/ChooseLinkModal";

function Datalink() {
    interface DataType {
        key: React.Key;
        name: string;
        address: string;
    }

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
                <a>删除</a>
                <a>编辑</a>
                <a>检测</a>
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

    const [isModalVisible, setIsModalVisible] = useState(false);
    const handleOk = () => setIsModalVisible(true);
    const handleCancel = () => setIsModalVisible(false);

    return <>
        <div className={"datalink-main"}>
            <div className={"datalink-opt-div"}>
                <Button onClick={() => handleOk()} className={"datalink-add-datasource-btn"}>添加数据源</Button>
                <Button className={"datalink-search-btn"}>搜索</Button>
                <Input className={"datalink-search-input"} placeholder={"名称/类型/连接信息/备注/创建人"}/>
            </div>
            <div className={"datalink-table-div"}>
                <Table columns={columns} dataSource={data} scroll={{x: 1500, y: 430}}/>
            </div>
        </div>
        <ChooseLinkModal handleCancel={handleCancel} handleOk={handleOk} isModalVisible={isModalVisible}/>
    </>;
}

export default Datalink;
