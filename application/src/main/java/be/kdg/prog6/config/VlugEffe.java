package be.kdg.prog6.config;

public class VlugEffe {

    @Service
    public class TestService {
        @Bulkhead(name = "callRemote",fallbackMethod = "fallback")
        public String callRemote()  {
            return this.testClient.callRemote();
        }
        public String fallback(Throwable throwable) {
             return "";
        }
    }

    @FeignClient(name = "x")
    interface TestClient {
        @GetMapping("/testendpoint")
        String callRemote();
    }
}
