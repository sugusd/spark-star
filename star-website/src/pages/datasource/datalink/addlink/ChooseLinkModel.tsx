import React, {useEffect, useState} from 'react';
import {Button, Input, Modal} from "antd";
import {AddLinkModel} from "./AddLinkModel";

export const ChooseLinkModel = (
  props: {
    isModalVisible: any,
    handleOk: any,
    handleCancel: any
  }
) => {

    const [isModalVisible, setIsModalVisible] = useState(false);
    const handleOk = () => setIsModalVisible(true);
    const handleCancel = () => setIsModalVisible(false);

  const [title, setTitle] = useState('');

    return <>
        <Modal title="选择数据源类型" footer={false} open={props.isModalVisible} onOk={props.handleOk}
               onCancel={props.handleCancel}>˚
            <div>
                <Button onClick={() => handleOk()}>Mysql</Button>
                <Button>Oracle</Button>
                <Button>MongoDB</Button>
                <Button>PostgreSQL</Button>
                <Button>H2</Button>
            </div>
        </Modal>
        <AddLinkModel handleCancel={handleCancel} handleOk={handleOk} isModalVisible={isModalVisible}/>
    </>;
};

const todoReq = {
  taskId: "",
  title: ""
};
