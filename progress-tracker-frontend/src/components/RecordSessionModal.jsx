import { useState } from "react";
import { createSession } from "../api/learningService";

function RecordSessionModal({ closeModal, refreshData, goals }) {

    const [topic, setTopic] = useState("");

    const [hours, setHours] = useState(1);
    const [minutes, setMinutes] = useState(0);

    const [sessionDate, setSessionDate] = useState(
        new Date().toISOString().split("T")[0]
    );

    const [goalId, setGoalId] = useState("");

    const [saving, setSaving] = useState(false);

    async function handleSubmit(e) {
        e.preventDefault();

        if (!topic.trim()) {
            alert("Please enter what you studied.");
            return;
        }

        const h = parseInt(hours, 10) || 0;
        const m = parseInt(minutes, 10) || 0;

        if (h === 0 && m === 0) {
            alert("Please enter a valid duration.");
            return;
        }

        if (m < 0 || m > 59) {
            alert("Minutes must be between 0 and 59.");
            return;
        }

        const duration = (h * 60) + m;

        const sessionData = {
            topic: topic.trim(),
            durationMinutes: duration,
            sessionDate: sessionDate,
            goalId: goalId ? parseInt(goalId, 10) : null
        };

        try {
            setSaving(true);

            await createSession(sessionData);

            await refreshData();

            closeModal();

        } catch (err) {
            console.error("Failed to record session:", err);

            alert(
                err.response?.data ||
                "Failed to record learning session."
            );

        } finally {
            setSaving(false);
        }
    }

    return (
        <div className="session-modal-overlay">

            <div className="session-modal">

                {/* Header */}
                <div className="session-modal-header">

                    <div>
                        <h2>Record Learning Session</h2>

                        <p>
                            Keep track of what you learned and your study time.
                        </p>
                    </div>

                    <button
                        type="button"
                        className="session-close-btn"
                        onClick={closeModal}
                    >
                        ×
                    </button>

                </div>


                <form onSubmit={handleSubmit}>

                    {/* Topic */}
                    <div className="session-form-group full-width">

                        <label htmlFor="session-topic">
                            What did you study? <span>*</span>
                        </label>

                        <input
                            id="session-topic"
                            type="text"
                            placeholder="e.g. Spring Security JWT Authentication"
                            value={topic}
                            onChange={(e) => setTopic(e.target.value)}
                            maxLength={150}
                            required
                        />

                        <small>
                            Be specific so you can easily understand your history later.
                        </small>

                    </div>


                    <div className="session-form-grid">

                        {/* Duration */}
                        <div className="session-form-group">

                            <label>
                                Duration <span>*</span>
                            </label>

                            <div className="session-duration-inputs">

                                <div className="session-duration-field">

                                    <input
                                        type="number"
                                        min="0"
                                        value={hours}
                                        onChange={(e) =>
                                            setHours(e.target.value)
                                        }
                                    />

                                    <span>hours</span>

                                </div>


                                <div className="session-duration-field">

                                    <input
                                        type="number"
                                        min="0"
                                        max="59"
                                        value={minutes}
                                        onChange={(e) =>
                                            setMinutes(e.target.value)
                                        }
                                    />

                                    <span>mins</span>

                                </div>

                            </div>

                        </div>


                        {/* Date */}
                        <div className="session-form-group">

                            <label htmlFor="session-date">
                                Date <span>*</span>
                            </label>

                            <input
                                id="session-date"
                                type="date"
                                value={sessionDate}
                                onChange={(e) =>
                                    setSessionDate(e.target.value)
                                }
                                required
                            />

                        </div>


                        {/* Goal */}
                        <div className="session-form-group full-width">

                            <label htmlFor="session-goal">
                                Associated Learning Goal
                            </label>

                            <select
                                id="session-goal"
                                value={goalId}
                                onChange={(e) =>
                                    setGoalId(e.target.value)
                                }
                            >

                                <option value="">
                                    None / General Learning
                                </option>

                                {goals.map((g) => (
                                    <option
                                        key={g.id}
                                        value={g.id}
                                    >
                                        {g.title} — {g.completedHours}/
                                        {g.targetHours}h
                                    </option>
                                ))}

                            </select>

                        </div>

                    </div>


                    {/* Duration preview */}
                    <div className="session-duration-summary">

                        <div className="duration-summary-icon">
                            ⏱
                        </div>

                        <div>

                            <strong>
                                {(() => {
                                    const total =
                                        (parseInt(hours, 10) || 0) * 60 +
                                        (parseInt(minutes, 10) || 0);

                                    if (total === 0) {
                                        return "No duration entered";
                                    }

                                    if (total < 60) {
                                        return `${total} minutes`;
                                    }

                                    const h = Math.floor(total / 60);
                                    const m = total % 60;

                                    return m === 0
                                        ? `${h} hour${h > 1 ? "s" : ""}`
                                        : `${h}h ${m}m`;
                                })()}
                            </strong>

                            <span>
                                will be added to your learning progress
                            </span>

                        </div>

                    </div>


                    {/* Buttons */}
                    <div className="session-modal-actions">

                        <button
                            type="button"
                            className="session-cancel-btn"
                            onClick={closeModal}
                            disabled={saving}
                        >
                            Cancel
                        </button>

                        <button
                            type="submit"
                            className="session-save-btn"
                            disabled={saving}
                        >
                            {saving ? "Saving..." : "Save Session"}
                        </button>

                    </div>

                </form>

            </div>

        </div>
    );
}

export default RecordSessionModal;