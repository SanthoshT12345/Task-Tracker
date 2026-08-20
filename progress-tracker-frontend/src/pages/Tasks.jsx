import { useEffect, useState } from "react";

import Sidebar from "../components/Sidebar";
import Navbar from "../components/Navbar";
import TaskCard from "../components/TaskCard";
import AddTaskModal from "../components/AddTaskModal";

import { getTasks } from "../api/taskService";

import "../styles/tasks.css";

function Tasks() {

    const [tasks, setTasks] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [editingTask, setEditingTask] = useState(null);

    useEffect(() => {
        loadTasks();
    }, []);

    async function loadTasks() {

        try {

            const response = await getTasks();

            setTasks(response.data);

        } catch (error) {

            console.error("Failed to load tasks", error);

        }

    }

    return (

        <div className="dashboard">

            <Sidebar />

            <div className="main-content">

                <Navbar />

                <div className="tasks-header">

                    <h2>My Tasks</h2>

                    <button
                        className="add-task-btn"
                        onClick={() => {
                            setEditingTask(null);
                            setShowModal(true);
                        }}
                    >
                        + Add Task
                    </button>

                </div>

                {tasks.length === 0 ? (

                    <div className="empty-tasks">

                        <h3>No Tasks Yet</h3>

                        <p>
                            Click <b>+ Add Task</b> to create your first task.
                        </p>

                    </div>

                ) : (

                    <div className="tasks-grid">

                        {tasks.map((task) => (

                            <TaskCard
                                key={task.id}
                                task={task}
                                refreshTasks={loadTasks}
                                onEdit={(task) => {
                                    setEditingTask(task);
                                    setShowModal(true);
                                }}
                            />

                        ))}

                    </div>

                )}

                {showModal && (

                    <AddTaskModal
                        closeModal={() => {
                            setShowModal(false);
                            setEditingTask(null);
                        }}
                        refreshTasks={loadTasks}
                        editingTask={editingTask}
                    />

                )}

            </div>

        </div>

    );

}

export default Tasks;