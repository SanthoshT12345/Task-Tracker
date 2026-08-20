import { Link } from "react-router-dom";
import "../styles/home.css";

function Home() {
    return (
        <div className="home-page">

            {/* ================= NAVBAR ================= */}

            <header className="home-navbar">

                <Link to="/" className="home-brand">
                    <div className="brand-icon">✓</div>

                    <div>
                        <span>Task </span>Tracker
                    </div>
                </Link>

                <nav className="home-nav">

                    <a href="#features">Features</a>

                    <a href="#how-it-works">
                        How It Works
                    </a>

                    <Link to="/login">
                        Login
                    </Link>

                    <Link
                        to="/register"
                        className="nav-signup"
                    >
                        Get Started
                    </Link>

                </nav>

            </header>


            {/* ================= HERO ================= */}

            <main>

                <section className="hero">

                    <div className="hero-left">

                        <div className="hero-badge">
                            <span>✦</span>
                            YOUR PERSONAL PROGRESS SYSTEM
                        </div>

                        <h1>
                            Turn your
                            <span> goals </span>
                            into real progress.
                        </h1>

                        <p className="hero-description">
                            Progress Tracker helps you organize tasks,
                            build learning goals, record your study
                            sessions, and understand how you're improving.
                        </p>

                        <div className="hero-actions">

                            <Link
                                to="/register"
                                className="hero-primary"
                            >
                                Start Tracking
                                <span>→</span>
                            </Link>

                            <a
                                href="#features"
                                className="hero-secondary"
                            >
                                Explore Features
                            </a>

                        </div>

                        <div className="hero-trust">

                            <div className="trust-item">
                                <span>✓</span>
                                Task Management
                            </div>

                            <div className="trust-item">
                                <span>✓</span>
                                Learning Tracking
                            </div>

                            <div className="trust-item">
                                <span>✓</span>
                                Progress Analytics
                            </div>

                        </div>

                    </div>


                    {/* Dashboard mockup */}

                    <div className="hero-right">

                        <div className="dashboard-window">

                            <div className="window-top">

                                <div className="window-dots">
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                </div>

                                <span className="window-title">
                                    Task Tracker
                                </span>

                            </div>


                            <div className="mock-dashboard">

                                <div className="mock-sidebar">

                                    <div className="mock-logo">
                                        PT
                                    </div>

                                    <div className="mock-menu active">
                                        ▦ Dashboard
                                    </div>

                                    <div className="mock-menu">
                                        ✓ Tasks
                                    </div>

                                    <div className="mock-menu">
                                        ▣ Learning
                                    </div>

                                    <div className="mock-menu">
                                        ◩ Analytics
                                    </div>

                                </div>


                                <div className="mock-main">

                                    <div className="mock-header">

                                        <div>
                                            <h4>Dashboard</h4>
                                            <small>
                                                Welcome back 👋
                                            </small>
                                        </div>

                                        <div className="mock-avatar">
                                            S
                                        </div>

                                    </div>


                                    <div className="mock-cards">

                                        <div className="mock-card">
                                            <div className="mock-card-icon blue">
                                                ✓
                                            </div>

                                            <div>
                                                <small>Total Tasks</small>
                                                <strong>24</strong>
                                            </div>
                                        </div>


                                        <div className="mock-card">
                                            <div className="mock-card-icon green">
                                                ✓
                                            </div>

                                            <div>
                                                <small>Completed</small>
                                                <strong>18</strong>
                                            </div>
                                        </div>


                                        <div className="mock-card">
                                            <div className="mock-card-icon orange">
                                                ◷
                                            </div>

                                            <div>
                                                <small>Learning</small>
                                                <strong>32h</strong>
                                            </div>
                                        </div>

                                    </div>


                                    <div className="mock-bottom">

                                        <div className="mock-progress-card">

                                            <div className="mock-section-title">
                                                Overall Progress
                                                <strong>75%</strong>
                                            </div>

                                            <div className="mock-progress">
                                                <div></div>
                                            </div>

                                            <small>
                                                Keep building your consistency.
                                            </small>

                                        </div>


                                        <div className="mock-chart">

                                            <div className="mock-section-title">
                                                Weekly Activity
                                            </div>

                                            <div className="bars">

                                                <span style={{ height: "35%" }}></span>
                                                <span style={{ height: "55%" }}></span>
                                                <span style={{ height: "45%" }}></span>
                                                <span style={{ height: "75%" }}></span>
                                                <span style={{ height: "60%" }}></span>
                                                <span style={{ height: "90%" }}></span>
                                                <span style={{ height: "70%" }}></span>

                                            </div>

                                        </div>

                                    </div>

                                </div>

                            </div>

                        </div>

                    </div>

                </section>


                {/* ================= FEATURES ================= */}

                <section
                    className="features"
                    id="features"
                >

                    <div className="section-heading">

                        <span>POWERFUL FEATURES</span>

                        <h2>
                            Everything you need
                            to stay on track.
                        </h2>

                        <p>
                            Stop keeping your progress in your head.
                            Put everything in one organized system.
                        </p>

                    </div>


                    <div className="feature-grid">

                        <div className="feature-card">

                            <div className="feature-number">
                                01
                            </div>

                            <div className="feature-icon blue-icon">
                                ✓
                            </div>

                            <h3>
                                Task Management
                            </h3>

                            <p>
                                Create tasks, assign priorities,
                                set deadlines and track completion
                                without losing sight of what matters.
                            </p>

                            <div className="feature-link">
                                Stay organized →
                            </div>

                        </div>


                        <div className="feature-card featured">

                            <div className="feature-number">
                                02
                            </div>

                            <div className="feature-icon green-icon">
                                🎯
                            </div>

                            <h3>
                                Learning Goals
                            </h3>

                            <p>
                                Set learning targets and record
                                study sessions to see exactly how
                                much time you're investing in yourself.
                            </p>

                            <div className="feature-link">
                                Build consistency →
                            </div>

                        </div>


                        <div className="feature-card">

                            <div className="feature-number">
                                03
                            </div>

                            <div className="feature-icon purple-icon">
                                ↗
                            </div>

                            <h3>
                                Analytics & Progress
                            </h3>

                            <p>
                                Turn your activity into useful
                                statistics and understand where
                                you're improving.
                            </p>

                            <div className="feature-link">
                                See your growth →
                            </div>

                        </div>

                    </div>

                </section>


                {/* ================= HOW IT WORKS ================= */}

                <section
                    className="workflow"
                    id="how-it-works"
                >

                    <div className="section-heading">

                        <span>SIMPLE WORKFLOW</span>

                        <h2>
                            From intention to progress.
                        </h2>

                    </div>


                    <div className="workflow-grid">

                        <div className="workflow-step">

                            <div className="step-circle">
                                01
                            </div>

                            <h3>
                                Set your goals
                            </h3>

                            <p>
                                Decide what you want to accomplish
                                and define measurable learning goals.
                            </p>

                        </div>


                        <div className="workflow-line"></div>


                        <div className="workflow-step">

                            <div className="step-circle">
                                02
                            </div>

                            <h3>
                                Do the work
                            </h3>

                            <p>
                                Create tasks and record learning
                                sessions as you work toward your goals.
                            </p>

                        </div>


                        <div className="workflow-line"></div>


                        <div className="workflow-step">

                            <div className="step-circle">
                                03
                            </div>

                            <h3>
                                Measure progress
                            </h3>

                            <p>
                                Use your dashboard and analytics
                                to see how far you've come.
                            </p>

                        </div>

                    </div>

                </section>


                {/* ================= CTA ================= */}

                <section className="cta">

                    <div className="cta-content">

                        <div className="cta-badge">
                            START TODAY
                        </div>

                        <h2>
                            Your progress deserves
                            to be visible.
                        </h2>

                        <p>
                            Stop wondering whether you're improving.
                            Start tracking it.
                        </p>

                        <Link
                            to="/register"
                            className="cta-button"
                        >
                            Create Your Account
                            <span>→</span>
                        </Link>

                    </div>

                </section>

            </main>


            {/* ================= FOOTER ================= */}

            <footer className="home-footer">

                <div className="footer-brand">

                    <div className="brand-icon">
                        ✓
                    </div>

                    <div>
                        <strong>
                            Task Tracker
                        </strong>

                        <span>
                            Track. Learn. Improve.
                        </span>
                    </div>

                </div>


                <div className="footer-links">

                    <a href="#features">
                        Features
                    </a>

                    <a href="#how-it-works">
                        How It Works
                    </a>

                    <Link to="/login">
                        Login
                    </Link>

                </div>


                <p>
                    © 2026 Task Tracker
                </p>

            </footer>

        </div>
    );
}

export default Home;