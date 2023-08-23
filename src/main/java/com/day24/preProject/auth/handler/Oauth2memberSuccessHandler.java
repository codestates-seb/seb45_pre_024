package com.day24.preProject.auth.handler;

import com.day24.preProject.auth.jwt.JwtTokenizer;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.member.service.MemberService;
import org.json.JSONArray;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Oauth2memberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;
    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;
    private final URL githubUrl = new URL("https://api.github.com/user/emails");
    public Oauth2memberSuccessHandler(JwtTokenizer jwtTokenizer, MemberService memberService, OAuth2AuthorizedClientService auth2AuthorizedClientService) throws MalformedURLException {
        this.jwtTokenizer = jwtTokenizer;
        this.memberService = memberService;
        this.auth2AuthorizedClientService = auth2AuthorizedClientService;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var oAuth2User = (OAuth2User)authentication.getPrincipal();
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        if (email.equals("null")) email = getEmailFromGitHub(getOAuthAccessToken(authentication));

        Member findMember = memberService.findMemberByEmail(email);
        String path;
        MultiValueMap<String, String> queryParams;
        if (findMember == null) {
            queryParams = signUpResponse(email);
            path = "/oauth";
        }
        else {
            queryParams = signInRespnose(findMember);
            path = "";
        }
        String uri = createURI(path, queryParams).toString();
        getRedirectStrategy().sendRedirect(request, response, uri);
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

    private MultiValueMap<String, String> signUpResponse(String email) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("email", email);

        return queryParams;
    }

    private MultiValueMap<String, String> signInRespnose(Member member) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", jwtTokenizer.generateAccessToken(member));
        queryParams.add("refresh_token", jwtTokenizer.generateRefreshToken(member));
        queryParams.add("username", member.getUsername());
        queryParams.add("email", member.getEmail());

        return queryParams;
    }
    private URI createURI(String path, MultiValueMap<String, String> queryParams){
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("pre-project.s3-website.ap-northeast-2.amazonaws.com")
                .port(80)
                .path(path)
                .queryParams(queryParams)
                .build().toUri();
    }
}
