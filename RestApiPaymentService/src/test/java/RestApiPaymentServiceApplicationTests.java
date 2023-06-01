//import static org.junit.jupiter.api.Assertions.*;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.*;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.*;
//import payservice.model.*;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Slf4j
//class RestApiPaymentServiceApplicationTests {
//    private TestRestTemplate restTemplate;
//    private UserDAO userFull;
//    private UserDAO userRead;
//    private UserDAO userBad;
//    private List<UserDAO> lstUser;
//    private String jwtUserFull;
//    private String jwtUserRead;
//    private String jwtUserBad;
//
//    private String jwtSuperAdmin;
//
//    private PaymentDTO paymentDTO1;
//    private PaymentDTO paymentDTO2;
//    private PaymentDTO paymentDTO3;
//
//    public RestApiPaymentServiceApplicationTests() {
//        restTemplate = new TestRestTemplate();
//
//        jwtSuperAdmin = getJWT("admin", "admin");
//        userFull = UserDAO.of("vadim", "p@$$word", "user_full", "Tsoy Vadim Batkovich");
//        userRead = UserDAO.of("tima", "12345678", "user_read", "Chudov Tima C#ovich");
//        userBad = UserDAO.of("artem", "12345678", "user_zero", "Atoyn Artem Batkovich");
//        lstUser = new ArrayList<>(Arrays.asList(userFull, userRead, userBad));
//        addUser(lstUser);
//        jwtUserFull = getJWT(userFull.getUsername(), userFull.getPassword());
//        jwtUserRead = getJWT(userRead.getUsername(), userRead.getPassword());
//        jwtUserBad = getJWT(userBad.getUsername(), userBad.getPassword());
//
//        List<MetadataDTO> lstMetadata = new ArrayList<>(Arrays.asList(MetadataDTO.of(9999, "Moscow"),
//                MetadataDTO.of(4555, "Piter")));
//        paymentDTO1 = PaymentDTO.of("pay1", 5555, lstMetadata);
//        paymentDTO2 = PaymentDTO.of("pay2", 5555, lstMetadata);
//        paymentDTO3 = PaymentDTO.of("pay3", 5555, lstMetadata);
//    }
//
//    @Test
//    void checkAuthorizationUserFull() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserFull);
//        ResponseEntity<UserDAO> response = restTemplate.exchange(getURI("user"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), UserDAO.class);
//        assertTrue(response.getBody().getUsername().equals(userFull.username));
//    }
//
//    @Test
//    void checkAuthorizationUserRead() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserRead);
//        ResponseEntity<UserDAO> response = restTemplate.exchange(getURI("user"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), UserDAO.class);
//        assertTrue(response.getBody().getUsername().equals(userRead.username));
//    }
//
//    @Test
//    void checkAuthorizationUserBad() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserBad);
//        ResponseEntity<UserDAO> response = restTemplate.exchange(getURI("user"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), UserDAO.class);
//        assertTrue(response.getBody().getUsername().equals(userBad.username));
//    }
//
//    @Test
//    void checkAuthorizationWithoutJwt() {
//        ResponseEntity<UserDAO> response = restTemplate.exchange(getURI("user"),
//                HttpMethod.GET.GET, new HttpEntity<>(new HttpHeaders()), UserDAO.class);
//        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
//    }
//
//    @Test
//    void checkAddPayUserFull() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserFull);
//
//        ResponseEntity<PaymentDAO> response = restTemplate.postForEntity(
//                getURI("payment/add"), new HttpEntity<>(paymentDTO1, new HttpHeaders(headers)),
//                PaymentDAO.class);
//
//        assertFalse(response.getBody().getTestPay());
//        assertTrue(response.getBody().getUserCreator().equals(userFull.fioUser));
//    }
//
//    @Test
//    void checkAddPayUserRead() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserRead);
//
//        ResponseEntity<PaymentDAO> response = restTemplate.postForEntity(
//                getURI("payment/add"), new HttpEntity<>(paymentDTO2, new HttpHeaders(headers)),
//                PaymentDAO.class);
//
//        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
//    }
//
//    @Test
//    void checkAddPayUserBad() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserBad);
//
//        ResponseEntity<PaymentDAO> response = restTemplate.postForEntity(
//                getURI("payment/add"), new HttpEntity<>(paymentDTO3, new HttpHeaders(headers)),
//                PaymentDAO.class);
//
//        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
//    }
//
//    @Test
//    void checkGetListPayUserFull() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserFull);
//
//        ResponseEntity<String> response = restTemplate.exchange(getURI("payment/get"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), String.class);
//        assertEquals(response.getStatusCode(), HttpStatus.OK);
//        assertFalse(response.getBody().contains("payments not found"));
//    }
//
//    @Test
//    void checkGetListPayUserRead() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserRead);
//
//        ResponseEntity<String> response = restTemplate.exchange(getURI("payment/get"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), String.class);
//        assertEquals(response.getStatusCode(), HttpStatus.OK);
//        assertFalse(response.getBody().contains("payments not found"));
//    }
//
//    @Test
//    void checkGetListPayUserBad() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserBad);
//
//        ResponseEntity<String> response = restTemplate.exchange(getURI("payment/get"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), String.class);
//        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void checkGetListPayByFilterUserFull() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserFull);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                getURI("payment/getbyfilter?nameService=pay1&amount=5555&statusPayment=created"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), String.class);
//        assertEquals(response.getStatusCode(), HttpStatus.OK);
//        assertFalse(response.getBody().contains("payments not found"));
//    }
//
//    @Test
//    void checkGetListPayByFilterUserRead() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserRead);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                getURI("payment/getbyfilter?nameService=pay1&amount=5555&statusPayment=created"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), String.class);
//        assertEquals(response.getStatusCode(), HttpStatus.OK);
//        assertFalse(response.getBody().contains("payments not found"));
//    }
//
//    @Test
//    void checkGetListPayByFilterUserBad() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserBad);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                getURI("payment/getbyfilter?nameService=pay3&amount=5555&statusPayment=created"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), String.class);
//        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void checkGetPayByIdUserFull() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserFull);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                getURI("payment/get/1"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), String.class);
//        assertEquals(response.getStatusCode(), HttpStatus.OK);
//        assertFalse(response.getBody().contains("payment not found"));
//    }
//
//    @Test
//    void checkGetPayByIdUserRead() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserRead);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                getURI("payment/get/1"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), String.class);
//        assertEquals(response.getStatusCode(), HttpStatus.OK);
//        assertFalse(response.getBody().contains("payment not found"));
//    }
//
//    @Test
//    void checkGetPayByIdUserBad() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserBad);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                getURI("payment/get/1"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), String.class);
//        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void healthcheckUserFull() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserFull);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                getURI("healthcheck"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), String.class);
//        assertEquals(response.getStatusCode(), HttpStatus.OK);
//        assertTrue(response.getBody().equals("Healthy"));
//    }
//
//    @Test
//    void healthcheckUserRead() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserRead);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                getURI("healthcheck"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), String.class);
//        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
//    }
//
//    @Test
//    void healthcheckUserBad() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtUserBad);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                getURI("healthcheck"),
//                HttpMethod.GET.GET, new HttpEntity<>(headers), String.class);
//        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
//    }
//
//    private void addUser(List<UserDAO> lst) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtSuperAdmin);
//        for (var usr : lst) {
//            restTemplate.postForEntity(
//                    getURI("adduser"), new HttpEntity<>(usr, new HttpHeaders(headers)),
//                    String.class);
//        }
//    }
//
//    private String getJWT(String login, String password) {
//        var authRequest = new UserDTO();
//        authRequest.setUsername(login);
//        authRequest.setPassword(password);
//
//        try {
//            var authResponseJWT = restTemplate.postForObject(getURI("login"),
//                    authRequest, AuthResponseJWT.class);
//            return authResponseJWT.getAccessToken();
//        } catch (NullPointerException e) {
//            System.out.println("Error authentication by login:" + login);
//            return "";
//        }
//    }
//
//    private String getURI(String path) {
//        return "http://localhost:8084/api/v1/" + path;
//    }
//}