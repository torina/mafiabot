package ua.tools.questions.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class TynTec {

    private String sendUrl = "https://api.tyntec.com/2fa/v1/otp?";
    private String checkUrl ="https://api.tyntec.com/2fa/v1/otp/{otpId}/check?otpCode={otpCode}";
    private String apikey = "41619d59c4a44b81987195463012fef6";


    @Autowired
    private RestTemplate restTemplate;

    public Optional<ResponseTT> sendVerificationCode(String phoneNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apiKey", apikey);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(sendUrl).queryParam("number", phoneNumber);

        URI uri = null;
        try {
            uri = new URI(builder.toUriString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ResponseEntity<ResponseTT> res = null;
        try {

            HttpEntity<String> entity = new HttpEntity<>("", headers);

            res = restTemplate.exchange(uri, HttpMethod.POST, entity, ResponseTT.class);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
        if (res.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(res.getBody());
        } else {
            System.err.println(res);
            return Optional.empty();
        }
    }

    public Boolean verifyCode(String otpId, String otpCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apiKey", apikey);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        Map<String, String> vars = new HashMap<>();
        vars.put("otpId", otpId);
        vars.put("otpCode", otpCode);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(checkUrl);


        ResponseEntity<ResponseTT> res = null;
        try {

            HttpEntity<String> entity = new HttpEntity<>("", headers);

            res = restTemplate.exchange(builder.buildAndExpand(vars).toUri(), HttpMethod.POST, entity, ResponseTT.class);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        if (res.getStatusCode().is2xxSuccessful()) {
            if (res.getBody().getOtpStatus() != null && res.getBody().getOtpStatus().equals("VERIFIED"));
            return true;
        } else {
            System.err.println(res);
            return false;
        }
    }

}
