import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "https://task-tracker-2p3s.onrender.com",
});

axiosInstance.interceptors.request.use((config) => {

    const token = localStorage.getItem("token");

    const publicEndpoints = [
        "/api/auth/login",
        "/api/auth/register",
        "/api/auth/verify-otp",
        "/api/auth/resend-otp"
    ];

    const isPublicEndpoint = publicEndpoints.includes(config.url);

    if (token && !isPublicEndpoint) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

export default axiosInstance;