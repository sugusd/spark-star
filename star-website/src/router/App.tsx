import React from 'react'
import {BrowserRouter, Navigate, Route, Routes, useLocation} from 'react-router-dom';
import Login from "../pages/login/Login";
import Index from "../pages/index/Index";

export default function App() {
    return <>
        <React.StrictMode>
            <BrowserRouter>
                <Routes>
                    <Route>
                        <Route index element={<RequireAuth><Index/></RequireAuth>}/>
                        <Route path="/index" element={<RequireAuth><Index/></RequireAuth>}/>
                        <Route path="/login" element={<Login/>}/>
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
