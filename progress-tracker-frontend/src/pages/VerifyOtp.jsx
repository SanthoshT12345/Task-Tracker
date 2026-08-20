import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

import {
    verifyOtp,
    resendOtp
} from "../api/authService";

import "../styles/verifyOtp.css";

function VerifyOtp() {

    const location = useLocation();
    const navigate = useNavigate();

    const email =
        location.state?.email || "";

    const [otp, setOtp] = useState("");

    const [loading, setLoading] =
        useState(false);

    const [message, setMessage] =
        useState("");

    const [error, setError] =
        useState("");


    async function handleVerify(e) {

        e.preventDefault();

        setError("");
        setMessage("");

        if (otp.length !== 6) {

            setError(
                "Please enter a 6-digit OTP"
            );

            return;
        }

        try {

            setLoading(true);

            await verifyOtp({
                email,
                otp
            });

            setMessage(
                "Account verified successfully!"
            );

            setTimeout(() => {

                navigate("/");

            }, 1500);

        } catch (err) {

            setError(
                err.response?.data ||
                "Invalid or expired OTP"
            );

        } finally {

            setLoading(false);

        }
    }


    async function handleResend() {

        setError("");
        setMessage("");

        try {

            setLoading(true);

            await resendOtp({
                email
            });

            setMessage(
                "A new OTP has been sent to your email."
            );

        } catch (err) {

            setError(
                err.response?.data ||
                "Unable to resend OTP"
            );

        } finally {

            setLoading(false);

        }
    }


    return (

        <div className="verify-page">

            <div className="verify-card">

                <h2>
                    Verify Your Email
                </h2>

                <p className="verify-description">

                    We sent a 6-digit verification
                    code to:

                </p>

                <strong>
                    {email}
                </strong>


                <form onSubmit={handleVerify}>

                    <input
                        type="text"
                        inputMode="numeric"
                        maxLength="6"
                        placeholder="Enter OTP"
                        value={otp}
                        onChange={(e) =>
                            setOtp(
                                e.target.value
                                    .replace(/\D/g, "")
                            )
                        }
                    />


                    {error && (

                        <p className="verify-error">
                            {error}
                        </p>

                    )}


                    {message && (

                        <p className="verify-success">
                            {message}
                        </p>

                    )}


                    <button
                        type="submit"
                        disabled={loading}
                    >

                        {loading
                            ? "Verifying..."
                            : "Verify Account"
                        }

                    </button>

                </form>


                <div className="resend-section">

                    <span>
                        Didn't receive the code?
                    </span>

                    <button
                        type="button"
                        className="resend-btn"
                        onClick={handleResend}
                        disabled={loading}
                    >
                        Resend OTP
                    </button>

                </div>

            </div>

        </div>

    );
}

export default VerifyOtp;