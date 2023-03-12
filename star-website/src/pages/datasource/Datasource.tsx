import React from 'react';
import './Datasource.scss'
import {Link, LinkProps, Outlet, useMatch, useResolvedPath} from "react-router-dom";

function CustomLink({children, to, ...props}: LinkProps) {
    let resolved = useResolvedPath(to);
    let match = useMatch({path: resolved.pathname, end: true});

    return (
        <li>
            <Link
                className={match ? "active-link-x" : "un-active-link-x"}
                to={to}
                {...props}
                onClick={props.onClick}
            >
                {children}
            </Link>
        </li>
    )
}
function Datasource() {
    return <>
        <div className={"datasource-nav"}>
            <ul className={"datasource-nav-ul"}>
                <CustomLink to="/datasource/datalink">
                    数据源连接
                </CustomLink>
                <CustomLink to="/datasource/driver">
                    驱动仓库
                </CustomLink>
            </ul>
        </div>
        <div className={"datasource-content"}>
            <Outlet/>
        </div>
    </>
}

export default Datasource;
