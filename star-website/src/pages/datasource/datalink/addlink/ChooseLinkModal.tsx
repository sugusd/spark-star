import React, {useEffect, useState} from 'react';
import {Button, Input, Modal} from "antd";
import {AddLinkModal} from "./AddLinkModal";
import './ChooseLinkModal.scss'

export const ChooseLinkModal = (
  props: {
    isModalVisible: any,
    handleOk: any,
    handleCancel: any
  }
) => {

    const [isModalVisible, setIsModalVisible] = useState(false);
    const handleOk = () => setIsModalVisible(true);
    const handleCancel = () => setIsModalVisible(false);

    return <>
        <Modal
            title="选择类型"
            footer={false}
            className={"choose-link-modal"}
            open={props.isModalVisible}
            onOk={props.handleOk}
            onCancel={props.handleCancel}
            width={500}>
            <div className={"choose-link-div"}>
                <div className={"datasource-type"} onClick={() => handleOk()}>Api</div>
                <div className={"datasource-type"} onClick={() => handleOk()}>Clickhouse</div>
                <div className={"datasource-type"} onClick={() => handleOk()}>Doris</div>
                <div className={"datasource-type"} onClick={() => handleOk()}>Elastic</div>
                <div className={"datasource-type"} onClick={() => handleOk()}>H2</div>
                <div className={"datasource-type"} onClick={() => handleOk()}>Hbase</div>
                <div className={"datasource-type"} onClick={() => handleOk()}>Hive</div>
                <div className={"datasource-type"} onClick={() => handleOk()}>Mysql</div>
                <div className={"datasource-type"} onClick={() => handleOk()}>MongoDB</div>
                <div className={"datasource-type"} onClick={() => handleOk()}>Oracle</div>
                <div className={"datasource-type"} onClick={() => handleOk()}>OSS</div>
                <div className={"datasource-type"} onClick={() => handleOk()}>PostgreSQL</div>
                <div className={"datasource-type"} onClick={() => handleOk()}>SqlServer</div>
            </div>
        </Modal>
        <AddLinkModal handleCancel={handleCancel} handleOk={handleOk} isModalVisible={isModalVisible}/>
    </>;
};

const todoReq = {
  taskId: "",
  title: ""
};
