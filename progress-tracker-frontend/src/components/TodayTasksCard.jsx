import { useEffect, useState } from "react";

import { getTasks } from "../api/taskService";

import "../styles/dashboard.css";

function TodayTasksCard() {

    const [tasks, setTasks] = useState([]);

    useEffect(() => {

        loadTasks();

    }, []);

    async function loadTasks() {

        try {

            const response = await getTasks();

            const today = new Date().toISOString().split("T")[0];

            const todayTasks = response.data.filter(
                task => task.dueDate === today
            );

            setTasks(todayTasks);

        } catch (error) {

            console.log(error);

        }

    }

    return (

        <div className="task-card">

            <h3>Today's Tasks</h3>

            {tasks.length === 0 ? (

                <p>No tasks due today.</p>

            ) : (

                <ul>

                    {tasks.map(task => (

                        <li key={task.id}>

                            {task.title}

                        </li>

                    ))}

                </ul>

            )}

        </div>

    );

}

export default TodayTasksCard;