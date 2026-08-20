import { useEffect, useState } from "react";

import Sidebar from "../components/Sidebar";
import Navbar from "../components/Navbar";

import { getCurrentUser } from "../api/userService";
import { getAnalyticsOverview } from "../api/analyticsService";

import "../styles/profile.css";

function Profile() {

    const [user, setUser] = useState(null);

    const [stats, setStats] = useState({
        totalTasks: 0,
        completedTasks: 0,
        totalLearningHours: 0,
        currentStreak: 0
    });

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {

        const loadProfile = async () => {

            try {

                // Load user information
                const userResponse = await getCurrentUser();

                setUser(userResponse.data);


                // Load analytics
                const statsResponse = await getAnalyticsOverview();

                setStats(statsResponse.data);

            } catch (error) {

                console.error("Failed to load profile", error);

                setError("Unable to load profile");

            } finally {

                setLoading(false);

            }

        };

        loadProfile();

    }, []);


    if (loading) {

        return (

            <div className="dashboard">

                <Sidebar />

                <div className="main-content">

                    <Navbar />

                    <p>Loading profile...</p>

                </div>

            </div>

        );

    }


    if (error) {

        return (

            <div className="dashboard">

                <Sidebar />

                <div className="main-content">

                    <Navbar />

                    <p>{error}</p>

                </div>

            </div>

        );

    }


    return (

        <div className="dashboard">

            <Sidebar />

            <div className="main-content">

                <Navbar />

                <div className="profile-container">

                    {/* PROFILE CARD */}

                    <div className="profile-card">

                        <div className="profile-avatar">

                            {user?.name
                                ?.charAt(0)
                                .toUpperCase()
                            }

                        </div>

                        <h2>{user?.name}</h2>

                        <p>{user?.email}</p>

                        <button>
                            Edit Profile
                        </button>

                    </div>


                    <div className="profile-details">

                        {/* PERSONAL INFORMATION */}

                        <div className="info-card">

                            <h3>Personal Information</h3>

                            <div className="info-row">

                                <span>Name</span>

                                <span>{user?.name}</span>

                            </div>

                            <div className="info-row">

                                <span>Email</span>

                                <span>{user?.email}</span>

                            </div>

                            <div className="info-row">

                                <span>User ID</span>

                                <span>{user?.id}</span>

                            </div>

                        </div>


                        {/* STATISTICS */}

                        <div className="stats-card">

                            <h3>Your Statistics</h3>

                            <div className="stats-grid-profile">

                                {/* TOTAL TASKS */}

                                <div>

                                    <h1>
                                        {stats.totalTasks}
                                    </h1>

                                    <p>
                                        Total Tasks
                                    </p>

                                </div>


                                {/* COMPLETED */}

                                <div>

                                    <h1>
                                        {stats.completedTasks}
                                    </h1>

                                    <p>
                                        Completed
                                    </p>

                                </div>


                                {/* LEARNING HOURS */}

                                <div>

                                    <h1>
                                        {stats.totalLearningHours}h
                                    </h1>

                                    <p>
                                        Learning Hours
                                    </p>

                                </div>


                                {/* STREAK */}

                                <div>

                                    <h1>
                                        🔥 {stats.currentStreak}
                                    </h1>

                                    <p>
                                        Day Streak
                                    </p>

                                </div>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default Profile;