import axiosInstance from "./axiosInstance";

export const getTasks = () => {
    return axiosInstance.get("/api/tasks");
};

export const createTask = (task) => {
    return axiosInstance.post("/api/tasks", task);
};

export const updateTask = (id, task) => {
    return axiosInstance.put(`/api/tasks/${id}`, task);
};

export const deleteTask = (id) => {
    return axiosInstance.delete(`/api/tasks/${id}`);
};

export const completeTask = (id) => {
    return axiosInstance.patch(`/api/tasks/${id}/complete`);
};