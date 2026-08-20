import { NavLink } from "react-router-dom";
import {
    FaHome,
    FaTasks,
    FaBook,
    FaChartLine,
    FaTrophy,
    FaUser,
    FaCog
} from "react-icons/fa";

import "../styles/sidebar.css";

function Sidebar() {

    return (

        <div className="sidebar">

            <div className="logo">

                <h2>Task Tracker</h2>

            </div>

            <nav>

                <NavLink
                    to="/dashboard"
                    className={({ isActive }) =>
                        isActive ? "menu active" : "menu"
                    }
                >
                    <FaHome />
                    Dashboard
                </NavLink>

                <NavLink
                    to="/tasks"
                    className={({ isActive }) =>
                        isActive ? "menu active" : "menu"
                    }
                >
                    <FaTasks />
                    Tasks
                </NavLink>

                <NavLink
                    to="/learning"
                    className={({ isActive }) =>
                        isActive ? "menu active" : "menu"
                    }
                >
                    <FaBook />
                    Learning
                </NavLink>

                <NavLink
                    to="/analytics"
                    className={({ isActive }) =>
                        isActive ? "menu active" : "menu"
                    }
                >
                    <FaChartLine />
                    Analytics & Stats
                </NavLink>

                <NavLink
                    to="/profile"
                    className={({ isActive }) =>
                        isActive ? "menu active" : "menu"
                    }
                >
                    <FaUser />
                    Profile
                </NavLink>

                <NavLink
                    to="/settings"
                    className={({ isActive }) =>
                        isActive ? "menu active" : "menu"
                    }
                >
                    <FaCog />
                    Settings
                </NavLink>

            </nav>

        </div>

    );

}

export default Sidebar;