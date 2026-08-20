import "../styles/dashboard.css";

function ProgressCard({ percentage = 0 }) {
    const pct = Math.min(100, Math.max(0, percentage));

    return (
        <div className="progress-card">
            <h3>Overall Learning Progress</h3>

            <div className="progress">
                <div
                    className="progress-fill"
                    style={{ width: `${pct}%` }}
                >
                    {pct}%
                </div>
            </div>

            <p>
                {pct >= 100
                    ? "🎉 Outstanding! All learning goals achieved!"
                    : pct > 50
                    ? "Great job! Keep up the consistency."
                    : "Every step counts! Log your learning daily."}
            </p>
        </div>
    );
}

export default ProgressCard;