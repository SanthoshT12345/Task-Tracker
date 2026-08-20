import { deleteGoal } from "../api/learningService";

function GoalCard({
    goal,
    refreshGoals,
    onEdit,
    onViewSessions,
    showSessions
}) {

    async function handleDelete() {
        if (!window.confirm(`Delete learning goal "${goal.title}"?`)) return;

        try {
            await deleteGoal(goal.id);
            refreshGoals();
        } catch (err) {
            console.error("Failed to delete goal", err);
            alert("Failed to delete learning goal.");
        }
    }

    const pct = goal.progressPercentage || 0;

    return (
        <div className="goal-card">

            <div className="goal-card-header">
                <h3>{goal.title}</h3>

                <span className="goal-pct">
                    {pct}%
                </span>
            </div>

            {goal.description && (
                <p className="goal-desc">
                    {goal.description}
                </p>
            )}

            <div className="goal-hours-info">
                <span>
                    <b>{goal.completedHours}</b> / {goal.targetHours} hours
                </span>
            </div>

            <div className="goal-progress-bar">
                <div
                    className="goal-progress-fill"
                    style={{
                        width: `${Math.min(100, pct)}%`
                    }}
                ></div>
            </div>

            <div className="goal-actions">

                {/* VIEW SESSIONS */}
                <button
                    className="view-sessions-btn"
                    onClick={onViewSessions}
                >
                    {showSessions
                        ? "▲ Hide Sessions"
                        : "▼ View Sessions"}
                </button>

                {/* EDIT */}
                <button
                    className="edit-btn"
                    onClick={() => onEdit(goal)}
                >
                    ✏ Edit
                </button>

                {/* DELETE */}
                <button
                    className="delete-btn"
                    onClick={handleDelete}
                >
                    🗑 Delete
                </button>

            </div>

        </div>
    );
}

export default GoalCard;