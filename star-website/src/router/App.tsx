import React from 'react'
import {BrowserRouter, Navigate, Route, Routes, useLocation} from 'react-router-dom';
import Login from "../pages/login/Login";
import Index from "../pages/index/Index";
import Monitor from "../pages/monitor/Monitor";
import Project from "../pages/project/Project";
import Datasource from "../pages/datasource/Datasource";
import Datalink from "../pages/datasource/datalink/Datalink";
import Driver from "../pages/datasource/driver/Driver";

export default function App() {
    return <>
        <React.StrictMode>
            <BrowserRouter>
                <Routes>
                    <Route path="/*" element={<RequireAuth><Index/></RequireAuth>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path={'/'} element={<RequireAuth><Index/></RequireAuth>}>
                        <Route index element={<Navigate to={'/monitor'}/>}/>
                        <Route path={'/monitor'} element={<RequireAuth><Monitor/></RequireAuth>}/>
                        <Route path={'/project'} element={<RequireAuth><Project/></RequireAuth>}/>
                        <Route path={'/datasource'} element={<RequireAuth><Datasource/></RequireAuth>}>
                            <Route index element={<Navigate to={'/datasource/datalink'}/>}/>
                            <Route path={'/datasource/datalink'} element={<RequireAuth><Datalink/></RequireAuth>}/>
                            <Route path={'/datasource/driver'} element={<RequireAuth><Driver/></RequireAuth>}/>
                        </Route>
                    </Route>
                </Routes>
            </BrowserRouter>
        </React.StrictMode>
    </>;
};

function RequireAuth({children}: { children: JSX.Element }) {
    const location = useLocation();
    if (localStorage.getItem('Authorization') == null) {
        return <Navigate to="/login" state={{from: location}} replace/>;
    }
    return children;
}
