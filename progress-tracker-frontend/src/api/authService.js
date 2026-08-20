import axiosInstance from "./axiosInstance";



export const loginUser = (data) => {

    return axiosInstance.post("/api/auth/login", data);

};

export const registerUser = (data) => {

    return axiosInstance.post("/api/auth/register", data);

};
export const getCurrentUser = () => {
    return axiosInstance.get("/api/users/me");
};


export const verifyOtp = (data) => {
    return axiosInstance.post(
        "/api/auth/verify-otp",
        data
    );
};

export const resendOtp = (data) => {
    return axiosInstance.post(
        "/api/auth/resend-otp",
        data
    );
};