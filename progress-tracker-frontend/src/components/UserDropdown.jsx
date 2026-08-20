import { useNavigate } from "react-router-dom";
import {
    FaUser,
    FaCog,
    FaSignOutAlt
} from "react-icons/fa";

function UserDropdown() {

    const navigate = useNavigate();

    function logout() {

        localStorage.removeItem("token");

        navigate("/");

    }

    return (

        <div className="dropdown">

            <button onClick={() => navigate("/profile")}>

                <FaUser />

                View Profile

            </button>

            <button onClick={() => navigate("/Settings")}>

                <FaCog />

                Settings

            </button>

            <hr />

            <button
                className="logout"
                onClick={logout}
            >

                <FaSignOutAlt />

                Logout

            </button>

        </div>

    );

}

export default UserDropdown;