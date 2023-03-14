import React from 'react'
import {BrowserRouter, Navigate, Route, Routes, useLocation} from 'react-router-dom';
import Login from "./pages/login/Login";
import Home from "./pages/home/Home";
import Monitor from "./pages/monitor/Monitor";
import Project from "./pages/project/Project";
import Datasource from "./pages/datasource/Datasource";
import Datalink from "./pages/datasource/datalink/Datalink";
import Driver from "./pages/datasource/driver/Driver";
import Work from "./pages/work/Work";

export default function App() {
    return <>
        <React.StrictMode>
            <BrowserRouter>
                <Routes>
                    <Route path="/*" element={<RequireAuth><Home/></RequireAuth>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path={'/'} element={<RequireAuth><Home/></RequireAuth>}>
                        <Route index element={<Navigate to={'/monitor'}/>}/>
                        <Route path={'/monitor'} element={<RequireAuth><Monitor/></RequireAuth>}/>
                        <Route path={'/project'} element={<RequireAuth><Project/></RequireAuth>}/>
                        <Route path={'/datasource'} element={<RequireAuth><Datasource/></RequireAuth>}/>
                        <Route path={'/work'} element={<RequireAuth><Work/></RequireAuth>}/>
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
