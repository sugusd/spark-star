package com.isxcode.star.common.filter;

import com.isxcode.star.api.properties.StarProperties;
import com.isxcode.star.common.constant.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CommonKeyFilter extends OncePerRequestFilter {

    private final StarProperties starProperties;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("star-key");

        if (authorization == null) {
            request.getRequestDispatcher(CommonConstants.KEY_IS_NULL_EXCEPTION).forward(request, response);
            return;
        }

        if (!authorization.equals(starProperties.getSecret())) {
            request.getRequestDispatcher(CommonConstants.KEY_IS_ERROR_EXCEPTION).forward(request, response);
            return;
        }

        doFilter(request, response, filterChain);
    }
}
