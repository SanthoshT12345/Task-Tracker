import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import Navbar from "../components/Navbar";
import GoalCard from "../components/GoalCard";
import AddGoalModal from "../components/AddGoalModal";
import RecordSessionModal from "../components/RecordSessionModal";
import { getGoals, getSessions, deleteSession } from "../api/learningService";
import "../styles/learning.css";

function Learning() {
    const [goals, setGoals] = useState([]);
    const [sessions, setSessions] = useState([]);
    const [loading, setLoading] = useState(true);

    const [showGoalModal, setShowGoalModal] = useState(false);
    const [editingGoal, setEditingGoal] = useState(null);
    const [showSessionModal, setShowSessionModal] = useState(false);
    const [expandedGoal, setExpandedGoal] = useState(null);

    useEffect(() => {
        loadData();
    }, []);

    async function loadData() {
        try {
            setLoading(true);
            const [goalsRes, sessionsRes] = await Promise.all([
                getGoals(),
                getSessions()
            ]);
            setGoals(goalsRes.data);
            setSessions(sessionsRes.data);
        } catch (error) {
            console.error("Failed to load learning data", error);
        } finally {
            setLoading(false);
        }
    }

    async function handleDeleteSession(id) {
        if (!window.confirm("Delete this learning session?")) return;
        try {
            await deleteSession(id);
            loadData();
        } catch (error) {
            console.error("Failed to delete session", error);
            alert("Failed to delete session.");
        }
    }

    

    function formatDuration(minutes) {
        if (minutes < 60) return `${minutes}m`;
        const hours = minutes / 60;
        return Number.isInteger(hours) ? `${hours}h` : `${hours.toFixed(1)}h`;
    }

    function formatDate(dateStr) {
        if (!dateStr) return "";
        const d = new Date(dateStr);
        return d.toLocaleDateString("en-US", { month: "short", day: "numeric", year: "numeric" });
    }
    function handleGoalClick(goalId) {

    if (expandedGoal === goalId) {
        setExpandedGoal(null);
    } else {
        setExpandedGoal(goalId);
    }

    }

    return (
        <div className="dashboard">
            <Sidebar />

            <div className="main-content">
                <Navbar />

                <div className="learning-header">
                    <h2>Learning Tracker</h2>
                    <div className="learning-actions-top">
                        <button
                            className="btn-primary"
                            onClick={() => {
                                setEditingGoal(null);
                                setShowGoalModal(true);
                            }}
                        >
                            + Add Goal
                        </button>

                        <button
                            className="btn-secondary"
                            onClick={() => setShowSessionModal(true)}
                        >
                            + Record Session
                        </button>
                    </div>
                </div>

                <h3 style={{ marginBottom: "15px", color: "#1e293b" }}>Learning Goals</h3>
                {goals.length === 0 ? (
                    <div className="empty-state" style={{ marginBottom: "30px" }}>
                        <h4>No Learning Goals Yet</h4>
                        <p>Click <b>+ Add Goal</b> to set your first learning objective.</p>
                    </div>
                ) : (
                    <div className="goals-grid">
                        {goals.map((goal) => {

    const linkedSessions = sessions.filter(
        (session) => session.goalId === goal.id
    );

    return (
        <div key={goal.id}>

            <GoalCard
                goal={goal}
                refreshGoals={loadData}
                onEdit={(g) => {
                    setEditingGoal(g);
                    setShowGoalModal(true);
                }}
                onViewSessions={() =>
                    handleGoalClick(goal.id)
                }
                showSessions={expandedGoal === goal.id}
            />

            {expandedGoal === goal.id && (

                <div className="goal-sessions">

                    <h4>Learning Sessions</h4>

                    {linkedSessions.length === 0 ? (

                        <p className="no-goal-sessions">
                            No sessions recorded for this goal.
                        </p>

                    ) : (

                        linkedSessions.map((session) => (

                            <div
                                className="goal-session"
                                key={session.id}
                            >

                                <div className="goal-session-info">

                                    <strong>
                                        {session.topic}
                                    </strong>

                                    <small>
                                        📅{" "}
                                        {formatDate(
                                            session.sessionDate
                                        )}
                                    </small>

                                </div>

                                <span className="history-duration">
                                    {formatDuration(
                                        session.durationMinutes
                                    )}
                                </span>

                            </div>

                        ))

                    )}

                </div>

            )}

        </div>
    );
})}
                    </div>
                )}

                <div className="history-section">
                    <h3>Learning History</h3>
                    {sessions.length === 0 ? (
                        <p style={{ color: "#64748b" }}>No learning sessions recorded yet. Click <b>+ Record Session</b> to log your progress!</p>
                    ) : (
                        <div className="history-list">
                            {sessions.map((session) => (
                                <div className="history-item" key={session.id}>
                                    <div className="history-item-info">
                                        <h4>{session.topic}</h4>
                                        <small>
                                            📅 {formatDate(session.sessionDate)}
                                            {session.goalTitle ? ` • 🎯 ${session.goalTitle}` : ""}
                                        </small>
                                    </div>
                                    <div className="history-item-right">
                                        <span className="history-duration">
                                            {formatDuration(session.durationMinutes)}
                                        </span>
                                        <button
                                            className="delete-icon-btn"
                                            onClick={() => handleDeleteSession(session.id)}
                                            title="Delete Session"
                                        >
                                            🗑
                                        </button>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </div>

                {showGoalModal && (
                    <AddGoalModal
                        closeModal={() => {
                            setShowGoalModal(false);
                            setEditingGoal(null);
                        }}
                        refreshGoals={loadData}
                        editingGoal={editingGoal}
                    />
                )}

                {showSessionModal && (
                    <RecordSessionModal
                        closeModal={() => setShowSessionModal(false)}
                        refreshData={loadData}
                        goals={goals}
                    />
                )}
            </div>
        </div>
    );
}

export default Learning;
