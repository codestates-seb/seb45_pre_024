package com.day24.preProject.auth.handler;

import com.day24.preProject.auth.jwt.JwtTokenizer;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Oauth2memberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${redirect.front.url}")
    private String baseUrl;
    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;
    private final ObjectMapper objectMapper;
    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;
    private final URL githubUrl = new URL("https://api.github.com/user/emails");
    public Oauth2memberSuccessHandler(JwtTokenizer jwtTokenizer, MemberService memberService, ObjectMapper objectMapper, OAuth2AuthorizedClientService auth2AuthorizedClientService) throws MalformedURLException {
        this.jwtTokenizer = jwtTokenizer;
        this.memberService = memberService;
        this.objectMapper = objectMapper;
        this.auth2AuthorizedClientService = auth2AuthorizedClientService;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var oAuth2User = (OAuth2User)authentication.getPrincipal();
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        if (email.equals("null")) email = getEmailFromGitHub(getOAuthAccessToken(authentication));

        Member findMember = memberService.findMemberByEmail(email);
        String redirectUrl;
        if (findMember == null) {
            signUpResponse(response, email);
            redirectUrl = "/oauth";
        }
        else {
            signInRespnose(response, findMember);
            redirectUrl = "";
        }
        getRedirectStrategy().sendRedirect(request, response, baseUrl+redirectUrl);
    }
    private String getEmailFromGitHub(String accessToken) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) githubUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "token "+ accessToken);
        conn.setDoOutput(true);
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
            sb.append(line);
        }
        JSONArray obj = new JSONArray(sb.toString());
        return String.valueOf(obj.getJSONObject(0).get("email"));
    }
    private String getOAuthAccessToken(Authentication authentication){
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient authorizedClient = auth2AuthorizedClientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(),
                authentication.getName());

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        return accessToken.getTokenValue();
    }

    private void signUpResponse(HttpServletResponse response, String email) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
        response.getWriter().write(objectMapper.writeValueAsString(Map.of("email", email)));
    }

    private void signInRespnose(HttpServletResponse response, Member member) throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("username", member.getUsername());
        responseData.put("email", member.getEmail());

        response.setHeader("Authorization", "Bearer "+jwtTokenizer.generateAccessToken(member));
        response.setHeader("Refresh", jwtTokenizer.generateRefreshToken(member));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseData));
    }
}
