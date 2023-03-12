import React, {useEffect, useState} from 'react';
import {Button, Input, Modal} from "antd";
import './AddLinkModal.scss'
import TextArea from "antd/es/input/TextArea";

export const AddLinkModal = (
  props: {
    isModalVisible: any,
    handleOk: any,
    handleCancel: any
  }
) => {

    return <>
        <Modal title="新建数据源"
               footer={false}
               open={props.isModalVisible}
               onOk={props.handleOk}
               onCancel={props.handleCancel}
               width={600}
               className={"add-link-modal"}>

            <div className={"add-datasource-div"}>
                <div className={"source-host-div"}>
                    <label className={"source-label"}>数据源名称:</label>
                    <Input className={"link-name-input"}/>
                </div>
                <div className={"source-comment-div"}>
                    <label className={"source-label"}>备注:</label>
                    <TextArea className={"source-comment"}></TextArea>
                </div>
                <div className={"source-host-div"}>
                    <label className={"source-label"}>Host:</label>
                    <Input className={"link-name-input"}/>
                    <label className={"source-label"}>Port:</label>
                    <Input className={"link-name-input"}/>
                </div>
                <div className={"source-host-div"}>
                    <label className={"source-label"}>Database:</label>
                    <Input className={"link-name-input"}/>
                    <a className={"advance-a"}>高级配置</a>
                </div>
                <div className={"source-host-div"}>
                    <label className={"source-label"}>Username:</label>
                    <Input className={"link-name-input"}/>
                </div>
                <div className={"source-host-div"}>
                    <label className={"source-label"}>Password:</label>
                    <Input className={"link-name-input"}/>
                </div>
                <div className={"source-host-div"}><label className={"source-label"}>Url:</label><Input
                    className={"link-name-input-long"}/></div>
                <div className={"test-click-div"}>
                    <Button className={"test-link-btn"}>测试连接</Button>
                    <Button className={"save-link-btn"} onClick={props.handleCancel}>保存</Button>
                </div>
                <div className={"link-log"}>
                    连接日志
                </div>
            </div>
        </Modal>;
    </>
};

const todoReq = {
  taskId: "",
  title: ""
};
