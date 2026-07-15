package com.example.sistembimbingan.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        boolean loggedIn =
                session != null &&
                        session.getAttribute("userId") != null;

        if (!loggedIn) {
            response.sendRedirect("/login");
            return false;
        }

        String role =
                (String) session.getAttribute("role");

        String uri =
                request.getRequestURI();

        if (uri.startsWith("/mahasiswa")
                && !"MAHASISWA".equals(role)) {

            response.sendRedirect("/login");
            return false;
        }

        if (uri.startsWith("/dosen")
                && !"DOSEN".equals(role)) {

            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}