import React, {useEffect, useState} from 'react';
import {Button, Card, Input, Modal} from "antd";
import {AddLinkModal} from "./AddLinkModal";
import './ChooseLinkModal.scss'

export const ChooseLinkModal = (
    props: {
        isModalVisible: any,
        handleOk: any,
        handleCancel: any;
    }
) => {

    const [isModalVisible, setIsModalVisible] = useState(false);
    const handleOk = () => setIsModalVisible(true);
    const handleCancel = () => setIsModalVisible(false);

    return <>
        <Modal
            title="类型选择"
            footer={false}
            open={props.isModalVisible}
            onOk={props.handleOk}
            onCancel={props.handleCancel}
            width={500}>
            <Card className={"choose-datasource-card"}>
                <Card.Grid onClick={() => handleOk()}>Api</Card.Grid>
                <Card.Grid onClick={() => handleOk()}>Clickhouse</Card.Grid>
                <Card.Grid onClick={() => handleOk()}>Doris</Card.Grid>
                <Card.Grid onClick={() => handleOk()}>Elastic</Card.Grid>
                <Card.Grid onClick={() => handleOk()}>H2</Card.Grid>
                <Card.Grid onClick={() => handleOk()}>Hbase</Card.Grid>
                <Card.Grid onClick={() => handleOk()}>Hive</Card.Grid>
                <Card.Grid onClick={() => handleOk()}>Kafka</Card.Grid>
                <Card.Grid onClick={() => handleOk()}>Mysql</Card.Grid>
                <Card.Grid onClick={() => handleOk()}>MongoDB</Card.Grid>
                <Card.Grid onClick={() => handleOk()}>Oracle</Card.Grid>
                <Card.Grid onClick={() => handleOk()}>OSS</Card.Grid>
                <Card.Grid onClick={() => handleOk()}>PostgreSQL</Card.Grid>
                <Card.Grid onClick={() => handleOk()}>Redis</Card.Grid>
                <Card.Grid onClick={() => handleOk()}>SqlServer</Card.Grid>
            </Card>
        </Modal>
        <AddLinkModal handleCancel={handleCancel} handleOk={handleOk} isModalVisible={isModalVisible}/>
    </>;
};
