import { useState } from "react";
import { createGoal, updateGoal } from "../api/learningService";

function AddGoalModal({ closeModal, refreshGoals, editingGoal }) {

    const [title, setTitle] = useState(editingGoal?.title || "");
    const [description, setDescription] = useState(
        editingGoal?.description || ""
    );
    const [targetHours, setTargetHours] = useState(
        editingGoal?.targetHours || ""
    );

    const [saving, setSaving] = useState(false);

    async function handleSubmit(e) {
        e.preventDefault();

        if (!title.trim()) {
            alert("Title is required");
            return;
        }

        const hours = parseFloat(targetHours);

        if (isNaN(hours) || hours <= 0) {
            alert("Target hours must be greater than 0");
            return;
        }

        const goalData = {
            title: title.trim(),
            description: description.trim(),
            targetHours: hours
        };

        try {
            setSaving(true);

            if (editingGoal) {
                await updateGoal(editingGoal.id, goalData);
            } else {
                await createGoal(goalData);
            }

            await refreshGoals();
            closeModal();

        } catch (err) {

            console.error("Failed to save learning goal:", err);

            alert(
                err.response?.data ||
                "Failed to save learning goal."
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
                        <h2>
                            {editingGoal
                                ? "Edit Learning Goal"
                                : "Add Learning Goal"}
                        </h2>

                        <p>
                            Set a clear target and track your learning progress.
                        </p>
                    </div>

                    <button
                        type="button"
                        className="session-close-btn"
                        onClick={closeModal}
                        disabled={saving}
                    >
                        ×
                    </button>

                </div>


                <form onSubmit={handleSubmit}>

                    {/* Title */}
                    <div className="session-form-group">

                        <label htmlFor="goal-title">
                            Goal Title <span>*</span>
                        </label>

                        <input
                            id="goal-title"
                            type="text"
                            placeholder="e.g. Master Spring Boot"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            maxLength={100}
                            required
                        />

                        <small>
                            Give your goal a clear and specific name.
                        </small>

                    </div>


                    {/* Description */}
                    <div className="session-form-group">

                        <label htmlFor="goal-description">
                            Description
                        </label>

                        <textarea
                            id="goal-description"
                            placeholder="e.g. Build microservices and master REST APIs"
                            value={description}
                            onChange={(e) =>
                                setDescription(e.target.value)
                            }
                            rows="4"
                            maxLength={500}
                        />

                    </div>


                    {/* Target Hours */}
                    <div className="session-form-group">

                        <label htmlFor="target-hours">
                            Target Hours <span>*</span>
                        </label>

                        <div className="goal-hours-input">

                            <input
                                id="target-hours"
                                type="number"
                                step="0.5"
                                min="0.5"
                                placeholder="e.g. 40"
                                value={targetHours}
                                onChange={(e) =>
                                    setTargetHours(e.target.value)
                                }
                                required
                            />

                            <span>hours</span>

                        </div>

                        <small>
                            Set the total number of hours you want to spend
                            reaching this goal.
                        </small>

                    </div>


                    {/* Preview */}
                    {targetHours && parseFloat(targetHours) > 0 && (
                        <div className="goal-target-summary">

                            <div className="goal-summary-icon">
                                🎯
                            </div>

                            <div>

                                <strong>
                                    {parseFloat(targetHours)} hours target
                                </strong>

                                <span>
                                    You can track your progress from the
                                    Learning Tracker.
                                </span>

                            </div>

                        </div>
                    )}


                    {/* Actions */}
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
                            {saving
                                ? "Saving..."
                                : editingGoal
                                    ? "Update Goal"
                                    : "Save Goal"}
                        </button>

                    </div>

                </form>

            </div>

        </div>
    );
}

export default AddGoalModal;