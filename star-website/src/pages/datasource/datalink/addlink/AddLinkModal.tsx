import React, {ReactNode, useEffect, useState} from 'react';
import {Button, Form, Input, message, Modal, Tabs, TabsProps} from "antd";
import './AddLinkModal.scss'

export const AddLinkModal = (
    props: {
        isModalVisible: any,
        handleOk: any,
        handleCancel: any
    }
) => {

    const onChange = (key: string) => {
        console.log(key);
    };

    const items: TabsProps['items'] = [
        {
            key: '1',
            label: `基础`,
            children: <>

                <Form.Item label="Host:" name="username">
                    <Input/>
                </Form.Item>
                <Form.Item label="Port:" name="username">
                    <Input/>
                </Form.Item>
                <Form.Item label="Database:" name="username">
                    <Input/>
                </Form.Item>
                <Form.Item label="Username:" name="username">
                    <Input/>
                </Form.Item>
                <Form.Item label="Password:" name="username">
                    <Input/>
                </Form.Item>
                <Form.Item label="Url:" name="username">
                    <Input/>
                </Form.Item>
            </>,
        },
        {
            key: '2',
            label: `可选`,
            children: "请上传企业许可证！",
        },
        {
            key: '3',
            label: `高级`,
            children: "请上传企业许可证！",
        },
    ];

    const onFinish = (values: any) => {
        console.log('Success:', values);
    };

    const onFinishFailed = (errorInfo: any) => {
        console.log('Failed:', errorInfo);
    };

    return <>
        <Modal title="新建数据源"
               footer={false}
               open={props.isModalVisible}
               onOk={props.handleOk}
               onCancel={props.handleCancel}
               width={600}>

            <Form labelCol={{span: 4}}
                  wrapperCol={{span: 18}}
                  style={{maxWidth: 600}}
                  initialValues={{remember: true}}
                  onFinish={onFinish}
                  onFinishFailed={onFinishFailed}
                  autoComplete="off"
            >
                <Form.Item label="数据源名称" name="username">
                    <Input/>
                </Form.Item>

                <Form.Item label="备注" name="password">
                    <Input/>
                </Form.Item>

                <Tabs defaultActiveKey="1" items={items} onChange={onChange}/>

                <Form.Item wrapperCol={{offset: 8, span: 16}}>
                    <Button type="primary">测试连接</Button>
                    <Button type="primary" htmlType="submit" onClick={props.handleCancel}>保存</Button>
                </Form.Item>
            </Form>
        </Modal>;
    </>;
};

const todoReq = {
  taskId: "",
  title: ""
};
