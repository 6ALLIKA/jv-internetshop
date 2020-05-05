package ma.internetshop.web.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.internetshop.lib.Injector;
import ma.internetshop.model.Role;
import ma.internetshop.model.User;
import ma.internetshop.service.UserService;

public class AuthorizationFilter implements Filter {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("ma.internetshop");
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private Map<String, Set<Role.RoleName>> protectedUrls = new HashMap<>();
    private List<String> adminUrls;
    private List<String> userUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String adminPattern = filterConfig.getInitParameter("adminUrls");
        adminUrls = Arrays.asList(adminPattern.split(","));
        String userPattern = filterConfig.getInitParameter("userUrls");
        userUrls = Arrays.asList(userPattern.split(","));
        for (String adminUrl : adminUrls) {
            protectedUrls.put(adminUrl, Set.of(Role.RoleName.ADMIN));
        }
        for (String userUrl : userUrls) {
            protectedUrls.put(userUrl, Set.of(Role.RoleName.USER));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String requestedUrl = req.getServletPath();
        if (protectedUrls.get(requestedUrl) == null) {
            filterChain.doFilter(req, resp);
            return;
        }

        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        User user = userService.get(userId);

        if (isAuthorized(user, protectedUrls.get(requestedUrl))) {
            filterChain.doFilter(req, resp);
            return;
        } else {
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
            return;
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isAuthorized(User user, Set<Role.RoleName> authorizedRoles) {
        for (Role.RoleName authorizedRole : authorizedRoles) {
            for (Role userRole : user.getRoles()) {
                if (authorizedRole.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
