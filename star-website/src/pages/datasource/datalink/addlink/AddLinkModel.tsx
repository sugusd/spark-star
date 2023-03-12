import React, {useEffect, useState} from 'react';
import {Button, Input, Modal} from "antd";

export const AddLinkModel = (
  props: {
    isModalVisible: any,
    handleOk: any,
    handleCancel: any
  }
) => {

    return <>
    <Modal title="新建数据源" footer={false} open={props.isModalVisible} onOk={props.handleOk}
           onCancel={props.handleCancel}>

        <div>
            名称：<Input/>
            备注：<Input/>
            host：<Input/>
            port：<Input/>
            username：<Input/>
            password：<Input/>
            备注：<Input/>
            <Button>测试连接</Button>
            <Button>上一步</Button>
            <Button onClick={props.handleCancel}>保存</Button>
    </div>

    </Modal>;
  </>;
};

const todoReq = {
  taskId: "",
  title: ""
};
