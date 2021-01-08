/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import auth.SessionHandler;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.CacheUtil;
import java.util.Enumeration;
import java.util.logging.Level;
import module.cr.entity.EntityCrUser;
import org.jose4j.lang.JoseException;
import resources.config.Config;
import utility.SessionManager;

/**
 *
 * @author Azerbaycan
 */
@WebFilter(filterName = "url-filter", urlPatterns = {"/*"}, asyncSupported = true)
public class AcnisUrlFilter implements Filter {

    private static Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String url = request.getRequestURI();

//       
        String token = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if ("apdtok".equals(cookie.getName())) {
                    token = cookie.getValue();
                    if (token != null) {
                        EntityCrUser user = new EntityCrUser();

                        try {
                            user = SessionHandler.getTokenFromCookie(token);

                            SessionManager.setUserName(Thread.currentThread().getId(), user.getUsername());
                            SessionManager.setLang(Thread.currentThread().getId(), user.selectLang());
                            SessionManager.setDomain(Thread.currentThread().getId(), user.selectDomain());
                            SessionManager.setUserId(Thread.currentThread().getId(), user.getId());
                            SessionManager.setCompanyId(Thread.currentThread().getId(), user.selectCompanyId());
                        } catch (JoseException ex) {
                        }
                    }
                    break;
                }
            }
        }

//        if (token == null) {
//            //for local
//            token = "eyJhbGciOiJBMTI4S1ciLCJlbmMiOiJBMTI4Q0JDLUhTMjU2In0.yXExYboTGtAgoJW78IEpD11kJ6hghXbI1rUCV3YSz-4WEhAuf80toQ.cM4Hc36UN5g2xCLOMzW5Mw.xbHj01jhjHVE6-uIMVE60s_R-DFnccU6crDY1eLEhNXo_U1iWBr7rtxLzwwntwNoN3GigdLKiJyItrBPyvt6zyOoMe8KXOuvpFGHTZ0_cRhrqbt-4WNe7aMvXb3odGKnA9Ev8Nq-29XQ9jEzoH6n8Q.1TInJmRhni8q5nTpWBgjGw";
//
//            //for cloud
////            token = "eyJhbGciOiJBMTI4S1ciLCJlbmMiOiJBMTI4Q0JDLUhTMjU2In0.8CfCKDDkN5bDj_WgmUCxtKfkVe7Dg2kqqyRy6sVRXUed_ZjjZ0z7aA.Nv3dDqtYh2RtnQNwjkZTiQ.RjH9tmSc8KO623BW5nUU9QPv9oNwih7kgMHi30vOdR43SU1Oy7JGLHD8lvgeRDMwkiYWgv-IkZoM8K-DnA492ufzFd7IwNdvAWoze7DR2fEbJfwywZ-3RkwHQogctezvb9GaKSAF17y_Pga5pyBU9IQoTdsSZoa9q7WjlszN2V8.ewgirI-uoJfcQI9rJ1YbNw";
//        }
//        System.out.println(" ok 3 - 1"+"  "+url);
//        System.out.println("token=" + token);
        //Key test = CacheUtil.getKeyFromCache(token);
        //System.out.println(test);
//        System.out.println("java.io.tmpdir="+System.getProperty("java.io.tmpdir"));
//        System.out.println("web service token->>" + token);  
//        String urlLink[] = Config.getProperty("url.none.auth.link").split(",");
//        ArrayUtils.contains(res, arg);
        if (url.trim().length() == 0 || url.trim().equals("/tsn/")
                || url.trim().equals("/tsn1/")
                || url.trim().equals("/tsn2/") || url.trim().equals("/")) {
//            System.out.println(url + "--> forward to index.html......");
// System.out.println("ok 3 - 2"+"  "+url);
            response.sendRedirect(request.getContextPath() + "/index.html");
//        } else if (!SessionHandler.checkSession(token)
//                && !url.contains("resource")
//                && !url.contains("login")
//                && !url.contains("register")
//                && !url.contains("/bview.html")
//                && !url.contains("api/get/filed/")
//                && !url.contains("/frgtpwd.html")
//                && !url.contains("index.html")
//                && !url.contains("signup")
//                && !url.contains("/termsandco.html")
//                && !url.contains("/nali/")
//                && !url.contains("/files/")
//                && !url.contains("/filesd/")
//                && !url.contains("/filev/")
//                && !url.contains("/filed/")
//                && !url.contains("/nasrv/")
//                && !url.contains("/activation")
//                  ) {
////            System.out.println("Redirected to "+request.getContextPath() + "/login.html")
//// System.out.println("ok 3 - 3"+"  "+url);;
//            System.out.println(url + "--> forward to login.html......");
//            response.addHeader("Access-Controll-Allow-Origin", "*");
//            response.addHeader("Access-Control-Allow-Credentials", "true");
//
//            response.sendRedirect(request.getContextPath() + "/login.html");
////            chain.doFilter(req, res);
        } else {
//             System.out.println("ok 3 - 4"+"  "+url);
//            System.out.println("Access controled allowed" + res.toString());
            response.addHeader("Access-Controll-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "*");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Headers", "Accept");
            response.addHeader("Access-Control-Allow-Headers", "momo,MCTotal");
            System.out.println(url + "--> forward edildi......");
            chain.doFilter(req, res);
        }

    }

    @Override
    public void destroy() {

    }
}
