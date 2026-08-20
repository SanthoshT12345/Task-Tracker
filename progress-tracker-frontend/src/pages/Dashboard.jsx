import { useEffect, useState } from "react";

import Sidebar from "../components/Sidebar";
import Navbar from "../components/Navbar";
import StatCard from "../components/StatCard";
import ProgressCard from "../components/ProgressCard";
import TodayTasksCard from "../components/TodayTasksCard";

import { getAnalyticsOverview } from "../api/analyticsService";

import {
    FaTasks,
    FaCheckCircle,
    FaClock,
    FaCalendarDay,
    FaBookReader,
    FaFire,
    FaAward
} from "react-icons/fa";

function Dashboard() {

    const [stats, setStats] = useState({
        totalTasks: 0,
        completedTasks: 0,
        pendingTasks: 0,
        overdueTasks: 0,
        completionPercentage: 0,
        totalLearningHours: 0,
        currentStreak: 0
    });

    useEffect(() => {
        loadDashboard();
    }, []);

    async function loadDashboard() {
        try {
            const response = await getAnalyticsOverview();
            setStats(response.data);
        } catch (error) {
            console.error("Failed to load dashboard data", error);
        }
    }

    return (

        <div className="dashboard">

            <Sidebar />

            <div className="main-content">

                <Navbar />

                <h3 style={{ marginBottom: "15px", color: "#1e293b" }}>Tasks Overview</h3>
                <div className="stats-grid" style={{ marginBottom: "25px" }}>

                    <StatCard
                        title="Total Tasks"
                        value={stats.totalTasks}
                        icon={<FaTasks />}
                        color="#2563EB"
                    />

                    <StatCard
                        title="Completed"
                        value={stats.completedTasks}
                        icon={<FaCheckCircle />}
                        color="#16A34A"
                    />

                    <StatCard
                        title="Pending"
                        value={stats.pendingTasks}
                        icon={<FaClock />}
                        color="#EA580C"
                    />

                    <StatCard
                        title="Overdue Tasks"
                        value={stats.overdueTasks}
                        icon={<FaCalendarDay />}
                        color="#DC2626"
                    />

                </div>

                <h3 style={{ marginBottom: "15px", color: "#1e293b" }}>Learning & Progress Summary</h3>
                <div className="stats-grid" style={{ marginBottom: "30px" }}>

                    <StatCard
                        title="Learning Hours"
                        value={`${stats.totalLearningHours || 0}h`}
                        icon={<FaBookReader />}
                        color="#0D9488"
                    />

                    <StatCard
                        title="Current Streak"
                        value={`${stats.currentStreak || 0} Days`}
                        icon={<FaFire />}
                        color="#EA580C"
                    />

                    <StatCard
                        title="Completion Rate"
                        value={`${stats.completionPercentage || 0}%`}
                        icon={<FaAward />}
                        color="#D97706"
                    />

                </div>

                <div className="dashboard-row">

                    <TodayTasksCard />

                    <ProgressCard percentage={stats.completionPercentage || 0} />

                </div>

            </div>

        </div>

    );

}

export default Dashboard;