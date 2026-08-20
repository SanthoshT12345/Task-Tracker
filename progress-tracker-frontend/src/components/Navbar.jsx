import { useEffect, useState } from "react";
import { FaBell } from "react-icons/fa";

import UserDropdown from "./UserDropdown";
import { getCurrentUser } from "../api/userService";
import { getTasks } from "../api/taskService";

import "../styles/navbar.css";

function Navbar() {

    const [open, setOpen] = useState(false);

    const [user, setUser] = useState(null);

    const [tasks, setTasks] = useState([]);

    const [showNotifications, setShowNotifications] = useState(false);


    // Load current user
    useEffect(() => {

        const loadUser = async () => {

            try {

                const response = await getCurrentUser();

                setUser(response.data);

            } catch (error) {

                console.error("Failed to load user", error);

            }

        };

        loadUser();

    }, []);


    // Load tasks for notifications
    useEffect(() => {

        const loadTasks = async () => {

            try {

                const response = await getTasks();

                setTasks(response.data);

            } catch (error) {

                console.error(
                    "Failed to load tasks for notifications",
                    error
                );

            }

        };

        loadTasks();

    }, []);


    // Pending tasks
    const pendingTasks = tasks.filter(
        task => task.status === "PENDING"
    );


    // Overdue tasks
    const overdueTasks = tasks.filter(
        task => task.status === "OVERDUE"
    );


    // Total notifications
    const notificationCount =
        pendingTasks.length + overdueTasks.length;


    return (

        <div className="navbar">

            {/* Left side */}

            <div>

                <h2>Dashboard</h2>

                <p>
                    Welcome back, {user?.name} 👋
                </p>

            </div>


            {/* Right side */}

            <div className="navbar-right">


                {/* Notification */}

                <div className="notification-wrapper">

                    <button
                        className="notification-btn"
                        onClick={() =>
                            setShowNotifications(
                                !showNotifications
                            )
                        }
                    >

                        <FaBell />

                        {notificationCount > 0 && (

                            <span className="notification-count">

                                {notificationCount}

                            </span>

                        )}

                    </button>


                    {/* Notification dropdown */}

                    {showNotifications && (

                        <div className="notification-dropdown">

                            <h3>
                                Notifications
                            </h3>


                            {notificationCount === 0 ? (

                                <p className="no-notifications">

                                    No pending or overdue tasks 🎉

                                </p>

                            ) : (

                                <>

                                    {/* Overdue */}

                                    {overdueTasks.map(task => (

                                        <div
                                            className="notification-item overdue-notification"
                                            key={`overdue-${task.id}`}
                                        >

                                            <span>
                                                🔴
                                            </span>

                                            <div>

                                                <strong>
                                                    Overdue Task
                                                </strong>

                                                <p>
                                                    {task.title}
                                                </p>

                                            </div>

                                        </div>

                                    ))}


                                    {/* Pending */}

                                    {pendingTasks.map(task => (

                                        <div
                                            className="notification-item pending-notification"
                                            key={`pending-${task.id}`}
                                        >

                                            <span>
                                                ⚠️
                                            </span>

                                            <div>

                                                <strong>
                                                    Pending Task
                                                </strong>

                                                <p>
                                                    {task.title}
                                                </p>

                                            </div>

                                        </div>

                                    ))}

                                </>

                            )}

                        </div>

                    )}

                </div>


                {/* Profile */}

                <div
                    className="profile-area"
                    onClick={() => setOpen(!open)}
                >

                    <div className="avatar">

                        {user?.name
                            ?.charAt(0)
                            .toUpperCase()
                        }

                    </div>


                    {open && (

                        <UserDropdown />

                    )}

                </div>

            </div>

        </div>

    );

}

export default Navbar;