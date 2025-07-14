import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { FaFacebookF, FaInstagram, FaYoutube, FaBloggerB } from 'react-icons/fa';
import './user/styles.css';

function Header() {
  const location = useLocation();
  return (
    <header className="main-header">
      <div className="header-logo">
        <Link to="/">
          <img src="/nova_logo.jpg" alt="노바문화센터 로고" className="main-logo" />
        </Link>
      </div>
      <nav className="header-nav">
        <ul className="header-menu">
          <li><Link to="/" className={location.pathname === '/' ? 'active' : ''}>노바문화센터</Link></li>
          <li><Link to="/instructors" className={location.pathname.startsWith('/instructors') ? 'active' : ''}>강사소개</Link></li>
          <li><Link to="/programs" className={location.pathname.startsWith('/programs') ? 'active' : ''}>프로그램</Link></li>
          <li><Link to="/reservation" className={location.pathname.startsWith('/reservation') ? 'active' : ''}>예약하기</Link></li>
          <li><Link to="/news" className={location.pathname.startsWith('/news') ? 'active' : ''}>소식</Link></li>
        </ul>
      </nav>
      <div className="header-sns">
        <a href="#" aria-label="페이스북"><FaFacebookF /></a>
        <a href="#" aria-label="인스타그램"><FaInstagram /></a>
        <a href="#" aria-label="유튜브"><FaYoutube /></a>
        <a href="#" aria-label="블로그"><FaBloggerB /></a>
      </div>
    </header>
  );
}

export default Header; 