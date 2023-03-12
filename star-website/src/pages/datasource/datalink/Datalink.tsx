import React, {useState} from 'react';
import {Outlet} from "react-router-dom";
import "./Datalink.scss"
import {Space, Table} from "antd";
import {ColumnsType} from "antd/es/table";
import {ChooseLinkModel} from "./addlink/ChooseLinkModel";

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
            width: 140,
        },
        {
            title: '类型',
            dataIndex: 'address',
            key: '2',
            width: 100,
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
            width: 150,
        },
        {
            title: '创建人',
            dataIndex: 'address',
            key: '5',
            width: 150,
        },
        {
            title: '状态',
            dataIndex: 'address',
            key: '6',
            width: 150,
        },
        {
            title: '操作',
            key: 'operation',
            fixed: 'right',
            width: 140,
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

    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);

    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
        console.log('selectedRowKeys changed: ', newSelectedRowKeys);
        setSelectedRowKeys(newSelectedRowKeys);
    };

    const rowSelection = {
        selectedRowKeys,
        onChange: onSelectChange,
    };

    const [isModalVisible, setIsModalVisible] = useState(false);
    const handleOk = () => setIsModalVisible(true);
    const handleCancel = () => setIsModalVisible(false);

    return<>
        <div className={"datalink-main"}>
            <div className={"datalink-opt-div"}>
                <button onClick={()=>handleOk()} className={"datalink-add-datasource-btn"}>添加数据源</button>
                <div className={"datalink-search-div"}>
                    <input className={"datalink-search-input"} placeholder={"名称/类型/连接信息/备注/创建人"}/>
                    <button className={"datalink-search-btn"}>搜索</button>
                </div>
            </div>
            <div className={"datalink-table-div"}>
                <Table rowSelection={rowSelection} columns={columns} dataSource={data} scroll={{ x: 1500, y: 300 }} />
            </div>
        </div>
        <ChooseLinkModel  handleCancel={handleCancel} handleOk={handleOk} isModalVisible={isModalVisible}/>
    </>;
}

export default Datalink;
