import React from 'react';
import './index.scss'
import {message} from "antd";
import {Link, LinkProps, Outlet, useMatch, useNavigate, useResolvedPath} from "react-router-dom";


function CustomLink({ children, to, ...props }: LinkProps) {
    let resolved = useResolvedPath(to);
    let match = useMatch({ path: resolved.pathname, end: true });

    return (
        <li>
            <Link
                className={match ? "custom-link active-link" : "custom-link  un-active-link"}
                to={to}
                {...props}
                onClick={props.onClick}
            >
                {children}
            </Link>
        </li>
    );
}

function Index() {

    const navigate = useNavigate();

    return <>
        <div className={"index-nav"}>
            <div className={"nav-left"}>至轻云</div>
            <div className={"nav-right"}>
                <a className={"msg-center-a"} onClick={() => {
                    message.success("获取成功");
                }}>消息通知</a>
                <img className={"user-img"} src={'https://img.isxcode.com/galaxy/avatar.png'}/>
            </div>
        </div>
        <div className={"index-main"}>
            <div className={"index-menu"}>
                <ul className={"index-menu-ul"}>
                    <CustomLink to="/monitor">
                        健康中心
                    </CustomLink>
                    <CustomLink to="/project">
                        项目管理
                    </CustomLink>
                    <CustomLink to={"1"} onClick={() => {
                        message.warning("请上传企业许可证！")
                    }}>
                        计算集群
                    </CustomLink>
                    <CustomLink to={"2"} onClick={() => {
                        message.warning("请上传企业许可证！")
                    }}>
                        数据源
                    </CustomLink>
                    <CustomLink to={"3"} onClick={() => {
                        message.warning("请上传企业许可证！")
                    }}>
                        作业流
                    </CustomLink>
                    <CustomLink to={"4"} onClick={() => {
                        message.warning("请上传企业许可证！")
                    }}>
                        数据建模
                    </CustomLink>
                    <CustomLink to={"5"} onClick={() => {
                        message.warning("请上传企业许可证！")
                    }}>
                        调度历史
                    </CustomLink>
                    <CustomLink to={"6"} onClick={() => {
                        message.warning("请上传企业许可证！")
                    }}>
                        BI酷屏
                    </CustomLink>
                    <CustomLink to={"7"} onClick={() => {
                        message.warning("请上传企业许可证！")
                    }}>
                        数据资产
                    </CustomLink>
                    <CustomLink to={"8"} onClick={() => {
                        message.warning("请上传企业许可证！")
                    }}>
                        自定义Api
                    </CustomLink>
                    <CustomLink to={"9"} onClick={() => {
                        message.warning("请上传企业许可证！")
                    }}>
                        AI计算
                    </CustomLink>
                    <CustomLink to={"10"} onClick={() => {
                        message.warning("请上传企业许可证！")
                    }}>
                        用户管理
                    </CustomLink>
                    <CustomLink to={"11"} onClick={() => {
                        message.warning("请上传企业许可证！")
                    }}>
                        系统配置
                    </CustomLink>
                </ul>
            </div>
            <div className={"index-content"}>
                <Outlet/>
            </div>
        </div>
    </>;
}

export default Index;
