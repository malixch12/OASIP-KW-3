//package sit.oasip.Component;
//
//import org.springframework.util.StringUtils;
//
//import javax.servlet.http.HttpServletRequest;
//
//public class TokenHandler {
//    public String extractJwtFromRequest(HttpServletRequest request) {
//        final String bearerToken = request.getHeader("Authorization");
//        System.out.println(bearerToken);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7, bearerToken.length());
//        }
//        request.setAttribute("message", "Please check your header key is Authorization and header value start with Bearer");
//        return null;
//    }
//}
