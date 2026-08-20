import Sidebar from "../components/Sidebar";
import Navbar from "../components/Navbar";
import "../styles/settings.css";

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function Settings() {

    const navigate = useNavigate();

    const [theme, setTheme] = useState(
        localStorage.getItem("progressTrackerTheme") || "light"
    );

    useEffect(() => {

        document.body.classList.remove(
            "light-theme",
            "dark-theme"
        );

        document.body.classList.add(`${theme}-theme`);

        localStorage.setItem(
            "progressTrackerTheme",
            theme
        );

    }, [theme]);


    // =========================
    // LOGOUT
    // =========================

    const handleLogout = () => {

        localStorage.removeItem("token");

        navigate("/");

    };


    // =========================
    // EDIT PROFILE
    // =========================

    const handleEditProfile = () => {

        navigate("/profile");

    };


    // =========================
    // CHANGE PASSWORD
    // =========================

    const handleChangePassword = () => {

        alert("Change password feature coming next.");

    };


    // =========================
    // DELETE ACCOUNT
    // =========================

    const handleDeleteAccount = () => {

        const confirmed = window.confirm(
            "Are you sure you want to permanently delete your account?"
        );

        if (!confirmed) {
            return;
        }

        alert("Delete account feature coming next.");

    };


    return (

        <div className="dashboard">

            <Sidebar />

            <div className="main-content">

                <Navbar />

                <div className="settings-container">

                    <div className="settings-header">

                        <h2>Settings</h2>

                        <p>
                            Manage your account and application preferences.
                        </p>

                    </div>


                    {/* Account */}

                    <div className="settings-card">

                        <h3>Account</h3>

                        <div className="setting-row">

                            <div>
                                <strong>Profile</strong>

                                <p>
                                    Manage your personal information.
                                </p>
                            </div>

                            <button
                                onClick={handleEditProfile}
                            >
                                Edit Profile
                            </button>

                        </div>

                    </div>


                    {/* Appearance */}

                    <div className="settings-card">

                        <h3>Appearance</h3>

                        <div className="setting-row">

                            <div>
                                <strong>Theme</strong>

                                <p>
                                    Choose how Progress Tracker looks.
                                </p>
                            </div>

                            <select
                                value={theme}
                                onChange={(e) =>
                                    setTheme(e.target.value)
                                }
                            >
                                <option value="light">
                                    Light
                                </option>

                                <option value="dark">
                                    Dark
                                </option>

                            </select>

                        </div>

                    </div>


                    {/* Task Preferences */}

                    <div className="settings-card">

                        <h3>Task Preferences</h3>

                        <div className="setting-row">

                            <div>
                                <strong>Default Priority</strong>

                                <p>
                                    Priority used when creating a new task.
                                </p>
                            </div>

                            <select>

                                <option value="LOW">
                                    Low
                                </option>

                                <option value="MEDIUM">
                                    Medium
                                </option>

                                <option value="HIGH">
                                    High
                                </option>

                            </select>

                        </div>


                        <div className="setting-row">

                            <div>
                                <strong>Default Status</strong>

                                <p>
                                    New tasks always begin as pending.
                                </p>
                            </div>

                            <span className="setting-value">
                                PENDING
                            </span>

                        </div>

                    </div>


                    {/* Security */}

                    <div className="settings-card">

                        <h3>Security</h3>

                        <div className="setting-row">

                            <div>
                                <strong>Password</strong>

                                <p>
                                    Change your account password.
                                </p>
                            </div>

                            <button
                                onClick={handleChangePassword}
                            >
                                Change Password
                            </button>

                        </div>


                        <div className="setting-row">

                            <div>
                                <strong>Logout</strong>

                                <p>
                                    Sign out of your account.
                                </p>
                            </div>

                            <button
                                className="logout-btn"
                                onClick={handleLogout}
                            >
                                Logout
                            </button>

                        </div>

                    </div>


                    {/* Danger Zone */}

                    <div className="settings-card danger-card">

                        <h3>Danger Zone</h3>

                        <div className="setting-row">

                            <div>

                                <strong>
                                    Delete Account
                                </strong>

                                <p>
                                    Permanently delete your account
                                    and associated data.
                                </p>

                            </div>

                            <button
                                className="delete-account-btn"
                                onClick={handleDeleteAccount}
                            >
                                Delete Account
                            </button>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );
}

export default Settings;