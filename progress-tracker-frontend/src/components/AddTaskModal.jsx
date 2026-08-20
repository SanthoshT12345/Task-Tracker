import { useState } from "react";
import { createTask, updateTask } from "../api/taskService";

function AddTaskModal({
    closeModal,
    refreshTasks,
    editingTask
}) {

    const [title, setTitle] = useState(
        editingTask?.title || ""
    );

    const [description, setDescription] = useState(
        editingTask?.description || ""
    );

    const [dueDate, setDueDate] = useState(
        editingTask?.dueDate || ""
    );

    const [priority, setPriority] = useState(
        editingTask?.priority || "MEDIUM"
    );

    async function handleSubmit(e) {

        e.preventDefault();

        const taskData = {
            title,
            description,
            priority,
            dueDate
        };

        try {

            if (editingTask) {

                await updateTask(
                    editingTask.id,
                    taskData
                );

            } else {

                await createTask(taskData);

            }

            refreshTasks();
            closeModal();

        } catch (err) {

            console.error(err);
            alert("Unable to save task.");

        }
    }

    return (

        <div className="modal-overlay">

            <div className="modal">

                <h2>
                    {editingTask ? "Edit Task" : "Add Task"}
                </h2>

                <form onSubmit={handleSubmit}>

                    {/* TITLE */}

                    <label>
                        Task Title

                        <input
                            type="text"
                            placeholder="e.g. Complete Java practice"
                            value={title}
                            onChange={(e) =>
                                setTitle(e.target.value)
                            }
                            required
                        />

                    </label>


                    {/* DESCRIPTION */}

                    <label>
                        Description

                        <textarea
                            placeholder="Describe your task..."
                            value={description}
                            onChange={(e) =>
                                setDescription(e.target.value)
                            }
                        />

                    </label>


                    {/* PRIORITY + DATE */}

                    <div className="form-row">

                        <label>
                            Priority

                            <select
                                value={priority}
                                onChange={(e) =>
                                    setPriority(e.target.value)
                                }
                            >

                                <option value="LOW">
                                    Low
                                </option>

                                <option value="MEDIUM">
                                    Medium
                                </option>

                                <option value="HIGH">
                                    High
                                </option>

                            </select>

                        </label>


                        <label>
                            Due Date

                            <input
                                type="date"
                                value={dueDate}
                                onChange={(e) =>
                                    setDueDate(e.target.value)
                                }
                                required
                            />

                        </label>

                    </div>


                    {/* BUTTONS */}

                    <div className="modal-actions">

                        <button
                            type="button"
                            className="cancel-task-btn"
                            onClick={closeModal}
                        >
                            Cancel
                        </button>

                        <button
                            type="submit"
                            className="save-task-btn"
                        >
                            {editingTask
                                ? "Update Task"
                                : "Save Task"}
                        </button>

                    </div>

                </form>

            </div>

        </div>

    );
}

export default AddTaskModal;