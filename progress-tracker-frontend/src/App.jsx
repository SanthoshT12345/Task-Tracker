import { Routes, Route } from "react-router-dom";
import { useEffect } from "react";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Profile from "./pages/Profile";
import Tasks from "./pages/Tasks";
import Learning from "./pages/Learning";
import Analytics from "./pages/Analytics";
import Settings from "./pages/Settings";
import VerifyOtp from "./pages/VerifyOtp";
import Home from "./components/Home";

function App() {

    useEffect(() => {

        const savedTheme =
            localStorage.getItem("progressTrackerTheme") || "light";

        document.documentElement.classList.remove(
            "light-theme",
            "dark-theme"
        );

        document.documentElement.classList.add(
            `${savedTheme}-theme`
        );

    }, []);

    return (

        <Routes>

            {/* Public pages */}
            <Route path="/" element={<Home />} />

            <Route path="/login" element={<Login />} />

            <Route path="/register" element={<Register />} />

            <Route
                path="/verify-otp"
                element={<VerifyOtp />}
            />

            {/* Application */}
            <Route
                path="/dashboard"
                element={<Dashboard />}
            />

            <Route
                path="/profile"
                element={<Profile />}
            />

            <Route
                path="/tasks"
                element={<Tasks />}
            />

            <Route
                path="/learning"
                element={<Learning />}
            />

            <Route
                path="/analytics"
                element={<Analytics />}
            />

            <Route
                path="/settings"
                element={<Settings />}
            />

        </Routes>

    );
}

export default App;