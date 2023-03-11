import React from 'react'
import {BrowserRouter, Navigate, Route, Routes, useLocation} from 'react-router-dom';
import Login from "../pages/login/Login";
import Index from "../pages/index/Index";
import Monitor from "../pages/monitor/Monitor";
import Project from "../pages/project/Project";

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
