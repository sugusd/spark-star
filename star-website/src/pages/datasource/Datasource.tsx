import React from 'react';
import './Datasource.scss'
import {Link, LinkProps, Outlet, useMatch, useResolvedPath} from "react-router-dom";
import {Tabs, TabsProps} from "antd";
import Datalink from "./datalink/Datalink";
import Driver from "./driver/Driver";

function Datasource() {

    const items: TabsProps['items'] = [
        {
            key: '1',
            label: `数据源连接`,
            children: <Datalink/>,
        },
        {
            key: '2',
            label: `驱动仓库`,
            children: <Driver/>,
        }
    ];

    return <>
        <Tabs defaultActiveKey="1" items={items}/>
    </>;
}

export default Datasource;
