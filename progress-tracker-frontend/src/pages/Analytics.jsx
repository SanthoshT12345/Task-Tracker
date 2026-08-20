import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import Navbar from "../components/Navbar";
import StatCard from "../components/StatCard";
import { getAnalyticsOverview, getWeeklyAnalytics, getAchievements } from "../api/analyticsService";
import {
    FaTasks,
    FaCheckCircle,
    FaClock,
    FaCalendarDay,
    FaBook,
    FaFire,
    FaTrophy,
    FaAward,
    FaLock
} from "react-icons/fa";

import "../styles/analytics.css";

function Analytics() {
    const [overview, setOverview] = useState({
        totalTasks: 0,
        completedTasks: 0,
        pendingTasks: 0,
        overdueTasks: 0,
        completionPercentage: 0,
        totalLearningHours: 0,
        weeklyLearningHours: 0,
        todayLearningMinutes: 0,
        activeGoals: 0,
        currentStreak: 0,
        longestStreak: 0
    });

    const [weekly, setWeekly] = useState({
        taskCompletion: [],
        learningHours: []
    });

    const [achievements, setAchievements] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadData();
    }, []);

    async function loadData() {
        try {
            setLoading(true);
            const [overviewRes, weeklyRes, achievementsRes] = await Promise.all([
                getAnalyticsOverview(),
                getWeeklyAnalytics(),
                getAchievements()
            ]);
            setOverview(overviewRes.data);
            setWeekly(weeklyRes.data);
            setAchievements(achievementsRes.data);
        } catch (error) {
            console.error("Failed to load analytics data", error);
        } finally {
            setLoading(false);
        }
    }

    const dayLabels = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];

    const maxTasks = weekly.taskCompletion.length > 0 
        ? Math.max(...weekly.taskCompletion.map(d => d.completedTasks), 1) 
        : 1;

    const maxHours = weekly.learningHours.length > 0 
        ? Math.max(...weekly.learningHours.map(d => d.hours), 1.0) 
        : 1.0;

    function getAchievementIcon(code) {
        switch(code) {
            case "FIRST_TASK": return "🎯";
            case "10_TASKS_COMPLETED": return "🏆";
            case "50_TASKS_COMPLETED": return "👑";
            case "FIRST_LEARNING_SESSION": return "📚";
            case "10_LEARNING_HOURS": return "⚡";
            case "50_LEARNING_HOURS": return "🎓";
            case "7_DAY_STREAK": return "🔥";
            case "30_DAY_STREAK": return "💥";
            case "FIRST_GOAL_COMPLETED": return "🌟";
            default: return "🏆";
        }
    }

    return (
        <div className="dashboard">
            <Sidebar />

            <div className="main-content">
                <Navbar />

                <div className="learning-header">
                    <h2>Analytics & Achievements</h2>
                    <button className="btn-primary" onClick={loadData}>
                        🔄 Refresh
                    </button>
                </div>

                <div className="stats-grid">
                    <StatCard
                        title="Total Tasks"
                        value={overview.totalTasks}
                        icon={<FaTasks />}
                        color="#2563EB"
                    />
                    <StatCard
                        title="Completed Tasks"
                        value={overview.completedTasks}
                        icon={<FaCheckCircle />}
                        color="#16A34A"
                    />
                    <StatCard
                        title="Completion Rate"
                        value={`${overview.completionPercentage}%`}
                        icon={<FaAward />}
                        color="#D97706"
                    />
                    <StatCard
                        title="Learning Hours"
                        value={`${overview.totalLearningHours}h`}
                        icon={<FaBook />}
                        color="#0D9488"
                    />
                    <StatCard
                        title="Current Streak"
                        value={`${overview.currentStreak} Days`}
                        icon={<FaFire />}
                        color="#EA580C"
                    />
                    <StatCard
                        title="Longest Streak"
                        value={`${overview.longestStreak} Days`}
                        icon={<FaTrophy />}
                        color="#8B5CF6"
                    />
                </div>

                <div className="analytics-grid">
                    <div className="analytics-card">
                        <h3>Weekly Task Completion</h3>
                        {weekly.taskCompletion.length === 0 ? (
                            <p style={{ color: "#64748b" }}>No task completions recorded this week.</p>
                        ) : (
                            <div className="chart-container">
                                {weekly.taskCompletion.map((day, idx) => {
                                    const pct = (day.completedTasks / maxTasks) * 100;
                                    return (
                                        <div className="chart-column" key={day.date}>
                                            <div className="chart-bar-wrapper">
                                                <div 
                                                    className="chart-bar-fill tasks" 
                                                    style={{ height: `${pct}%` }}
                                                />
                                                <span className="chart-tooltip">
                                                    {day.completedTasks} task{day.completedTasks !== 1 ? "s" : ""}
                                                </span>
                                            </div>
                                            <span className="chart-label">{dayLabels[idx]}</span>
                                        </div>
                                    );
                                })}
                            </div>
                        )}
                    </div>

                    <div className="analytics-card">
                        <h3>Weekly Learning Hours</h3>
                        {weekly.learningHours.length === 0 ? (
                            <p style={{ color: "#64748b" }}>No learning logged this week.</p>
                        ) : (
                            <div className="chart-container">
                                {weekly.learningHours.map((day, idx) => {
                                    const pct = (day.hours / maxHours) * 100;
                                    return (
                                        <div className="chart-column" key={day.date}>
                                            <div className="chart-bar-wrapper">
                                                <div 
                                                    className="chart-bar-fill learning" 
                                                    style={{ height: `${pct}%` }}
                                                />
                                                <span className="chart-tooltip">
                                                    {day.hours} hour{day.hours !== 1.0 ? "s" : ""}
                                                </span>
                                            </div>
                                            <span className="chart-label">{dayLabels[idx]}</span>
                                        </div>
                                    );
                                })}
                            </div>
                        )}
                    </div>
                </div>

                <div className="history-section" style={{ padding: "25px" }}>
                    <h3 style={{ marginBottom: "20px", color: "#1e293b" }}>🏆 Achievements</h3>
                    {achievements.length === 0 ? (
                        <p style={{ color: "#64748b" }}>Loading achievements...</p>
                    ) : (
                        <div className="achievements-grid">
                            {achievements.map((ach) => (
                                <div 
                                    className={`achievement-box ${ach.earned ? "earned" : "locked"}`} 
                                    key={ach.code}
                                >
                                    <div className={`achievement-badge ${ach.earned ? "earned" : "locked"}`}>
                                        {ach.earned ? getAchievementIcon(ach.code) : <FaLock style={{ fontSize: "16px" }} />}
                                    </div>
                                    <div className="achievement-info">
                                        <h4>{ach.title}</h4>
                                        <p>{ach.description}</p>
                                        <span className={`achievement-status ${ach.earned ? "earned" : "locked"}`}>
                                            {ach.earned ? "Completed" : "Locked"}
                                        </span>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default Analytics;
