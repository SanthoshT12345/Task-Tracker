import axiosInstance from "./axiosInstance";

export const getAnalyticsOverview = () => {
    return axiosInstance.get("/api/analytics/overview");
};

export const getWeeklyAnalytics = () => {
    return axiosInstance.get("/api/analytics/weekly");
};

export const getAchievements = () => {
    return axiosInstance.get("/api/achievements");
};
