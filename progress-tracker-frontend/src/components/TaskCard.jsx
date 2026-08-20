import {
    deleteTask,
    completeTask
} from "../api/taskService";

import "../styles/tasks.css";

function TaskCard({
    task,
    refreshTasks,
    onEdit
}) {

    async function handleDelete() {

        if (!window.confirm("Delete this task?")) return;

        try {

            await deleteTask(task.id);

            refreshTasks();

        } catch (err) {

            console.error(err);

        }

    }

    async function handleComplete() {

        try {

            await completeTask(task.id);

            refreshTasks();

        } catch (err) {

            console.error(err);

        }

    }

    return (

        <div className="task-box">

            <h3>{task.title}</h3>

            <p>{task.description}</p>

           <p>

    <b>Priority:</b>

    <span className={`priority ${task.priority?.toLowerCase()}`}>

        {task.priority}

    </span>

</p>

<p>

    <b>Status:</b>

    <span className={`status ${task.status?.toLowerCase()}`}>

        {task.status === "OVERDUE" ? "🔴 OVERDUE" : task.status}

    </span>

</p>

            <small>
                📅 Due: {task.dueDate}
            </small>

            <div className="task-buttons">

                <button
                    className="edit-btn"
                    onClick={() => onEdit(task)}
                >
                    ✏ Edit
                </button>

                <button
                    className="complete-btn"
                    onClick={handleComplete}
                >
                    ✅ Complete
                </button>

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

export default TaskCard;