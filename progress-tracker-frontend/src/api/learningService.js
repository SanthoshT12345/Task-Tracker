import axiosInstance from "./axiosInstance";

// Learning Goals
export const getGoals = () => {
    return axiosInstance.get("/api/learning/goals");
};

export const getGoalById = (id) => {
    return axiosInstance.get(`/api/learning/goals/${id}`);
};

export const createGoal = (goalData) => {
    return axiosInstance.post("/api/learning/goals", goalData);
};

export const updateGoal = (id, goalData) => {
    return axiosInstance.put(`/api/learning/goals/${id}`, goalData);
};

export const deleteGoal = (id) => {
    return axiosInstance.delete(`/api/learning/goals/${id}`);
};

// Learning Sessions
export const getSessions = () => {
    return axiosInstance.get("/api/learning/sessions");
};

export const createSession = (sessionData) => {
    return axiosInstance.post("/api/learning/sessions", sessionData);
};

export const deleteSession = (id) => {
    return axiosInstance.delete(`/api/learning/sessions/${id}`);
};
